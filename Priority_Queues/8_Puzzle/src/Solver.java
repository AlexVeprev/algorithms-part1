import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import edu.princeton.cs.algs4.MinPQ;

public class Solver {
    private List<Board> solution = new ArrayList<Board>();
    private final boolean isSolvable;
    private final int moves;

    public Solver(Board initial) {
        if (initial == null)
            throw new NullPointerException("initial == null");

        if (initial.isGoal()) {
            solution.add(initial);
            isSolvable = true;
            moves = 0;
            return;
        }

        MinPQ<Node> minPQ = new MinPQ<Node>(manhattanOrder());
        MinPQ<Node> twinMinPQ = new MinPQ<Node>(manhattanOrder());

        Node node = new Node(initial, null, 0);
        Node twinNode = new Node(initial.twin(), null, 0);

        minPQ.insert(node);
        twinMinPQ.insert(twinNode);

        while (!node.currentBoard.isGoal() && !twinNode.currentBoard.isGoal()) {
            node = minPQ.delMin();
            twinNode = twinMinPQ.delMin();

            if (node.previousBoard == null || node.previousBoard == solution.get(solution.size() - 1))
                solution.add(node.currentBoard);

            for (Board neigh : node.currentBoard.neighbors()) {
                if (node.previousBoard == null || !node.previousBoard.equals(neigh)) {
                    Node n = new Node(neigh, node.currentBoard, node.numOfMoves + 1);
                    minPQ.insert(n);
                }
            }

            for (Board neigh : twinNode.currentBoard.neighbors()) {
                if (twinNode.previousBoard == null || !twinNode.previousBoard.equals(neigh)) {
                    Node n = new Node(neigh, twinNode.currentBoard, twinNode.numOfMoves + 1);
                    twinMinPQ.insert(n);
                }
            }
        }

        isSolvable = !twinNode.currentBoard.isGoal();

        if (!isSolvable) {
            moves = -1;
            solution = null;
        }
        else {
            moves = solution.size() - 1;
        }
    }

    public boolean isSolvable() {
        return isSolvable;
    }

    public int moves() {
        return moves;
    }

    public Iterable<Board> solution() {
        return solution;
    }

    private class Node {
        private final Board currentBoard;
        private final Board previousBoard;
        private final int numOfMoves;

        public Node(Board currentBoard, Board previousBoard, int numOfMoves) {
            this.currentBoard = currentBoard;
            this.previousBoard = previousBoard;
            this.numOfMoves = numOfMoves;
        }
    }

    private Comparator<Node> manhattanOrder() {
        return new ManhattanOrder();
    }

    private class ManhattanOrder implements Comparator<Node> {
        @Override
        public int compare(Node n1, Node n2) {
            int prio1 = n1.currentBoard.manhattan() + n1.numOfMoves;
            int prio2 = n2.currentBoard.manhattan() + n2.numOfMoves;

            if (prio1 < prio2) {
                return -1;
            }

            if (prio1 > prio2) {
                return 1;
            }

            return 0;
        }
    }

    public static void main(String[] args) {
        Board b1 = new Board(new int[][] {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}});
        Board b2 = new Board(new int[][] {
            {1, 2, 3},
            {4, 5, 0},
            {7, 8, 6}});

        System.out.println("Solve b1.");
        Solver s = new Solver(b1);
        for (Board b : s.solution())
            System.out.println(b);

        System.out.println("Solve b2.");
        s = new Solver(b2);
        for (Board b : s.solution())
            System.out.println(b);
    }
}
