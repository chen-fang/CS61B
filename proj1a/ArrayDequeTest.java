import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Created by BlackIce on 2017/6/21.
 */
public class ArrayDequeTest {
    /* Test addFirst() and prevIndex() without increaseCapacity */
    @Test
    public void addFirstTest01() {
        ArrayDeque<String> myArray = new ArrayDeque<>();
        assertEquals(0, myArray.size());
        assertEquals(8, myArray.getCapacity());

        myArray.addFirst( "a" );
        assertEquals(1, myArray.size());
        assertEquals( 6, myArray.getFirst() );
        assertEquals( 0, myArray.getLast() );

        myArray.addFirst( "b" );
        assertEquals(2, myArray.size());
        assertEquals( 5, myArray.getFirst() );
        assertEquals( 0, myArray.getLast() );

        String[] expArray = { null, null, null, null, null, null, "b", "a" };
        Object[] outArray = myArray.dump();
        assertArrayEquals( expArray, outArray );
    }

    /* Test addLast() and lastIndex() without increaseCapacity */
    @Test
    public void addLastTest01() {
        ArrayDeque<String> myArray = new ArrayDeque<>();
        assertEquals(0, myArray.size());
        assertEquals(8, myArray.getCapacity());

        myArray.addLast( "a" );
        assertEquals(1, myArray.size());
        assertEquals( 7, myArray.getFirst() );
        assertEquals( 1, myArray.getLast() );

        myArray.addLast( "b" );
        assertEquals(2, myArray.size());
        assertEquals( 7, myArray.getFirst() );
        assertEquals( 2, myArray.getLast() );

        String[] expArray = { "a", "b", null, null, null, null, null, null };
        Object[] outArray = myArray.dump();
        assertArrayEquals( expArray, outArray );
    }

    /* Test addFirst() & addLast() together without increaseCapacity */
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
        assertEquals( 3, myArray.getFirst() );
        assertEquals( 3, myArray.getLast() );
        String[] expArray = { "b", "c", "f", null, "g", "e", "d", "a" };
        Object[] outArray = myArray.dump();
        assertArrayEquals( expArray, outArray );
    }

    /* Test removeFirst() without decreaseCapacity*/
    @Test
    public void removeFirstTest01() {
        ArrayDeque<String> myArray = new ArrayDeque<>();
        myArray.addFirst("a");
        String s = myArray.removeFirst();
        assertEquals( 0, myArray.size() );
        assertEquals( 7, myArray.getFirst() );
        assertEquals( "a", s );
        String[] expArray = { null, null, null, null, null, null, null, null };
        Object[] outArray = myArray.dump();
        assertArrayEquals( expArray, outArray );

        myArray.addFirst("a");
        myArray.addFirst("b");
        String ss = myArray.removeFirst();
        assertEquals( 1, myArray.size() );
        assertEquals( 6, myArray.getFirst() );
        assertEquals( "b", ss );
        expArray = new String[]{ null, null, null, null, null, null, null, "a" };
        outArray = myArray.dump();
        assertArrayEquals( expArray, outArray );

        ss = myArray.removeFirst();
        assertEquals( 0, myArray.size() );
        assertEquals( 7, myArray.getFirst() );
        assertEquals( "a", ss );
        expArray = new String[]{ null, null, null, null, null, null, null, null };
        outArray = myArray.dump();
        assertArrayEquals( expArray, outArray );

        myArray.addLast( "c" );
        myArray.addLast( "d" );
        ss = myArray.removeFirst();
        assertEquals( 1, myArray.size() );
        assertEquals( 0, myArray.getFirst() );
        assertEquals( "c", ss );
        expArray = new String[]{ null, "d", null, null, null, null, null, null };
        outArray = myArray.dump();
        assertArrayEquals( expArray, outArray );
    }

    /* Test removeLast() without decreaseCapacity*/
    @Test
    public void removeLastTest() {
        ArrayDeque<String> myArray = new ArrayDeque<>();
        myArray.addLast("a");
        String s = myArray.removeLast();
        assertEquals( 0, myArray.size() );
        assertEquals( 0, myArray.getLast() );
        assertEquals( "a", s );
        String[] expArray = { null, null, null, null, null, null, null, null };
        Object[] outArray = myArray.dump();
        assertArrayEquals( expArray, outArray );

        myArray.addLast("a");
        myArray.addLast("b");
        String ss = myArray.removeLast();
        assertEquals( 1, myArray.size() );
        assertEquals( 1, myArray.getLast() );
        assertEquals( "b", ss );
        expArray = new String[]{ "a", null, null, null, null, null, null, null };
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


    /* increaseCapacity() is called when the current buffer is fully occupied.
       GENERAL CASE: test "increaseCapacity" method when neither "first"
                     nor "last" crosses either end of the buffer.
       Example:
       Start with an array:
       [array] = { 0 1 2 3 4 5 -2 -1 }
       where:
       positive numbers are set by addLast()
       negative numbers are set by addFirst()
       If one more element is added, for instance, -3, the new array should be:
       [array] = { -2 -1 0 1 2 3 4 6 x x x x x x x -3 }
     */
    @Test
    public void increaseCapacityTest() {
        ArrayDeque<String> myArray = new ArrayDeque<>();
        myArray.addLast("0");
        myArray.addLast("1");
        myArray.addLast("2");
        myArray.addLast("3");
        myArray.addLast("4");
        myArray.addLast("5");
        myArray.addFirst("-1");
        myArray.addFirst("-2");

        assertEquals(8, myArray.size());
        assertEquals(8, myArray.getCapacity());
        String[] expArray = new String[]{ "0","1","2", "3", "4", "5", "-2", "-1" };
        Object[] outArray = myArray.dump();
        assertArrayEquals(expArray, outArray);

        myArray.addFirst("-3");
        assertEquals(9, myArray.size());
        assertEquals(16, myArray.getCapacity());
        expArray = new String[]{ "-2", "-1","0","1","2", "3", "4", "5",
                null, null, null, null, null, null, null, "-3" };
        outArray = myArray.dump();
        assertArrayEquals(expArray, outArray);

        myArray.printDeque();
    }


    /* increaseCapacity() is called when the current buffer is fully occupied.
       SPECIAL CASE #01: ArrayDeque is filled by addLast() only.
       
       Example:
       Start with an array:
       [array] = { 0 1 2 3 4 5 6 7 }

       If one more element is added, for instance, -1, the new array should be:
       [array] = { 0 1 2 3 4 5 6 7 x x x x x x x -1 }
     */
    @Test
    public void addLastTest02() {
        ArrayDeque<String> myArray = new ArrayDeque<>();
        myArray.addLast("0");
        myArray.addLast("1");
        myArray.addLast("2");
        myArray.addLast("3");
        myArray.addLast("4");
        myArray.addLast("5");
        myArray.addLast("6");
        myArray.addLast("7");
        assertEquals(8, myArray.size());
        assertEquals(8, myArray.getCapacity());
        String[] expArray = new String[]{ "0","1","2", "3", "4", "5", "6", "7" };
        Object[] outArray = myArray.dump();
        assertArrayEquals(expArray, outArray);

        myArray.addFirst("-1");
        assertEquals(9, myArray.size());
        assertEquals(16, myArray.getCapacity());
        expArray = new String[]{ "0","1","2", "3", "4", "5", "6", "7",
                null,null,null,null,null,null,null, "-1"};
        outArray = myArray.dump();
        assertArrayEquals(expArray, outArray);
    }

    /* increaseCapacity() is called when the current buffer is fully occupied.
       SPECIAL CASE #02: ArrayDeque is filled by addFirst() only.
           
       Example:
       Start with an array:
       [array] = { 7 6 5 4 3 2 1 0 }
    
       If one more element is added, for instance, -1, the new array should be:
       [array] = { 7 6 5 4 3 2 1 0 x x x x x x x -1 }
         */
    @Test
    public void addFirstTest02() {
        ArrayDeque<String> myArray = new ArrayDeque<>();
        myArray.addFirst("0");
        myArray.addFirst("1");
        myArray.addFirst("2");
        myArray.addFirst("3");
        myArray.addFirst("4");
        myArray.addFirst("5");
        myArray.addFirst("6");
        myArray.addFirst("7");
        assertEquals(8, myArray.size());
        assertEquals(8, myArray.getCapacity());
        String[] expArray = new String[]{ "7","6","5", "4", "3", "2", "1", "0" };
        Object[] outArray = myArray.dump();
        assertArrayEquals(expArray, outArray);

        myArray.addFirst("-1");
        assertEquals(9, myArray.size());
        assertEquals(16, myArray.getCapacity());
        expArray = new String[]{ "7","6","5", "4", "3", "2", "1", "0",
                null,null,null,null,null,null,null, "-1"};
        outArray = myArray.dump();
        assertArrayEquals(expArray, outArray);
    }


    /* Assumption: increaseCapacity() works properly.
       GENERAL CASE: test "decreaseCapacity" method when neither "first"
                     nor "last" crosses either end of the buffer.
       Example:
       Start with an array with size of 9 and capacity of 16:
       [array] = { -1 0 1 2 3 4 5 6 x x x x x x x -2 }
       When 5 elements are removed with removeLast(), [array] is reduced to
       [array] = { -1 0 1 x x x x x x x x x x x x -2 }
       with size of 4 and capacity of 16.
       If one more removeLast() is called, the "capacity" should be halved
       since usage factor <= 0.25. The new array should be:
       [expArray] = { -2 -1 0 x x x x x }
     */
    @Test
    public void decreaseCapacityTest() {
        ArrayDeque<String> myArray = new ArrayDeque<>();
        myArray.addLast("0");
        myArray.addLast("1");
        myArray.addLast("2");
        myArray.addLast("3");
        myArray.addLast("4");
        myArray.addLast("5");
        myArray.addLast("6");
        myArray.addFirst("-1");
        myArray.addFirst("-2");
        assertEquals(9, myArray.size());
        assertEquals(16, myArray.getCapacity());
        /* remove 5 elements */
        myArray.removeLast();
        myArray.removeLast();
        myArray.removeLast();
        myArray.removeLast();
        myArray.removeLast();
        assertEquals(4, myArray.size());
        assertEquals(16, myArray.getCapacity());
        /* if remove one more, usage factor will drop to 3/16 < 25% */
        myArray.removeLast();
        assertEquals(3, myArray.size());
        assertEquals(8, myArray.getCapacity());
        String[] expArray = new String[]{"-2","-1","0",null,null,null,null,null};
        Object[] outArray = myArray.dump();
        assertArrayEquals(expArray,outArray);
        assertEquals(7, myArray.getFirst());
        assertEquals(3, myArray.getLast());
    }


    /* Assumption: increaseCapacity() works properly.
       SPECIAL CASE #01: test "decreaseCapacity" method when "first" crosses
                         right end of the buffer.
       Example:
       Start with an array with size of 9 and capacity of 16:
       [array] = { -1 0 1 2 3 4 5 6 x x x x x x x -2 }
       When 5 elements are removed FROM THE FRONT, [array] is reduced to
       [array] = { x x x x 3 4 5 6 x x x x x x x x } 
       with size of 4 and capacity of 16.
       If one more removeFirst() is called, the "capacity" should be halved since
       usage factor <= 0.25. The new array should be:
       [expArray] = { 4 5 6 x x x x x }
     */
    @Test
    public void removeFirstTest02() {
        ArrayDeque<String> myArray = new ArrayDeque<>();
        myArray.addLast("0");
        myArray.addLast("1");
        myArray.addLast("2");
        myArray.addLast("3");
        myArray.addLast("4");
        myArray.addLast("5");
        myArray.addLast("6");
        myArray.addFirst("-1");
        myArray.addFirst("-2");
        assertEquals(9, myArray.size());
        assertEquals(16, myArray.getCapacity());
        /* remove 5 elements */
        myArray.removeFirst();
        myArray.removeFirst();
        myArray.removeFirst();
        myArray.removeFirst();
        myArray.removeFirst();
        assertEquals(4, myArray.size());
        assertEquals(16, myArray.getCapacity());
        /* with one more remove(), usage factor will drop to 3/16 < 25% */
        myArray.removeFirst();
        assertEquals(3, myArray.size());
        assertEquals(8, myArray.getCapacity());
        String[] expArray = new String[]{"4","5","6",null,null,null,null,null};
        Object[] outArray = myArray.dump();
        assertArrayEquals(expArray,outArray);
        assertEquals(7, myArray.getFirst());
        assertEquals(3, myArray.getLast());
    }

    /* Assumption: increaseCapacity() works properly.
       SPECIAL CASE #01: test "decreaseCapacity" method when "first" crosses
                         right end of the buffer.
       Example:
       Start with an array with size of 9 and capacity of 16:
       [array] = { -1 0 1 2 3 4 5 6 x x x x x x x -2 }
       When 5 elements are removed FROM THE FRONT, [array] is reduced to
       [array] = { x x x x 3 4 5 6 x x x x x x x x }
       with size of 4 and capacity of 16.
       If one more removeLast() is called, the "capacity" should be halved since
       usage factor <= 0.25. The new array should be:
       [expArray] = { 3 4 5 x x x x x x }
     */
    @Test
    public void removeLastTest02() {
        ArrayDeque<String> myArray = new ArrayDeque<>();
        myArray.addLast("0");
        myArray.addLast("1");
        myArray.addLast("2");
        myArray.addLast("3");
        myArray.addLast("4");
        myArray.addLast("5");
        myArray.addLast("6");
        myArray.addFirst("-1");
        myArray.addFirst("-2");
        assertEquals(9, myArray.size());
        assertEquals(16, myArray.getCapacity());
        /* remove 5 elements */
        myArray.removeFirst();
        myArray.removeFirst();
        myArray.removeFirst();
        myArray.removeFirst();
        myArray.removeFirst();
        assertEquals(4, myArray.size());
        assertEquals(16, myArray.getCapacity());
        /* with one more remove(), usage factor will drop to 3/16 < 25% */
        myArray.removeLast();
        assertEquals(3, myArray.size());
        assertEquals(8, myArray.getCapacity());
        String[] expArray = new String[]{"3","4","5",null,null,null,null,null};
        Object[] outArray = myArray.dump();
        assertArrayEquals(expArray,outArray);
        assertEquals(7, myArray.getFirst());
        assertEquals(3, myArray.getLast());
    }

    public void main(String[] args) {
        jh61b.junit.TestRunner.runTests( "all", ArrayDeque.class );
    }
}
