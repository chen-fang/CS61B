package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    int _T;
    int[] _percolation;
    /** perform T independent experiments on an N-by-N grid */
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0)
            throw new IllegalArgumentException("N<=0 or T <=0");
        _percolation = new int[T];
        for (int i = 0; i < T; i++) {
            Percolation perco = new Percolation(N);
            int num = 0;
            while (perco.numberOfOpenSites() != N*N && !perco.percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                if (!perco.isOpen(row, col)) {
                    perco.open(row, col);
                    num += 1;
                }
            }
            _percolation[i] = num;
        }
    }

    /** sample mean of percolation threshold */
    public double mean() {
        return StdStats.mean(_percolation);
    }

    /** sample standard deviation of percolation threshold */
    public double stddev() {
        return StdStats.stddev(_percolation);
    }

    /** low  endpoint of 95% confidence interval */
    public double confidenceLow() {
        return mean() - 1.96 * Math.sqrt(stddev() / _T);
    }

    /** high endpoint of 95% confidence interval */
    public double confidenceHigh() {
        return mean() + 1.96 * Math.sqrt(stddev() / _T);
    }
}                       
