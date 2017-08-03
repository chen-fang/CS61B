package hw3.puzzle;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

final public class Board implements WorldState{
    final private static int BLANK = 0;
    final private int[][] _tiles;
    /** Constructs a board from an N-by-N array of tiles where
     *  tiles[i][j] = tile at rowi, column j
     */
    public Board(int[][] tiles) {
        int N = tiles.length;
        _tiles = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                _tiles[i][j] = tiles[i][j];
            }
    }

    /** Returns value of tile at row i, column j (or 0 if blank) */
    public int tileAt(int i, int j) {
        if (i < 0 || i >= size() || j < 0 || j >= size())
            throw new IllegalArgumentException();
        return _tiles[i][j];
    }

    /** Returns the board size N */
    public int size() {
        return _tiles.length;
    }

    /**
     * Returns neighbors of this board.
     * SPOILERZ: This is the answer.
     * Reference: http://joshh.ug/neighbors.html
     * Comment: have to replace variables names with readable words.
     */
    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int N = size();
        int row = -1;
        int col = -1;

        int[][] tile = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tile[i][j] = tileAt(i, j);
                if (tile[i][j] == BLANK) {
                    row = i;
                    col = j;
                }
            }
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (Math.abs(i - row) + Math.abs(j - col) - 1 == 0) {
                    /* swap */
                    tile[row][col] = tile[i][j];
                    tile[i][j] = BLANK;
                    Board neighbor = new Board(tile);
                    neighbors.enqueue(neighbor);
                    /* swap back */
                    tile[i][j] = tile[row][col];
                    tile[row][col] = BLANK;
                }
            }
        }
        return neighbors;
    }

    /** Get Row i (0-based) from goal (1-based) */
    private int getRow(int goal) {
        return (goal -1) / size();
    }

    /** Get Col j (0-based) from goal (1-based) */
    private int getCol(int goal) {
        return goal - 1 - getRow(goal) * size();
    }

    /** See full description on http://datastructur.es/sp17/materials/hw/hw3/hw3.html
     *  Returns the number of tiles in the wrong position
     */
    public int hamming() {
        int goal_boundary = size() * size();
        int sum = 0;
        for (int i = 0; i < size(); i++ ) {
            for (int j = 0; j < size(); j++) {
                int goal = i * size() + j + 1;
                int n = tileAt(i, j);
                if (goal !=  goal_boundary && n != goal) {
                    sum += 1;
                }
            }
        }
        return sum;
    }

    /** See full description on http://datastructur.es/sp17/materials/hw/hw3/hw3.html
     *  Returns the sum of the Manhattan distances (sum of the vertical and horizontal
     *  distance) from the tiles to their goal positions.
     */
    public int manhattan() {
        int sum = 0;
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                int n = tileAt(i,j);
                int row = getRow(n);
                int col = getCol(n);
                if (n != BLANK) {
                    int r = Math.abs(row - i);
                    int c = Math.abs(col - j);
                    sum += r + c;
                }
            }
        }
        return sum;
    }

    /** Estimated distance to goal. This method should simply return the
     *  result of manhattan() when submitted to Gradescope
     */
    @Override
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    /** Returns true if is this board the gola board */
    @Override
    public boolean isGoal() {
        int N = size();
        int goal = 1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int n = tileAt(i, j);
                if (i == N-1 && j == N-1) {
                    goal = 0;
                }
                if (n != goal)
                    return false;
                goal += 1;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object y) {
        if (this == y)
            return true;

        if (y == null || this.getClass() != y.getClass())
            return false;

        Board yy = (Board) y;
        int N = size();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tileAt(i,j) != yy.tileAt(i,j))
                    return false;
            }
        }
        return true;
    }


    /** Returns the string representation of the board. 
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    public static class Tests {
        @Test
        public void testSize() {
            int N = 3;
            Board b = new Board(new int[N][N]);
            assertEquals(N, b.size());
        }

        @Test
        public void testGetRowCol() {
            int N = 3;
            Board b = new Board(new int[N][N]);
            int goal01 = 2;
            assertEquals(0, b.getRow(goal01));
            assertEquals(1, b.getCol(goal01));

            int goal02 = 6;
            assertEquals(1, b.getRow(goal02));
            assertEquals(2, b.getCol(goal02));
        }


        @Test
        public void testDistance() {
            int[][] tile = new int[3][3];
            tile[0][0] = 8;
            tile[0][1] = 1;
            tile[0][2] = 3;
            tile[1][0] = 4;
            tile[1][1] = 0;
            tile[1][2] = 2;
            tile[2][0] = 7;
            tile[2][1] = 6;
            tile[2][2] = 5;

            Board board = new Board(tile);
            int h = board.hamming();
            assertEquals(5, h);

            int m = board.manhattan();
            assertEquals(10, m);
        }

        @Test
        public void testIsGoal() {
            int N = 3;
            int[][] tile = new int[N][N];
            int goal = 1;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    tile[i][j] = goal;
                    goal += 1;
                }
            }
            tile[N-1][N-1] = BLANK;

            Board board = new Board(tile);
            assertTrue(board.isGoal());

            board._tiles[0][0] = BLANK;
            board._tiles[2][2] = 1;
            assertFalse(board.isGoal());
        }

        @Test
        public void testNeighbors() {
            int[][] tile = new int[3][3];
            tile[0][0] = 8;
            tile[0][1] = 1;
            tile[0][2] = 3;
            tile[1][0] = 4;
            tile[1][1] = 0;
            tile[1][2] = 2;
            tile[2][0] = 7;
            tile[2][1] = 6;
            tile[2][2] = 5;

            Board board = new Board(tile);
            System.out.println(board);

            int i = 1;
            for (WorldState neighbor : board.neighbors()) {
                System.out.println("neighbor " + i);
                i += 1;
                System.out.println((Board)neighbor);
            }
        }

    }

    public static void main(String[] args) {
        int[][] tile = new int[3][3];
        tile[0][0] = 2;
        tile[0][1] = 0;
        tile[0][2] = 8;
        tile[1][0] = 1;
        tile[1][1] = 3;
        tile[1][2] = 5;
        tile[2][0] = 4;
        tile[2][1] = 6;
        tile[2][2] = 7;

        Board initial = new Board(tile);

        Solver solver = new Solver(initial);
        StdOut.println("Minimum number of moves = " + solver.moves());
        int i = 0;
        for (WorldState ws : solver.solution()) {
            StdOut.println("Step # " + i);
            i += 1;
            StdOut.println(ws);
        }
    }
}
