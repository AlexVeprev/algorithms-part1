import java.util.ArrayList;
import java.util.List;

public class Board {
    private final int[][] board;
    private final int dim;
    private int emptyI;
    private int emptyJ;

    public Board(int[][] blocks) {
        dim = blocks.length;
        board = new int[dim][dim];

        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++) {
                board[i][j] = blocks[i][j];
                if (board[i][j] == 0) {
                    emptyI = i;
                    emptyJ = j;
                }
            }
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

    public Board twin() {
        Board twin = new Board(board);
        boolean swapped = false;

        int i = 0;
        while (!swapped && i < dim) {
            for (int j = 0; j < dim - 1; j++) {
                if (board[i][j] != 0 && board[i][j + 1] != 0) {
                    twin.board[i][j] = board[i][j + 1];
                    twin.board[i][j + 1] = board[i][j];
                    swapped = true;
                    break;
                }
            }
            i++;
        }

        return twin;
    }

    @Override
    public boolean equals(Object y) {
        if (y == this)
            return true;

        if (y == null)
            return false;

        if (getClass() != y.getClass())
            return false;


        Board anotherBoard = (Board) y;

        if (dim != anotherBoard.dim)
            return false;

        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++)
                if (board[i][j] != anotherBoard.board[i][j])
                    return false;

        return true;
    }

    public Iterable<Board> neighbors() {
        List<Board> neighbors = new ArrayList<Board>();
        Board neighbor;

        if (emptyI > 0) {
            neighbor = new Board(board);
            neighbor.board[emptyI][emptyJ] = board[emptyI - 1][emptyJ];
            neighbor.board[emptyI - 1][emptyJ] = board[emptyI][emptyJ];
            neighbor.emptyI = emptyI - 1;
            neighbors.add(neighbor);
        }

        if (emptyI < dim - 1) {
            neighbor = new Board(board);
            neighbor.board[emptyI][emptyJ] = board[emptyI + 1][emptyJ];
            neighbor.board[emptyI + 1][emptyJ] = board[emptyI][emptyJ];
            neighbor.emptyI = emptyI + 1;
            neighbors.add(neighbor);
        }

        if (emptyJ > 0) {
            neighbor = new Board(board);
            neighbor.board[emptyI][emptyJ] = board[emptyI][emptyJ - 1];
            neighbor.board[emptyI][emptyJ - 1] = board[emptyI][emptyJ];
            neighbor.emptyJ = emptyJ - 1;
            neighbors.add(neighbor);
        }

        if (emptyJ < dim - 1) {
            neighbor = new Board(board);
            neighbor.board[emptyI][emptyJ] = board[emptyI][emptyJ + 1];
            neighbor.board[emptyI][emptyJ + 1] = board[emptyI][emptyJ];
            neighbor.emptyJ = emptyJ + 1;
            neighbors.add(neighbor);
        }

        return neighbors;
    }

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
            {6, 0, 4},
            {7, 8, 5}});

        Board b4 = new Board(new int[][] {
            {1, 0, 3},
            {6, 2, 4},
            {7, 8, 5}});

        System.out.println("\nNeighbors of b4.neighbors():");
        for (Board n : b4.neighbors()) {
            System.out.println("==========\nNeighbors of\n" + n + "--------");
            for (Board nn : n.neighbors())
                System.out.println(nn);
        }

        System.out.println("b1.hamming() == 0: " + (b1.hamming() == 0));
        System.out.println("b2.hamming() == 8: " + (b2.hamming() == 8));
        System.out.println("b3.hamming() == 3: " + (b3.hamming() == 3));

        System.out.println("b1.manhattan() == 0:  " + (b1.manhattan() == 0));
        System.out.println("b2.manhattan() == 16: " + (b2.manhattan() == 16));
        System.out.println("b3.manhattan() == 6:  " + (b3.manhattan() == 6));

        System.out.println("b1.isGoal() == true:  " + (b1.isGoal() == true));
        System.out.println("b2.isGoal() == false: " + (b2.isGoal() == false));
        System.out.println("b2.isGoal() == false: " + (b2.isGoal() == false));

        System.out.println("b1.equals(b1) == true: " + (b1.equals(b1) == true));
        System.out.println("b1.equals(b2) == false: " + (b1.equals(b2) == false));
        System.out.println("b1.equals(new Board(new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}})) == true: " + (b1.equals(new Board(new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}})) == true));

        System.out.println(b1);
        System.out.println(b2);
        System.out.println(b3);

        System.out.println("\nTwins:");
        System.out.println("b1.twin()\n" + b1.twin());
        System.out.println("b1.twin()\n" + b1.twin());
        System.out.println("b1.twin()\n" + b1.twin());
        System.out.println("b2.twin()\n" + b2.twin());
        System.out.println("b3.twin()\n" + b3.twin());

        System.out.println("\nb1.neighbors():");
        for (Board n : b1.neighbors()) {
            System.out.println(n);
        }

        System.out.println("\nb3.neighbors():");
        for (Board n : b3.neighbors()) {
            System.out.println(n);
        }

    }
}
