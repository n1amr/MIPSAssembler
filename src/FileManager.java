import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileManager {
  public static String readFile(File file) throws FileNotFoundException {
    StringBuffer content = new StringBuffer();

    Scanner scanner = new Scanner(file);
    while (scanner.hasNextLine()) {
      content.append(scanner.nextLine());
      content.append('\n');
    }
    scanner.close();
    return content.toString();
  }

  public static void writeFile(File file, String content)
      throws FileNotFoundException {
    Scanner scanner = new Scanner(content);
    PrintWriter printWriter = new PrintWriter(file);
    while (scanner.hasNextLine()) {
      printWriter.println(scanner.nextLine());
    }
    scanner.close();
    printWriter.close();
  }
}
