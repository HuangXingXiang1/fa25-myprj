public class PrintIndexed {
   /**
     * Prints each character of a given string followed by the reverse of its index.
     * Example: printIndexed("hello") -> h4e3l2l1o0
     */
   public static void printIndexed(String s) {
      for (int i = 0; i < s.length(); i++) {
          int reverseindex = s.length()-1-i; //反向索引值
          System.out.print(s.charAt(i));
          System.out.print(reverseindex);
      }
      System.out.println();
   }

   public static void main(String[] args) {
      printIndexed("hello");
      printIndexed("cat"); // should print c2a1t0
   }
}