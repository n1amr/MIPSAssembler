import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Assembler {
  static Map<String, Integer> labels;
  static ArrayList<String> lines;

  public static String assemble(String content) {
    labels = new HashMap<String, Integer>();
    lines = new ArrayList<>();

    // Add non empty lines to list
    Scanner scanner = new Scanner(content);
    while (scanner.hasNextLine()) {
      String s = scanner.nextLine().trim();
      if (s.length() != 0)
        lines.add(s);
    }
    scanner.close();

    recordLabels(lines);

    StringBuffer bin = new StringBuffer();
    for (int i = 0; i < lines.size(); i++) {
      String line = lines.get(i);
      int p = line.indexOf(":");
      line = line.substring(p + 1);
      Instruction instruction;
      try {
        instruction = new Instruction(line, i);
      } catch (Exception e) {
        String msg = "Error at line " + (i + 1) + ": " + e.getMessage();
        System.out.println(msg);
        e.printStackTrace();
        return msg;
      }
      bin.append(instruction.toBinary() + '\n');
    }
    System.out.println(bin.toString());

    return bin.toString();
  }

  private static void recordLabels(ArrayList<String> lines) {
    for (int i = 0; i < lines.size(); i++) {
      String line = lines.get(i);
      if (line.contains(":")) {
        int p = line.indexOf(':');
        String label = line.substring(0, p);
        labels.put(label, i);
      }
    }
  }

  public static String getLabelCode(String name, int atLine) {
    int offset = labels.get(name) - atLine - 1;
    return integerToBinary(offset, 16);
  }

  public static String pad(String s, int l) {
    while (s.length() < l) {
      s = '0' + s;
    }
    return s;
  }

  public static String integerToBinary(int n, int l) {
    String s = Integer.toBinaryString(n);

    s = pad(s, l);
    if (n < 0 && s.length() > l) {
      int len = s.length();
      s = s.substring(len - l);
    }
    return s;
  }

  public static String stringToBinary(String num, int l) {
    return integerToBinary(Integer.valueOf(num), l);
  }
}