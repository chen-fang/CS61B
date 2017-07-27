package hw2;                       

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import org.junit.Test;
import static org.junit.Assert.*;

public class Percolation {
    private int _N;
    private int _numOpen;
    private int _top;
    private int _end;
    private boolean[] _openLog;
    private WeightedQuickUnionUF _connection;


    /**
     * create N-by-N grid, with all sites initially blocked
     */
    public Percolation(int N) {
        if (N <= 0)
            throw new IllegalArgumentException("N <= 0");

        _N = N;
        _numOpen = 0;
        _top = N*N;
        _end = _top + 1;
        int num = N * N;
        _openLog = new boolean[num];
        for (int i = 0; i < num; i++) {
            _openLog[i] = false;
        }
        _connection = new WeightedQuickUnionUF(num+2);
        for (int i = 0; i < _N; i++) {
            int topRowIndex = index(0, i);
            int endRowIndex = index(_N-1, i);
            _connection.union(_top, topRowIndex);
            _connection.union(_end, endRowIndex);
        }
    }

    private int index(int row, int col) {
        return row * _N + col;
    }

    private boolean isNextTo(int row1, int col1, int row2, int col2) {
        /* if (row,col) is invalid, simply return false */
        if (row2 < 0 || row2 >= _N || col2 < 0 || col2 >= _N)
            return false;
        if (row1 == row2)
            return Math.abs(col1 - col2) == 1;
        else if (col1 == col2)
            return Math.abs(row1 - row2) == 1;
        return false;
    }

    /**
     * is the site (row2, col2) open and next to (row1, col1)
     */
    private boolean isOpenNextTo(int row1, int col1, int row2, int col2) {
        return isNextTo(row1, col1, row2, col2) && isOpen(row2, col2);
    }

    private void validate(int row, int col) {
        if (row < 0 || row >= _N || col <0 || col >= _N)
            throw new IllegalArgumentException("row/col is out of bounds");
    }

    /**
     * open the site (row, col) if it is not open already
     */
    public void open(int row, int col) {
        validate(row, col);
        if (isOpen(row, col))
            return;
        _numOpen += 1;
        int index = index(row, col);
        _openLog[index] = true;
        if (isOpenNextTo(row, col, row - 1, col))
            _connection.union(index, index(row - 1, col));
        if (isOpenNextTo(row, col, row + 1, col))
            _connection.union(index, index(row + 1, col));
        if (isOpenNextTo(row, col, row, col - 1))
            _connection.union(index, index(row, col - 1));
        if (isOpenNextTo(row, col, row, col + 1))
            _connection.union(index, index(row, col + 1));
    }

    /**
     * is the site (row, col) open?
     */
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return _openLog[index(row, col)];
    }


    /**
     * is the site (row, col) full?
     */
    public boolean isFull(int row, int col) {
        validate(row, col);
        return isOpen(row,col) && _connection.connected(index(row,col), _top);
    }

    /**
     * number of open sites
     */
    public int numberOfOpenSites() {
        return _numOpen;
    }

    /**
     * does the system percolate?
     */
    public boolean percolates() {
        return _connection.connected(_top, _end);
    }

    // unit testing (not required)
    public static class tests {
        @Test
        public void testConstruct() {
            Percolation perco = new Percolation(3);
            assertFalse(perco.isOpen(0,0));
            assertFalse(perco.isFull(0,0));
        }

        @Test
        public void testIsNextTo() {
            Percolation perco = new Percolation(3);
            assertTrue(perco.isNextTo(1,1,1,0));
            assertTrue(perco.isNextTo(1,1,1,2));
            assertTrue(perco.isNextTo(1,1,0,1));
            assertTrue(perco.isNextTo(1,1,2,1));

            assertFalse(perco.isNextTo(0,0,0,-1));
            assertFalse(perco.isNextTo(0,0,-1,0));
            assertFalse(perco.isNextTo(0,0,1,1));
        }

        @Test
        public void testIsOpen() {
            Percolation perco = new Percolation(3);
            perco.open(1,1);
            assertTrue(perco.isOpen(1,1));
            assertFalse(perco.isOpen(0,1));
            assertFalse(perco.isOpen(0,3));
        }

        @Test
        public void testConnected() {
            Percolation perco = new Percolation(3);
            perco.open(0,1);
            perco.open(1,1);
            perco.open(1,2);
            assertTrue(perco._connection.connected(perco.index(1,1),perco.index(1,2)));
            assertFalse(perco._connection.connected(perco.index(1,1),perco.index(2,2)));

            assertTrue(perco._connection.connected(perco.index(1,2),perco._top));
        }

        @Test
        public void testBackWash() {
            Percolation perco = new Percolation(10);
            for (int i = 0; i < perco._N; i++) {
                perco.open(i,0);
            }
            perco.open(9,2);
            assertFalse(perco.isFull(9,1));
            assertFalse(perco.isFull(9,2));
            assertFalse(perco.isFull(8,2));

            perco.open(8,2);
            assertFalse(perco.isFull(8,2));
            assertFalse(perco.isFull(9,2));
        }
    }
}
