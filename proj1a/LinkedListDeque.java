/**
 * Created by BlackIce on 2017/6/19.
 */
public class LinkedListDeque<Item> {
    /* Internal storage structure */
    private class Node {
        public Item value;
        public Node next;
        public Node prev;

        public Node(Item value ) {
            this.value = value;
        }
    }

    /* instance variables */
    private int size;
    private Node sentinel;


    /* Default constructor without arguments: create empty Deque */
    LinkedListDeque() {
        sentinel = new Node(null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    /* Adds an item to the front of the Deque */
    public void addFirst(Item value) {
        Node p = new Node(value);
        p.next = sentinel.next;
        p.prev = sentinel;
        sentinel.next.prev = p;
        sentinel.next = p;
        size += 1;
    }

    /* Adds an item to the back of the Deque */
    public void addLast(Item value) {
        Node p = new Node(value);
        p.next = sentinel;
        p.prev = sentinel.prev;
        sentinel.prev.next = p;
        sentinel.prev = p;
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
        Node p = sentinel.next;
        while(p.value != null) {
            System.out.print(p.value + " ");
            p = p.next;
        }
    }

    /* Removes and returns the item at the front of the Deque.
       If no such item exists, returns null
    n */
    public Item removeFirst() {
        if( size == 0 )
            return null;
        Node firstNode = sentinel.next;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        size -= 1;
        return firstNode.value;
    }

    /* Removes and returns the item at the back of the Deque.
       If no such item exists, returns null
     */
    public Item removeLast() {
        if( size == 0 )
            return null;
        Node lastNode = sentinel.prev;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        size -= 1;
        return lastNode.value;
    }

    /* Gets the item at the given index, where 0 is the front, 1 is the next item,
       and so forth. If no such item exists, returns null. Must not alter the deque!
     */
    public Item get(int index) {
        if(index<0 || index >= size)
            return null;
        Node p = sentinel.next;
        for(int i=0; i<index; i++) {
            p = p.next;
        }
        return p.value;
    }

    /* Recursive version of get() */
    public Item getRecursive(int index ) {
        Node runningNode = getRecursiveHelper(index, sentinel.next);
        return runningNode.value;
    }
    private Node getRecursiveHelper(int index, Node p) {
        if(index == 0)
            return p;
        return getRecursiveHelper(index-1, p.next);
    }

    /* For test purpose */
    public Item[] dump() {
        Item[] dumpArray = (Item[]) new Object[size];
        Node p = sentinel.next;
        for( int i = 0; i < size; i++ ) {
            dumpArray[i] = p.value;
            p = p.next;
        }
        return dumpArray;
    }

    public static void main(String[] args) {
        LinkedListDeque<String> myList = new LinkedListDeque<>();
        myList.addFirst("A");
        myList.addLast("B");
        myList.addFirst("C");
        myList.addLast("D");
        myList.addLast("E");
        myList.printDeque();
    }
}
