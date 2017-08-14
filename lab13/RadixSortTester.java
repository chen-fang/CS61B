import org.junit.Test;
import static org.junit.Assert.*;

public class RadixSortTester {

    public static String[] toSort = {"a","b","c","lz","ye","wul", "mox","dfiz", "olizli"};

    public static void print(String[] str) {
        for (String s : str)
            System.out.print(s + " ");
        System.out.println();
    }

    public static void main(String[] args) {
        String[] sorted = RadixSort.sort(toSort);
        System.out.print("toSort: ");
        print(toSort);
        System.out.print("Sorted: ");
        print(sorted);
    }

}
