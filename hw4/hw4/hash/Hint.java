package hw4.hash;

/** integer values are represented by 32 bits. Multiplying 256 equals left shifting
 *  by 8 bits since 256 = 2^8. After 4 shifts, all valid values are pushed out, hence
 *  only 0 bits are left.
 */
public class Hint {
    public static void main(String[] args) {
        System.out.println("The powers of 256 in Java are:");
        int x = 1;
        for (int i = 0; i < 10; i += 1) {
            System.out.println(i + "th power: " + x);
            x = x * 256;
        }
    }
} 
