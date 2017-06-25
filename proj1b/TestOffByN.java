/**
 * Created by BlackIce on 2017/6/25.
 */
import static org.junit.Assert.*;
import org.junit.Test;

public class TestOffByN {
    @Test
    public void testOffByOne() {
        OffByOne obo = new OffByOne();
        assertTrue( obo.equalChars('a','b') );
        assertTrue( obo.equalChars('r','q') );

        assertFalse( obo.equalChars('a','e') );
        assertFalse( obo.equalChars('z','z') );
        assertFalse( obo.equalChars( 'a','a') );
    }

    @Test
    public void testOffByN() {
        int N = 5;
        OffByN obn = new OffByN(N);
        assertTrue( obn.equalChars('a','f') );
        assertFalse( obn.equalChars('f','h') );
    }
}
