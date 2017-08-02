package hw3.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.*;

public class Solver {
    SearchNode _initial;
    SearchNode _goal;

    /** search node to support A* algorithm */
    private class SearchNode {
        private WorldState state; /* a WorldState */
        private int moves; /* the number of moves made to reach this world state from the initial state */
        private SearchNode prev; /* a reference to the previous search node */

        /** Construct SearchNode. prev is null */
        public SearchNode(WorldState state, int moves, SearchNode prevNode ) {
            this.state = state;
            this.moves = moves;
            this.prev = prevNode;
        }
    }

    /** Method to compare SearchNodes */
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
        /* initialize the problem */
        _initial = new SearchNode(initial, 0, null);
        /* _initial.prev is set to be _initial itself because it helps avoid nested
         * if-statement and repeated codes within search() method during optimization
         * process.
         */
        _initial.prev = _initial;
        MinPQ<SearchNode> queue = new MinPQ<>(new SearchNodeComparator());
        queue.insert(_initial);

        /* recursive call */
        search(queue);
    }

    /** Search the path with A* algorithm
     *  Optimization:  To reduce unnecessary exploration of useless search nodes,
     *                 when considering the neighbors of a search node,
     *                 don't enqueue a neighbor if its world state is the same as
     *                 the world state of the previous search node.
     */
    private void search(MinPQ<SearchNode> queue) {
        SearchNode minNode = queue.min();
        queue.delMin();
        if (minNode.state.isGoal()) {
            _goal = minNode;
            return;
        }
        for (WorldState neighbor : minNode.state.neighbors()) {
            if (!neighbor.equals(minNode.prev.state)) {
                int moves = minNode.moves + 1;
                queue.insert(new SearchNode(neighbor, moves, minNode));
            }
        }
        search(queue);
    }



    /** Returns the minimum number of moves to solve the puzzle starting at the
     *  initial WorldState
     *
     */
    public int moves() {
        return _initial.state.estimatedDistanceToGoal();
    }

    /** Returns a sequence of WorldStates FROM the initial WorldState to the solution */
    public Iterable<WorldState> solution() {
        List<WorldState> path = new LinkedList<>();
        SearchNode p = _goal;
        while (p != p.prev) {
            path.add(0, p.state);
            p = p.prev;
        }
        path.add(0, p.state);
        return path;
    }

    public static void main(String[] args) {

    }
}
