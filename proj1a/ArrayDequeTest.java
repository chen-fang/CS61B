import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Created by BlackIce on 2017/6/21.
 */
public class ArrayDequeTest {
    /* Test addFirst() and prevIndex() */
    @Test
    public void addFirstTest() {
        ArrayDeque<String> myArray = new ArrayDeque<>();
        assertEquals(0, myArray.size());
        assertEquals(8, myArray.getCapacity());

        myArray.addFirst( "a" );
        assertEquals(1, myArray.size());
        assertEquals( 7, myArray.getFirst() );
        assertEquals( 1, myArray.getLast() );

        myArray.addFirst( "b" );
        assertEquals(2, myArray.size());
        assertEquals( 6, myArray.getFirst() );
        assertEquals( 1, myArray.getLast() );

        String[] expArray = { "a", null, null, null, null, null, null, "b" };
        Object[] outArray = myArray.dump();
        assertArrayEquals( expArray, outArray );
    }

    /* Test addLast() and lastIndex() */
    @Test
    public void addLastTest() {
        ArrayDeque<String> myArray = new ArrayDeque<>();
        assertEquals(0, myArray.size());
        assertEquals(8, myArray.getCapacity());

        myArray.addLast( "a" );
        assertEquals(1, myArray.size());
        assertEquals( 0, myArray.getFirst() );
        assertEquals( 2, myArray.getLast() );

        myArray.addLast( "b" );
        assertEquals(2, myArray.size());
        assertEquals( 0, myArray.getFirst() );
        assertEquals( 3, myArray.getLast() );

        String[] expArray = { null, "a", "b", null, null, null, null, null };
        Object[] outArray = myArray.dump();
        assertArrayEquals( expArray, outArray );
    }

    /* Test addFirst() & addLast() together */
    @Test
    public void addTest() {
        ArrayDeque<String> myArray = new ArrayDeque<>();
        assertEquals(0, myArray.size());
        assertEquals(8, myArray.getCapacity());

        myArray.addFirst("a");
        myArray.addLast("b");
        myArray.addLast("c");
        myArray.addFirst("d");
        myArray.addFirst("e");
        myArray.addLast("f");
        myArray.addFirst("g");

        assertEquals( 7, myArray.size() );
        assertEquals( 8, myArray.getCapacity() );
        assertEquals( 4, myArray.getFirst() );
        assertEquals( 4, myArray.getLast() );
        String[] expArray = { "a", "b", "c", "f", null, "g", "e", "d"};
        Object[] outArray = myArray.dump();
        assertArrayEquals( expArray, outArray );
    }

    /* Test removeFirst() */
    @Test
    public void removeFirstTest() {
        ArrayDeque<String> myArray = new ArrayDeque<>();
        myArray.addFirst("a");
        String s = myArray.removeFirst();
        assertEquals( 0, myArray.size() );
        assertEquals( 0, myArray.getFirst() );
        assertEquals( "a", s );
        String[] expArray = { null, null, null, null, null, null, null, null };
        Object[] outArray = myArray.dump();
        assertArrayEquals( expArray, outArray );

        myArray.addFirst("a");
        myArray.addFirst("b");
        String ss = myArray.removeFirst();
        assertEquals( 1, myArray.size() );
        assertEquals( 7, myArray.getFirst() );
        assertEquals( "b", ss );
        expArray = new String[]{ "a", null, null, null, null, null, null, null };
        outArray = myArray.dump();
        assertArrayEquals( expArray, outArray );

        ss = myArray.removeFirst();
        assertEquals( 0, myArray.size() );
        assertEquals( 0, myArray.getFirst() );
        assertEquals( "a", ss );
        expArray = new String[]{ null, null, null, null, null, null, null, null };
        outArray = myArray.dump();
        assertArrayEquals( expArray, outArray );

        myArray.addLast( "c" );
        myArray.addLast( "d" );
        ss = myArray.removeFirst();
        assertEquals( 1, myArray.size() );
        assertEquals( 1, myArray.getFirst() );
        assertEquals( "c", ss );
        expArray = new String[]{ null, null, "d", null, null, null, null, null };
        outArray = myArray.dump();
        assertArrayEquals( expArray, outArray );
    }

    /* Test removeLast() */
    @Test
    public void removeLastTest() {
        ArrayDeque<String> myArray = new ArrayDeque<>();
        myArray.addLast("a");
        String s = myArray.removeLast();
        assertEquals( 0, myArray.size() );
        assertEquals( 1, myArray.getLast() );
        assertEquals( "a", s );
        String[] expArray = { null, null, null, null, null, null, null, null };
        Object[] outArray = myArray.dump();
        assertArrayEquals( expArray, outArray );

        myArray.addLast("a");
        myArray.addLast("b");
        String ss = myArray.removeLast();
        assertEquals( 1, myArray.size() );
        assertEquals( 2, myArray.getLast() );
        assertEquals( "b", ss );
        expArray = new String[]{ null, "a", null, null, null, null, null, null };
        outArray = myArray.dump();
        assertArrayEquals( expArray, outArray );
    }

    /* Continuous add/remove operations in either positive or negative direction */
    @Test
    public void chaseTest() {
        ArrayDeque<String> myArray = new ArrayDeque<>();
        for( int i = 0; i < 3 * myArray.getCapacity(); i++ ) {
            myArray.addLast("a");
            String s = myArray.removeFirst();
            assertEquals( "a", s );
        }
        for( int i = 0; i < 3 * myArray.getCapacity(); i++ ) {
            myArray.addFirst("a");
            String s = myArray.removeLast();
            assertEquals( "a", s );
        }
    }

    /* Test get() */
    @Test
    public void getTest() {
        ArrayDeque<String> myArray = new ArrayDeque<>();
        myArray.addFirst("a");
        myArray.addLast("b");
        myArray.addLast("c");
        myArray.addFirst("d");

        assertEquals( "d", myArray.get(0) );
        assertEquals( "a", myArray.get(1) );
        assertEquals( "b", myArray.get(2) );
        assertEquals( "c", myArray.get(3) );
        assertEquals( null, myArray.get(4) );
        assertEquals( null, myArray.get(5) );
        assertEquals( null, myArray.get(6) );
        assertEquals( null, myArray.get(7) );
        assertEquals( null, myArray.get(8) );
    }

    public void main(String[] args) {
        jh61b.junit.TestRunner.runTests( "all", ArrayDeque.class );
    }
}
