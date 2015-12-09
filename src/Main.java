import java.io.File;
import java.io.FileNotFoundException;

public class Main {
  public static void main(String[] args) {
    File sourceFile = null;
    File outputFile = null;

    for (int i = 0; i < args.length; i++) {
      String s = args[i];
      if (s.charAt(0) == '-' && i + 1 < args.length) {
        if (s.equals("-o") || s.equals("--output")) {
          outputFile = new File(args[i + 1]);
          i++;
        }
      } else {
        sourceFile = new File(s);
      }
    }

    if (sourceFile == null) {
      System.out.println(
          "Usage: java -jar mips.jar <source file> [-o <output file>]");
      return;
    }

    if (outputFile == null) {
      outputFile = new File(sourceFile + ".memory");
    }

    try {
      String content = FileManager.readFile(sourceFile);
      System.out.println(content);
      String assembly = Assembler.assemble(content);
      FileManager.writeFile(outputFile, assembly);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
}
