package hw3.puzzle;

public class Board implements WorldState{
    public Board(int[][] tiles) {

    }

    public int tileAt(int i, int j) {
        throw new UnsupportedOperationException();
    }

    public int size() {
        throw new UnsupportedOperationException();
    }

    public Iterable<WorldState> neighbors() {
        throw new UnsupportedOperationException();
    }

    public int hamming() {
        throw new UnsupportedOperationException();
    }

    public int manhattan() {
        throw new UnsupportedOperationException();
    }

    public int estimatedDistanceToGoal() {
        throw new UnsupportedOperationException();
    }

    public boolean isGoal() {
        throw new UnsupportedOperationException();
    }

    public boolean equals(Object y) {
        throw new UnsupportedOperationException();
    }

    public String toString() {
        throw new UnsupportedOperationException();
    }
    /** Returns the string representation of the board. 
      * Uncomment this method. */
    /*public String toString() {
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
    }*/

}
