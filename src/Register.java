
public class Register {
  String name;
  int num;
  String binaryCode;

  public Register(String name) throws Exception {
    this.name = name.toLowerCase();
    convert();
    convertToBinary();
  }

  private void convertToBinary() {
    if (num == -1) {
      binaryCode = null;
      return;
    }
    String s = Integer.toBinaryString(num);
    while (s.length() < 5)
      s = '0' + s;
    binaryCode = s;
  }

  private void convert() throws Exception {
    if (name.startsWith("$"))
      name = name.substring(1);
    if (Character.isDigit(name.charAt(0)))
      num = Integer.valueOf(name);
    else if (name.equals("zero"))
      num = 0;
    else if (name.equals("at"))
      num = 1;
    else if (name.equals("gp"))
      num = 28;
    else if (name.equals("sp"))
      num = 29;
    else if (name.equals("fp"))
      num = 30;
    else if (name.equals("ra"))
      num = 31;
    else {
      int n = Integer.valueOf(name.substring(1));
      if (name.startsWith("v"))
        num = 2 + n;
      else if (name.startsWith("a"))
        num = 4 + n;
      else if (name.startsWith("t"))
        num = n < 8 ? 8 + n : n - 8 + 24;
      else if (name.startsWith("s"))
        num = 16 + n;
      else if (name.startsWith("k"))
        num = 26 + n;
      else
        throw new Exception("Unknown register: " + name);
    }
  }

  public String getBinaryCode() {
    return binaryCode;
  }

  public int getCode() {
    return num;
  }
}
