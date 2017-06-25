import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Created by BlackIce on 2017/6/24.
 */
public class TestArrayDeque1B {
    @Test
    public void addFirstTest() {
        ArrayDequeSolution<Integer> deque1 = new ArrayDequeSolution<>();
        StudentArrayDeque<Integer> deque2 = new StudentArrayDeque<>();
        int num = 100;
        for (int i = 0; i < num; i++) {
            Integer randomInt = StdRandom.uniform(0, 100);
            deque1.addFirst(randomInt);
            deque2.addFirst(randomInt);
        }
        assertEquals( deque1.size(), deque2.size() );
        for( int i = 0; i < num; i++ ) {
            assertEquals( deque1.get(i), deque2.get(i) );
        }
    }

    @Test
    public void addLastTest() {
        ArrayDequeSolution<Integer> deque1 = new ArrayDequeSolution<>();
        StudentArrayDeque<Integer> deque2 = new StudentArrayDeque<>();

        int num = 100;
        for (int i = 0; i < num; i++) {
            Integer randomInt = StdRandom.uniform(0, 100);
            deque1.addLast(randomInt);
            deque2.addLast(randomInt);
        }
        assertEquals( deque1.size(), deque2.size() );
        for( int i = 0; i < num; i++ ) {
            assertEquals( deque1.get(i), deque2.get(i) );
        }
    }

    /* Assume addFirstTest() are passed. */
    @Test
    public void removeFirstTest() {
        ArrayDequeSolution<Integer> deque1 = new ArrayDequeSolution<>();
        StudentArrayDeque<Integer> deque2 = new StudentArrayDeque<>();
        int num = 100;
        for (int i = 0; i < num; i++) {
            Integer randomInt = StdRandom.uniform(0, 100);
            deque1.addFirst(randomInt);
            deque2.addFirst(randomInt);
        }
        /* remove in inverse order */
        for(int i = 0; i < num; i++) {
            Integer d1 = deque1.removeFirst();
            Integer d2 = deque2.removeFirst();
            assertEquals( d1, d2 );
        }
    }

    /* Assume addLastTest() are passed. */
    @Test
    public void removeLastTest() {
        ArrayDequeSolution<Integer> deque1 = new ArrayDequeSolution<>();
        StudentArrayDeque<Integer> deque2 = new StudentArrayDeque<>();

        OperationSequence opSeq = new OperationSequence();

        int num = 100;
        for (int i = 0; i < num; i++) {
            Integer randomInt = StdRandom.uniform(0, 100);
            deque1.addLast(randomInt);
            deque2.addLast(randomInt);
        }
        /* remove in inverse order */
        for(int i = 0; i < num; i++) {
            DequeOperation dequeOp = new DequeOperation("removeLast");
            opSeq.addOperation(dequeOp);

            Integer d1 = deque1.removeLast();
            Integer d2 = deque2.removeLast();
            assertEquals( opSeq.toString(), d1, d2 );
        }
    }


    public static void main( String[] args ) {
        jh61b.junit.TestRunner.runTests("all", TestArrayDeque1B.class);
    }
}
