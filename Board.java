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
        int hammingCount = 0;

        for (int i = 0; i < pazzle.length; i++) {
            if (pazzle[i] != i + 1 && pazzle[i] != 0) {
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

            manhattanCount += (x < 0 ? x * -1 : x)
                    + (y < 0 ? y * -1 : y);
        }

        return manhattanCount;
    }

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
        int[][] blocks = new int[dimensionLength][dimensionLength];
        int pazzleIndex = 0;
        boolean twined = false;

        for (int y = 0; y < dimensionLength; y++) {
            for (int x = 0; x < dimensionLength; x++) {
                int block = pazzle[pazzleIndex++];
                blocks[y][x] = block;

                if (!twined && block != zero &&) {
                    twined = true;
                }
            }
        }

        return new Board(blocks);
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
