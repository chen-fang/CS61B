public class HelloNumbers {
   public static void main( String[] args ) {
      int N = 10;
      int n = 0;
      int sum = 0;
      while( n < N ) {
	 sum += n;
	 System.out.println( sum );
	 n++;
      }
   }
}
