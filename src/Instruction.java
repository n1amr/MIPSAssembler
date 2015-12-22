
import java.util.ArrayList;

public class Instruction {
  String instruction;
  int lineNumber;

  public Instruction(String s, int lineNumber) throws Exception {
    instruction = s;
    this.lineNumber = lineNumber;
    comvert();
  }

  String binaryCode;

  private void comvert() throws Exception {
    if (instruction.contains(":")) {
      int p = instruction.indexOf(":");
      instruction = instruction.substring(p + 1).trim();
    }

    if (instruction.contains("//")) {
      int p = instruction.indexOf("//");
      instruction = instruction.substring(0, p).trim();
      System.out.println(instruction);
    }

    String[] stringParts = instruction.split("[\\(\\, \\)]");
    ArrayList<String> parts = new ArrayList<>();
    for (String part : stringParts) {
      part = part.trim();
      if (part.length() > 0) {
        parts.add(part);
      }
    }

    if (parts.size() == 0) {
      binaryCode = "00000000000000000000000000000000";
      return;
    }

    String default_opcode = "000000";
    String default_shamt = "00000";
    String constant = null;

    String rs = null, rt = null, rd = null;

    try {
      rd = new Register(parts.get(1)).binaryCode;
    } catch (Exception e) {
    }
    try {
      rs = new Register(parts.get(2)).binaryCode;
    } catch (Exception e) {
    }
    try {
      rt = new Register(parts.get(3)).binaryCode;
    } catch (Exception e) {
    }

    String rFormat = "%s_%s_%s_%s_%s_%s";
    String iFormat = "%s_%s_%s_%s";
    String jFormat = "%s_%s";

    String function = parts.get(0);
    if (function.equals("add")) {
      binaryCode = String.format(rFormat, default_opcode, rs, rt, rd,
          default_shamt, "100000");

    } else if (function.equals("sub")) {
      binaryCode = String.format(rFormat, default_opcode, rs, rt, rd,
          default_shamt, "100010");

    } else if (function.equals("mul")) {
      binaryCode = String.format(rFormat, default_opcode, rs, rt, rd,
          default_shamt, "011000");

    } else if (function.equals("addi")) {
      constant = Assembler.stringToBinary(parts.get(3), 16);
      binaryCode = String.format(iFormat, "001000", rs, rd, constant);

    } else if (function.equals("lw")) {
      constant = Assembler.stringToBinary(parts.get(2), 16);
      rs = new Register(parts.get(3)).binaryCode;
      binaryCode = String.format(iFormat, "100011", rs, rd, constant);

    } else if (function.equals("lh")) {
      constant = Assembler.stringToBinary(parts.get(2), 16);
      rs = new Register(parts.get(3)).binaryCode;
      binaryCode = String.format(iFormat, "100001", rs, rd, constant);

    } else if (function.equals("lb")) {
      constant = Assembler.stringToBinary(parts.get(2), 16);
      rs = new Register(parts.get(3)).binaryCode;
      binaryCode = String.format(iFormat, "100000", rs, rd, constant);

    } else if (function.equals("lhu")) {
      constant = Assembler.stringToBinary(parts.get(2), 16);
      rs = new Register(parts.get(3)).binaryCode;
      binaryCode = String.format(iFormat, "100101", rs, rd, constant);

    } else if (function.equals("lbu")) {
      constant = Assembler.stringToBinary(parts.get(2), 16);
      rs = new Register(parts.get(3)).binaryCode;
      binaryCode = String.format(iFormat, "100100", rs, rd, constant);

    } else if (function.equals("sw")) {
      constant = Assembler.stringToBinary(parts.get(2), 16);
      rs = new Register(parts.get(3)).binaryCode;
      binaryCode = String.format(iFormat, "101011", rs, rd, constant);

    } else if (function.equals("sh")) {
      constant = Assembler.stringToBinary(parts.get(2), 16);
      rs = new Register(parts.get(3)).binaryCode;
      binaryCode = String.format(iFormat, "101001", rs, rd, constant);

    } else if (function.equals("sb")) {
      constant = Assembler.stringToBinary(parts.get(2), 16);
      rs = new Register(parts.get(3)).binaryCode;
      binaryCode = String.format(iFormat, "101000", rs, rd, constant);

    } else if (function.equals("sll")) {
      String shamt = Assembler.stringToBinary(parts.get(3), 5);
      binaryCode = String.format(rFormat, default_opcode, "00000", rs, rd,
          shamt, "000000");

    } else if (function.equals("sll")) {
      String shamt = Assembler.stringToBinary(parts.get(3), 5);
      binaryCode = String.format(rFormat, default_opcode, "00000", rs, rd,
          shamt, "000010");

    } else if (function.equals("and")) {
      binaryCode = String.format(rFormat, default_opcode, rs, rt, rd,
          default_shamt, "100100");

    } else if (function.equals("or")) {
      binaryCode = String.format(rFormat, default_opcode, rs, rt, rd,
          default_shamt, "100101");

    } else if (function.equals("andi")) {
      constant = Assembler.stringToBinary(parts.get(3), 16);
      binaryCode = String.format(iFormat, "001100", rs, rd, constant);

    } else if (function.equals("ori")) {
      constant = Assembler.stringToBinary(parts.get(3), 16);
      binaryCode = String.format(iFormat, "001101", rs, rd, constant);

    } else if (function.equals("nor")) {
      binaryCode = String.format(rFormat, default_opcode, rs, rt, rd,
          default_shamt, "100111");

    } else if (function.equals("beq")) {
      constant = Assembler.pad(Assembler.getLabelCode(parts.get(3), lineNumber),
          16);
      binaryCode = String.format(iFormat, "000101", rs, rd, constant);

    } else if (function.equals("bne")) {
      constant = Assembler.pad(Assembler.getLabelCode(parts.get(3), lineNumber),
          16);
      binaryCode = String.format(iFormat, "000100", rs, rd, constant);

    } else if (function.equals("jal")) {
      constant = Assembler.stringToBinary(parts.get(1), 26);
      binaryCode = String.format(jFormat, "000011", constant);

    } else if (function.equals("j")) {
      constant = Assembler.stringToBinary(parts.get(1), 26);
      binaryCode = String.format(jFormat, "000010", constant);

    } else if (function.equals("jr")) {
      rs = new Register(parts.get(1)).binaryCode;
      binaryCode = String.format(rFormat, default_opcode, rs, "00000", "00000",
          default_shamt, "001000");

    } else if (function.equals("slt")) {
      binaryCode = String.format(rFormat, default_opcode, rs, rt, rd,
          default_shamt, "101010");
    } else if (function.equals("sltu")) {
      binaryCode = String.format(rFormat, default_opcode, rs, rt, rd,
          default_shamt, "101001");
    } else if (function.equals("slti")) {
      constant = Assembler.stringToBinary(parts.get(3), 16);
      binaryCode = String.format(iFormat, "001010", rs, rd, constant);
    } else if (function.equals("sltui")) {
      constant = Assembler.stringToBinary(parts.get(3), 16);
      binaryCode = String.format(iFormat, "001001", rs, rd, constant);
    } else if (function.equals("lui")) {
      constant = Assembler.stringToBinary(parts.get(3), 16);
      binaryCode = String.format(iFormat, "001111", "00000", rd, constant);
    } else {
      throw new Exception("Undefined instruction: " + function);
    }

  }

  public String toBinary() {
    return binaryCode;
  }
}
