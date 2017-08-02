package hw3.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.*;

public class Solver {
    SearchNode _initial;

    /** search node to support A* algorithm */
    private class SearchNode {
        private WorldState state; /* a WorldState */
        private int moves; /* the number of moves made to reach this world state from the initial state */
        private SearchNode prev; /* a reference to the previous search node */

        /** Constuct SearchNode. prev is null */
        public SearchNode(WorldState state, int moves ) {
            this.state = state;
            this.moves = moves;
            this.prev = null;
        }

        /** Set the prev node of the current node */
        public void setPrev(SearchNode prevNode) {
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
        _initial = new SearchNode(initial, 0);
        MinPQ<SearchNode> queue = new MinPQ<>(new SearchNodeComparator());
        queue.insert(_initial);

        /* recursive call */
        search(queue);
    }

    /** Search the path with A* algorithm
     *  @param optPrev provides the parent SearchNode prior to the current search
     *                 attempt for the purpose of optimization.
     *  Optimization:  To reduce unnecessary exploration of useless search nodes,
     *                 when considering the neighbors of a search node,
     *                 don't enqueue a neighbor if its world state is the same as
     *                 the world state of the previous search node.
     */
    private void search(MinPQ<SearchNode> queue, SearchNode optPrev) {
        SearchNode minNode = queue.min();
        queue.delMin();
        if (minNode.state.isGoal())
            return;
        for (WorldState state : minNode.state.neighbors()) {
            if (!state.equals(optPrev.state)) {
                int moves = minNode.moves + 1;
                queue.insert(new SearchNode(state, moves));
            } else {
                System.out.println("Optimization works");
            }
        }
        minNode.prev = queue.min();
        search(queue, minNode);
    }

    private void search(MinPQ<SearchNode> queue) {
        /* optPrev should be initialized with null intuitively, but I chose to use
         * queue.min() instead because it helps avoid repeated codes and nested
         * if-statements caused by null.
         */
        search(queue, queue.min());
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
        List<WorldState> path = new ArrayList<>();
        SearchNode p = _initial;
        while (p != null) {
            path.add(p.state);
            p = p.prev;
        }
        return path;
    }

    public static void main(String[] args) {

    }
}
