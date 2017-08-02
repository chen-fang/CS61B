package hw3.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class Solver {
    WorldState _initial;
    Set<WorldState> _solution;

    /** search node to support A* algorithm */
    private class SearchNode {
        public WorldState state; /* a WorldState */
        public int moves; /* the number of moves made to reach this world state from the initial state */
        public SearchNode prev; /* a reference to the previous search node */

        public SearchNode(WorldState state, int moves, SearchNode prev) {
            this.state = state;
            this.moves = moves;
            this.prev = prev;
        }
    }

    /** compare method for WorldState objects */
    private class SearchNodeComparator implements Comparator<SearchNode> {
        @Override
        public int compare(SearchNode node1, SearchNode node2) {
            int priority1 = node1.moves + node1.state.estimatedDistanceToGoal();
            int priority2 = node2.moves + node2.state.estimatedDistanceToGoal();
            return priority1 - priority2;
        }
    }

    /** Constructor which solves the puzzle, computing everything necessary for
     *  moves() and solution() to NOT have to solve the problem again. Solves the
     *  puzzle using the A* algorithm. Assumes a solution exists.
     */
    public Solver(WorldState initial) {
        // initialize the problem
        this._initial = initial;
        this._solution = new HashSet<>();
        MinPQ<SearchNode> queue = new MinPQ<>(new SearchNodeComparator());
        queue.insert(new SearchNode(initial, 0, null));
    }

    /** Returns the minimum number of moves to solve the puzzle starting at the
     *  initial WorldState
     *
     */
    public int moves() {
        return _initial.estimatedDistanceToGoal();
    }

    /** Returns a sequence of WorldStates FROM the initial WorldState to the solution */
    public Iterable<WorldState> solution() {
        return _solution;
    }

    public static void main(String[] args) {

    }
}
