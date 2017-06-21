/**
 * Created by BlackIce on 2017/6/20.
 */
public class ArrayDeque<Item> {
    /* instance variables */
    private int size;
    private int capacity;
    private Item[] front;
    private int first;
    private int last;

    /* for test purpose */
    public int getCapacity() { return capacity; }
    public int getFirst() { return first; }
    public int getLast() { return last; }
    public Item[] dump() { return front; }

    /* Default constructor without arguments: create empty Deque */
    ArrayDeque() {
        size = 0;
        capacity = 8;
        front = (Item[]) new Object[capacity];
        first = 0;
        last = first + 1;
    }

    /* Helper functions to compute indices in circular structure */
    private int prevIndex(int currentIndex) {
        int index = currentIndex - 1;
        if( index < 0 ) {
            index = capacity - 1;
        }
        return index;
    }

    private int nextIndex(int currentIndex) {
        int index = currentIndex + 1;
        if( index >= capacity ) {
            index = index - capacity;
        }
        return index;
    }

    /* Adds an item to the front of the Deque */
    public void addFirst(Item value) {
        front[first] = value;
        first = prevIndex(first);
        size += 1;
    }

    /* Adds an item to the back of the Deque */
    public void addLast(Item value) {
        front[last] = value;
        last = nextIndex(last);
        size += 1;
    }

    /* Returns true if deque is empty, false otherwise */
    public boolean isEmpty() {
        if(size == 0)
            return true;
        return false;
    }

    /* Returns the number of items in the Deque */
    public int size() {
        return size;
    }



    /* Prints the items in the Deque from first to last, separated by a space */
    public void printDeque() {
        for( int i = 0; i < size; i++ ) {
            System.out.print( get(i) + " " );
        }
    }



    /* Removes and returns the item at the front of the Deque.
       If no such item exists, returns null
     */
    public Item removeFirst() {
        first = nextIndex(first);
        Item p = front[first];
        front[first] = null;
        size -= 1;
        return p;
    }

    /* Removes and returns the item at the back of the Deque.
       If no such item exists, returns null
     */
    public Item removeLast() {
        last = prevIndex(last);
        Item p = front[last];
        front[last] = null;
        size -= 1;
        return p;
    }

    /* Gets the item at the given index, where 0 is the front, 1 is the next item,
       and so forth. If no such item exists, returns null. Must not alter the deque!
     */
    public Item get(int index) {
        if( index >= 0 && index < size ) {
            int i = nextIndex(first + index);
            return front[i];
        }
        return null;
    }

    public static void main(String[] args) {
        ArrayDeque<String> myArray = new ArrayDeque<>();
        myArray.addFirst("a");
        myArray.addLast("b");
        myArray.addLast("c");
        myArray.addFirst("d");
        myArray.printDeque();
    }
}
