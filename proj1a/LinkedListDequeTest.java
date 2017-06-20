import static org.junit.Assert.*;
import org.junit.Test;

/** Performs some basic linked list tests. */
public class LinkedListDequeTest {

	@Test
	public void addFirstTest() {
		LinkedListDeque<String> myList = new LinkedListDeque<>();
		myList.addFirst("B");
		Object[] dumpArray = myList.dump();
		String[] expArray = new String[1];
		expArray[0] = "B";
		assertArrayEquals(expArray,dumpArray);

		myList.addFirst("A");
		dumpArray = myList.dump();
		expArray = new String[2];
		expArray[0] = "A";
		expArray[1] = "B";
		assertArrayEquals(expArray,dumpArray);
	}

	@Test
	public void addLastTest() {
		LinkedListDeque<String> myList = new LinkedListDeque<>();
		myList.addLast("A");
		Object[] dumpArray = myList.dump();
		String[] expArray = new String[1];
		expArray[0] = "A";
		assertArrayEquals(expArray,dumpArray);

		myList.addLast("B");
		dumpArray = myList.dump();
		expArray = new String[2];
		expArray[0] = "A";
		expArray[1] = "B";
		assertArrayEquals(expArray,dumpArray);
	}

	/* multiple tests: addFirst, addLast, isEmpty and size */
	@Test
	public void addTest() {
		LinkedListDeque<String> myList = new LinkedListDeque<>();
		myList.addFirst("A");
		myList.addLast("B");
		myList.addFirst("C");
		myList.addLast("D");
		myList.addLast("E");
		Object[] outputList = myList.dump();

		assertEquals(false, myList.isEmpty());
		assertEquals(5, myList.size());
		String[] expOutput = {"C","A","B","D","E"};
		assertArrayEquals(expOutput,outputList);
	}


	/* Utility method for printing out empty checks. */
	@Test
	public void isEmptyTest() {
		LinkedListDeque<String> myList = new LinkedListDeque<String>();
		assertEquals(true, myList.isEmpty());
		myList.addFirst("Cool");
		assertEquals( false, myList.isEmpty() );
	}

	/* Utility method for printing out empty checks. */
	@Test
	public void sizeTest() {
		LinkedListDeque<String> myList = new LinkedListDeque<String>();
		assertEquals(0, myList.size());
		myList.addFirst("Cool");
		assertEquals( 1, myList.size() );
		myList.addLast("Stuff");
		assertEquals( 2, myList.size() );
	}

	/* multiple tests: addFirst, addLast, isEmpty and size */
	@Test
	public void getTest() {
		LinkedListDeque<String> myList = new LinkedListDeque<>();
		assertEquals(null, myList.get(0));
		assertEquals(null, myList.get(1));

		myList.addFirst("A");
		myList.addLast("B");
		myList.addFirst("C");
		myList.addLast("D");
		myList.addLast("E");

		int size = myList.size();
		assertEquals(null, myList.get(size));
		assertEquals(null, myList.get(size+10));
		assertEquals(null, myList.get(-5));

		assertEquals("C", myList.get(0));
		assertEquals("A", myList.get(1));
		assertEquals("B", myList.get(2));
		assertEquals("D", myList.get(3));
		assertEquals("E", myList.get(4));
	}

	@Test
	public void removeFirstTest() {
		LinkedListDeque<String> myList = new LinkedListDeque<>();
		assertEquals(null, myList.removeFirst() );

		myList.addFirst("A");
		myList.addLast("B");
		myList.addFirst("C");
		myList.addLast("D");
		myList.addLast("E");

		assertEquals("C", myList.removeFirst());
		assertEquals("A", myList.removeFirst());

		assertEquals(3, myList.size());
		Object[] list_content = myList.dump();
		String[] exp_content = {"B","D","E"};
		assertArrayEquals(exp_content,list_content);

		assertEquals("B", myList.removeFirst());
		assertEquals("D", myList.removeFirst());
		assertEquals("E", myList.removeFirst());
		assertEquals(0, myList.size());

		assertEquals(null, myList.removeFirst());
		assertEquals(0, myList.size());
	}

	@Test
	public void removeLastTest() {
		LinkedListDeque<String> myList = new LinkedListDeque<>();
		assertEquals(null, myList.removeLast() );

		myList.addFirst("A");
		myList.addLast("B");
		myList.addFirst("C");
		myList.addLast("D");
		myList.addLast("E");

		assertEquals("E", myList.removeLast());
		assertEquals("D", myList.removeLast());

		assertEquals(3, myList.size());
		Object[] list_content = myList.dump();
		String[] exp_content = {"C","A","B"};
		assertArrayEquals(exp_content,list_content);

		assertEquals("B", myList.removeLast());
		assertEquals("A", myList.removeLast());
		assertEquals("C", myList.removeLast());
		assertEquals(0, myList.size());

		assertEquals(null, myList.removeLast());
		assertEquals(0, myList.size());
	}

	@Test
	public void getRecursiveTest() {
		LinkedListDeque<String> myList = new LinkedListDeque<>();
		myList.addFirst("A");
		myList.addLast("B");
		myList.addFirst("C");
		myList.addLast("D");
		myList.addLast("E");

		for( int i = 0; i < myList.size(); i++ ) {
			assertEquals( myList.get(i), myList.getRecursive(i) );
		}
	}

	/** Adds a few things to the list, checking isEmpty() and size() are correct,
	  * finally printing the results. 
	  *
	  * && is the "and" operation. *//*
	public static void addIsEmptySizeTest() {
		System.out.println("Running add/isEmpty/Size test.");
		System.out.println("Make sure to uncomment the lines below (and delete this print statement).");
		*//*
		LinkedListDeque<String> lld1 = new LinkedListDeque<String>();

		boolean passed = checkEmpty(true, lld1.isEmpty());

		lld1.addFirst("front");
		
		// The && operator is the same as "and" in Python.
		// It's a binary operator that returns true if both arguments true, and false otherwise.
		passed = checkSize(1, lld1.size()) && passed;
		passed = checkEmpty(false, lld1.isEmpty()) && passed;

		lld1.addLast("middle");
		passed = checkSize(2, lld1.size()) && passed;

		lld1.addLast("back");
		passed = checkSize(3, lld1.size()) && passed;

		System.out.println("Printing out deque: ");
		lld1.printDeque();

		printTestStatus(passed);
		*//*
	}

	*//** Adds an item, then removes an item, and ensures that dll is empty afterwards. *//*
	public static void addRemoveTest() {

		System.out.println("Running add/remove test.");

		System.out.println("Make sure to uncomment the lines below (and delete this print statement).");
		*//*
		LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
		// should be empty 
		boolean passed = checkEmpty(true, lld1.isEmpty());

		lld1.addFirst(10);
		// should not be empty 
		passed = checkEmpty(false, lld1.isEmpty()) && passed;

		lld1.removeFirst();
		// should be empty 
		passed = checkEmpty(true, lld1.isEmpty()) && passed;

		printTestStatus(passed);
		*//*
	}
*/
	public static void main(String[] args) {
		jh61b.junit.TestRunner.runTests("all", LinkedListDeque.class );
	}
} 