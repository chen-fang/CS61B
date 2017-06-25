/**
 * Created by BlackIce on 2017/6/25.
 */
import static org.junit.Assert.*;
import org.junit.Test;

public class TestPalindrome {
    @Test
    public void isPalindromeTest() {
        Palindrome p = new Palindrome();
        assertTrue( p.isPalindrome("") );
        assertTrue( p.isPalindrome("a") );
        assertTrue( p.isPalindrome("aba") );
        assertTrue( p.isPalindrome("bab") );

        assertFalse( p.isPalindrome("ab"));
        assertFalse( p.isPalindrome("abc") );
    }

    @Test
    public void isPalindromeByOneTest() {
        Palindrome p = new Palindrome();
        OffByOne obo = new OffByOne();
        assertTrue( p.isPalindrome("flake", obo) );
    }
}
