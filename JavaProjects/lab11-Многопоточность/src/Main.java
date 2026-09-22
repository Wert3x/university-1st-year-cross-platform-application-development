public class StudentPortal {
  private static boolean check(String var0) {
    if (var0.startsWith("grodno{") && var0.endsWith("}")) {
      String var1 = var0.substring(7, var0.length() - 1);
      String[] var2 = var1.split("_");
      if (var2.length != 4) {
        return false;
      } else {
        return partOne(var2[0]) && partTwo(var2[1]) && partThree(var2[2]) && partFour(var2[3]);
      }
    } else {
      return false;
    }
  }

  private static boolean partOne(String var0) {
    return var0.length() == 4 && var0.hashCode() == 3254818 && var0.charAt(0) == 'j';
  }

  private static boolean partTwo(String var0) {
    return (new StringBuilder(var0)).reverse().toString().equals("edocetyb");
  }

  private static boolean partThree(String var0) {
    int[] var1 = new int[]{115, 126, 97, 125};
    if (var0.length() != var1.length) {
      return false;
    } else {
      for(int var2 = 0; var2 < var0.length(); ++var2) {
        if ((var0.charAt(var2) ^ 18) != var1[var2]) {
          return false;
        }
      }

      return true;
    }
  }

  private static boolean partFour(String var0) {
    return var0.length() == 4 && var0.charAt(0) + var0.charAt(1) + var0.charAt(2) + var0.charAt(3) == 415 && var0.charAt(0) == 'n' && var0.charAt(3) == 'e';
  }

  public static void main(String[] var0) throws Exception {
    byte[] var1 = new byte[256];
    System.out.print("Token: ");
    int var2 = System.in.read(var1);
    if (var2 > 0) {
      String var3 = (new String(var1, 0, var2)).trim();
      if (check(var3)) {
        System.out.println("Welcome.");
      } else {
        System.out.println("Rejected.");
      }

    }
  }
}