package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        int capacity = 10;
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<Integer>(capacity);
        for (int i = 0; i < capacity; i++) {
            arb.enqueue(i);
        }
        for( int elem : arb ) {
            System.out.println(elem);
        }
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
