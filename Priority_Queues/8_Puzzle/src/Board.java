public class Board {
    private final int[][] board;
    private final int dim;

    public Board(int[][] blocks) {
        dim = blocks.length;
        board = new int[dim][dim];

        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++)
                board[i][j] = blocks[i][j];
    }

    public int dimension() {
        return dim;
    }

    public int hamming() {
        int hamming = 0;
        int expectedValue = 0;

        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++) {

                expectedValue = i * dim + j + 1;

                if (board[i][j] > 0 && board[i][j] != expectedValue)
                    hamming++;
            }

        return hamming;
    }

    public int manhattan() {
        int manhattan = 0;
        int expectedJ = 0;
        int expectedI = 0;

        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++) {
                if (board[i][j] == 0)
                    continue;

                expectedJ = (board[i][j] - 1) % dim;
                expectedI = (board[i][j] - expectedJ) / dim;
                manhattan += Math.abs(i - expectedI) + Math.abs(j - expectedJ);
            }

        return manhattan;
    }

    public boolean isGoal() {
        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++)
                if (board[i][j] > 0 && board[i][j] != i * dim + j + 1)
                    return false;

        return true;
    }
    /*
    public Board twin() {
        // a board that is obtained by exchanging any pair of blocks
    }
     */
    @Override
    public boolean equals(Object y) {
        if (y == this)
            return true;

        if (y == null)
            return false;

        if (getClass() != y.getClass())
            return false;

        Board anotherBoard = (Board) y;

        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++)
                if (board[i][j] != anotherBoard.board[i][j])
                    return false;

        return true;
    }
    /*
    public Iterable<Board> neighbors() {
        // all neighboring boards
    }
     */

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder(dim + "\n");

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++)
                out.append(" " + board[i][j] + " ");
            out.append("\n");
        }

        return out.toString();
    }

    public static void main(String[] args) {
        Board b1 = new Board(new int[][] {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}});

        Board b2 = new Board(new int[][] {
            {8, 7, 6},
            {5, 4, 3},
            {2, 1, 0}});

        Board b3 = new Board(new int[][] {
            {1, 2, 3},
            {6, 5, 4},
            {7, 8, 0}});

        System.out.println("b1.hamming() == 0: " + (b1.hamming() == 0));
        System.out.println("b2.hamming() == 8: " + (b2.hamming() == 8));
        System.out.println("b3.hamming() == 2: " + (b3.hamming() == 2));

        System.out.println("b1.manhattan() == 0:  " + (b1.manhattan() == 0));
        System.out.println("b2.manhattan() == 16: " + (b2.manhattan() == 16));
        System.out.println("b3.manhattan() == 4:  " + (b3.manhattan() == 4));

        System.out.println("b1.isGoal() == true:  " + (b1.isGoal() == true));
        System.out.println("b2.isGoal() == false: " + (b2.isGoal() == false));
        System.out.println("b2.isGoal() == false: " + (b2.isGoal() == false));

        System.out.println("b1.equals(b1) == true: " + (b1.equals(b1) == true));
        System.out.println("b1.equals(b2) == false: " + (b1.equals(b2) == false));
        System.out.println("b1.equals(new Board(new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}})) == true: " + (b1.equals(new Board(new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}})) == true));

        System.out.println(b1);
        System.out.println(b2);
        System.out.println(b3);
    }
}
