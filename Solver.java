/******************************************************************************
 *  Compilation:  javac Board.java
 *  Execution:    java Solver
 *  Dependencies: java.util.Arrays, edu.princeton.cs.algs4.*
 *
 *  8 Pazzle Solver
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private final Node mainNode;
    private final Node twinNode;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException();

        int moves = 0;

        twinNode = new Node(initial.twin(), moves, null);
        Node min = new Node(initial, moves, null);

        MinPQ<Node> queue = new MinPQ<Node>();
        queue.insert(min);
        queue.insert(twinNode);

        min = queue.delMin();

        while (!min.isGoal()) {
            moves = min.moves + 1;

            for (Board b : min.neighbors()) {
                Board previous = null;

                if (min.previous != null) {
                    previous = min.previous.getjBoard();
                }

                if (!b.equals(previous)) {
                    Node node = new Node(b, moves, min);
                    queue.insert(node);
                }
            }

            min = queue.delMin();
        }

        mainNode = min;
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        Node firstNode = mainNode;

        while (firstNode.previous != null) {
            firstNode = firstNode.previous;
        }

        return !firstNode.getjBoard().equals(twinNode.getjBoard());
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return isSolvable() ? mainNode.moves : -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        Node sequenceNode = mainNode;

        if (isSolvable()) {
            Stack<Board> solutionQueue = new Stack<Board>();

            solutionQueue.push(sequenceNode.getjBoard());
            while (sequenceNode.previous != null) {
                sequenceNode = sequenceNode.previous;
                solutionQueue.push(sequenceNode.getjBoard());
            }
            return solutionQueue;
        }
        else
            return null;
    }

    private class Node implements Comparable<Node> {
        private final Node previous;
        private final Board board;
        private final int moves;

        public Node(Board board, int moves, Node previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
        }

        public Board getjBoard() {
            return board;
        }

        public boolean isGoal() {
            return board.isGoal();
        }

        public Iterable<Board> neighbors() {
            return board.neighbors();
        }

        // add moves to simple manhattan
        public int heuristic() {
            return board.manhattan() + moves;
        }

        @Override
        public int compareTo(Node that) {
            return this.heuristic() - that.heuristic();
        }

        @Override
        public String toString() {
            return board.toString();
        }
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
