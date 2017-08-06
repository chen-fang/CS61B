package hw4.hash;

import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /* TODO: Write a utility function that returns true if the given oomages 
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        int[] array = new int[M];
        for (int i = 0; i < M; i++) {
            array[i] = 0;
        }
        for (Oomage s : oomages) {
            int bucketNumber = (s.hashCode() & 0x7FFFFFF) % M;
            array[bucketNumber] += 1;
        }
        int smallest = array[0];
        int biggest = array[0];
        for (int n : array) {
            if (n < smallest) smallest = n;
            if (n > biggest) biggest = n;
        }

        boolean test1 = smallest >= oomages.size() / 50;
        boolean test2 = biggest <= oomages.size() / 2.5;
        return test1 && test2;
    }

    public static void main(String[] args) {
        int x = -2;
        System.out.println(x & 0x7FFFFFF);
    }
}
