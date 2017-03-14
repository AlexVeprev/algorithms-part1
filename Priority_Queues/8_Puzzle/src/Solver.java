
public class Solver {
    public Solver(Board initial) {
        // find a solution to the initial board (using the A* algorithm)
    }

    public boolean isSolvable() {
        // is the initial board solvable?
    }

    public int moves() {
        // min number of moves to solve initial board; -1 if unsolvable
    }

    public Iterable<Board> solution() {
        // sequence of boards in a shortest solution; null if unsolvable
    }

    public static void main(String[] args) {

    }

    private class Node {
        private Board currentBoard;
        private Board previousBoard;
        private int numOfMoves;

        public Node(Board currentBoard, Board previousBoard, int numOfMoves) {
            this.currentBoard = currentBoard;
            this.previousBoard = previousBoard;
            this.numOfMoves = numOfMoves;
        }
    }
}
