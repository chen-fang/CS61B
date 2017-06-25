import java.util.LinkedList;

/**
 * Isn't this solution kinda... cheating? Yes.
 * The aesthete will be especially alarmed by the fact that this
 * supposed ArrayDeque is actually using a LinkedList. SAD!
 */
public class ArrayDequeSolution<Item>
        extends LinkedList<Item>
        implements Deque<Item> {
    @Override
    public void addFirst(Item e) {
        super.addFirst(e);
    }

    @Override
    public void addLast(Item e) {
        super.addLast(e);
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    @Override
    public int size() {
        return super.size();
    }

    public void printDeque() {
        for (int i = 0; i < size(); i++) {
            System.out.print(get(i) + " ");
        }
        System.out.println();
    }

    @Override
    public Item removeFirst() {
        return super.removeFirst();
    }

    @Override
    public Item removeLast() {
        return super.removeLast();
    }

    @Override
    public Item get(int index) {
        return super.get(index);
    }
}