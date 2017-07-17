package db;
import db.Value.*;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Created by BlackIce on 2017/7/17.
 */
public class testValue {
    @Test
    public void testString() {
        Value v1 = new Value("'Lee'", "string");
        Value v2 = new Value("'Fang'","string");
        Value v3 = new Value("'Ray","string");
        Value v4 = new Value("'Lee'", "string");

        assertTrue(v1.compareTo(v1) == 0);
        assertTrue(v1.compareTo(v4) == 0);
        assertTrue(v1.compareTo(v2) > 0);
        assertTrue(v1.compareTo(v3) < 0);



    }
}
