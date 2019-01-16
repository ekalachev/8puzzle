/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

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

    // board dimension n
    public int dimension() {
        return dimensionLength;
    }

    // number of blocks out of place
    public int hamming() {
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null || this.getClass() != y.getClass())
            return false;

        Board that = (Board) y;

        if (this.dimension() != that.dimension())
            return false;

        boolean areEquals = true;

        for (int i = 0; i < this.pazzle.length; i++) {
            if (this.pazzle[i] != that.pazzle[i]) {
                areEquals = false;
                break;
            }
        }

        return areEquals;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
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
