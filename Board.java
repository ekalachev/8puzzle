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
    private final int[] pazzle;
    private int dimensionLength;
    private final int zero = 0;

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

    private Board(int[] pazzle) {
        this.pazzle = new int[pazzle.length];
        for (int i = 0; i < pazzle.length; i++) {
            this.pazzle[i] = pazzle[i];
        }
    }

    // board dimension n
    public int dimension() {
        return dimensionLength;
    }

    // number of blocks out of place
    public int hamming() {
        int hammingCount = 0;

        for (int i = 0; i < pazzle.length; i++) {
            if (pazzle[i] != i + 1 && pazzle[i] != zero) {
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

            if (block == zero)
                continue;

            int[] currentPosotion = position(i);
            int[] neededPosotion = position(block);

            int x = neededPosotion[0] - currentPosotion[0];
            int y = neededPosotion[1] - currentPosotion[1];

            manhattanCount += Math.abs(x) + Math.abs(y);
        }

        return manhattanCount;
    }

    // returns XY position
    private int[] position(int num) {
        int xPosition = (num - 1) / dimensionLength;
        int yPosition = (num - 1) % dimensionLength;

        return new int[] { xPosition, yPosition };
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        Board twin = new Board(this.pazzle);

        for (int i = 0; i < this.pazzle.length; i++) {
            int nextIndex = i + 1;
            if (this.pazzle[i] != zero
                    && nextIndex < this.pazzle.length
                    && this.pazzle[nextIndex] != zero) {

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
            if (pazzle[i] == zero) {
                zeroIndex = i;
                zeroIndexFound = true;
                break;
            }
        }

        if (!zeroIndexFound) {
            return null;
        }

        Queue<Board> neighbors = new Queue<Board>();

        // not upper row
        if (zeroIndex / dimensionLength > 0)
            addNeighbor(neighbors, zeroIndex, zeroIndex - dimensionLength);

        // not bottom row
        if (zeroIndex / dimensionLength < dimensionLength - 1)
            addNeighbor(neighbors, zeroIndex, zeroIndex + dimensionLength);

        // not leftmost side
        if (zeroIndex != 0 && zeroIndex % dimensionLength == 0)
            addNeighbor(neighbors, zeroIndex, zeroIndex - 1);

        // not rightmost side
        if (zeroIndex + 1 % dimensionLength == 0)
            addNeighbor(neighbors, zeroIndex, zeroIndex + 1);

        return neighbors;
    }

    private void addNeighbor(Queue<Board> neighbors, int zeroIndex, int zeroNewPosition) {
        Board neighbor = new Board(this.pazzle);
        exch(neighbor, zeroIndex, zeroNewPosition);
        neighbors.enqueue(neighbor);
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        int index = 0;
        String result = "";

        for (int i = 0; i < this.dimension(); i++) {
            for (int j = 0; j < this.dimension(); j++) {
                result += pazzle[index++];
            }
            result += "\r\n";
        }

        return result;
    }

    // unit tests (not graded)
    public static void main(String[] args) {

    }
}
