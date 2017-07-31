package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    int _T;
    int[] _percolationLog;
    /** perform T independent experiments on an N-by-N grid */
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0)
            throw new IllegalArgumentException("N<=0 or T <=0");
        _T = T;
        _percolationLog = new int[T];
        for (int i = 0; i < T; i++) {
            Percolation perco = new Percolation(N);
            int num = 0;
            while (!perco.percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                if (!perco.isOpen(row, col)) {
                    perco.open(row, col);
                    num += 1;
                }
            }
            _percolationLog[i] = num;
        }
    }

    /** sample mean of percolation threshold */
    public double mean() {
        return StdStats.mean(_percolationLog);
    }

    /** sample standard deviation of percolation threshold */
    public double stddev() {
        return StdStats.stddev(_percolationLog);
    }

    /** low  endpoint of 95% confidence interval */
    public double confidenceLow() {
        return mean() - 1.96 * Math.sqrt(stddev() / _T);
    }

    /** high endpoint of 95% confidence interval */
    public double confidenceHigh() {
        return mean() + 1.96 * Math.sqrt(stddev() / _T);
    }

    public static void main(String[] args) {
        int N = 20;
        int T = 110;
        PercolationStats percolationTest = new PercolationStats(N, T);
        System.out.println("Percolation test with " + N + " by " + N);
        for (int i = 0; i < T; i++) {
            System.out.println("percolate at " + percolationTest._percolationLog[i]);
        }
        System.out.println("mean = " + percolationTest.mean());
        System.out.println("standard deviation = " + percolationTest.stddev());
        System.out.println("confidence low = " + percolationTest.confidenceLow());
        System.out.println("confidence high = " + percolationTest.confidenceHigh());
        System.out.println("estimated threshold = " + percolationTest.mean() / (N*N));
    }
}                       
