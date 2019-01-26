/******************************************************************************
 *  Compilation:  javac Board.java
 *  Execution:    java Board
 *  Dependencies: java.util.Arrays, edu.princeton.cs.algs4.Queue
 *
 *  8 Pazzle Board
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.Queue;

import java.util.Arrays;

public class Board {
    private static final int ZERO = 0;
    private final int[] pazzle;
    private final int dimensionLength;

    // construct a board from an n-by-n array of blocks
    public Board(int[][] blocks) {
        if (blocks == null)
            throw new IllegalArgumentException("blocks object is NULL");

        pazzle = new int[blocks.length * blocks[0].length];
        dimensionLength = blocks.length;

        int pazzleIndex = 0;
        for (int[] row : blocks) {
            for (int col : row) {
                pazzle[pazzleIndex++] = col;
            }
        }
    }

    private Board(int[] pazzle, int dimensionLength) {
        this.pazzle = new int[pazzle.length];
        for (int i = 0; i < pazzle.length; i++) {
            this.pazzle[i] = pazzle[i];
        }

        this.dimensionLength = dimensionLength;
    }

    // board dimension n
    public int dimension() {
        return dimensionLength;
    }

    // number of blocks out of place
    public int hamming() {
        int hammingCount = 0;

        for (int i = 0; i < pazzle.length; i++) {
            if (pazzle[i] != i + 1 && pazzle[i] != ZERO) {
                hammingCount++;
            }
        }

        return hammingCount;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int manhattanCount = 0;

        for (int i = 0; i < pazzle.length; i++) {
            int block = pazzle[i];

            if (block == ZERO)
                continue;

            // i + 1 needed because puzzle array count starts from 1
            int[] currentPosotion = position(i + 1);
            int[] neededPosotion = position(block);

            int x = currentPosotion[0] - neededPosotion[0];
            int y = currentPosotion[1] - neededPosotion[1];

            manhattanCount += Math.abs(x) + Math.abs(y);
        }

        return manhattanCount;
    }

    // returns XY position
    private int[] position(int num) {
        int xPosition = (num - 1) % dimensionLength;
        int yPosition = (num - 1) / dimensionLength;

        return new int[] { xPosition, yPosition };
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        Board twin = new Board(this.pazzle, this.dimensionLength);

        for (int i = 0; i < this.pazzle.length; i++) {
            int nextIndex = i + 1;
            if (this.pazzle[i] != ZERO
                    && nextIndex < this.pazzle.length
                    && this.pazzle[nextIndex] != ZERO) {

                exch(twin, i, i + 1);
                break;
            }
        }

        return twin;
    }

    // exchanges two points of Board array
    private void exch(Board board, int i, int j) {
        int tmp = board.pazzle[i];
        board.pazzle[i] = board.pazzle[j];
        board.pazzle[j] = tmp;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null || this.getClass() != y.getClass())
            return false;

        Board that = (Board) y;

        if (this.dimension() != that.dimension())
            return false;

        return Arrays.equals(this.pazzle, that.pazzle);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int zeroIndex = 0;
        boolean zeroIndexFound = false;

        for (int i = 0; i < pazzle.length; i++) {
            if (pazzle[i] == ZERO) {
                zeroIndex = i;
                zeroIndexFound = true;
                break;
            }
        }

        if (!zeroIndexFound) {
            return null;
        }

        Queue<Board> neighbors = new Queue<Board>();

        // has upper row
        if (zeroIndex / dimensionLength > 0)
            addNeighbor(neighbors, zeroIndex, zeroIndex - dimensionLength);

        // has bottom row
        if (zeroIndex / dimensionLength < dimensionLength - 1)
            addNeighbor(neighbors, zeroIndex, zeroIndex + dimensionLength);

        // has leftmost side
        if (zeroIndex != 0 && zeroIndex % dimensionLength != 0)
            addNeighbor(neighbors, zeroIndex, zeroIndex - 1);

        // has rightmost side
        if ((zeroIndex + 1) % dimensionLength != 0)
            addNeighbor(neighbors, zeroIndex, zeroIndex + 1);

        return neighbors;
    }

    private void addNeighbor(Queue<Board> neighbors, int zeroIndex, int zeroNewPosition) {
        Board neighbor = new Board(this.pazzle, this.dimensionLength);
        exch(neighbor, zeroIndex, zeroNewPosition);
        neighbors.enqueue(neighbor);
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder result = new StringBuilder();
        result
                .append(this.dimensionLength)
                .append("\n");

        for (int i = 0; i < this.pazzle.length; i++) {
            result.append(String.format("%2d ", this.pazzle[i]));

            if ((i + 1) % this.dimensionLength == 0)
                result.append("\n");
        }

        return result.toString();
    }
}
