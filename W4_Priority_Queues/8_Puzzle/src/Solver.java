import java.util.LinkedList;

import edu.princeton.cs.algs4.MinPQ;

public class Solver {
    private LinkedList<Board> solution = new LinkedList<Board>();
    private final boolean isSolvable;
    private int moves = 0;

    public Solver(Board initial) {
        if (initial == null)
            throw new NullPointerException("initial == null");

        MinPQ<Node> minPQ = new MinPQ<Node>();
        MinPQ<Node> twinMinPQ = new MinPQ<Node>();

        Node node = new Node(initial, null, 0);
        Node twinNode = new Node(initial.twin(), null, 0);

        minPQ.insert(node);
        twinMinPQ.insert(twinNode);

        while (!node.board.isGoal() && !twinNode.board.isGoal()) {
            node = minPQ.delMin();
            twinNode = twinMinPQ.delMin();

            for (Board neigh : node.board.neighbors()) {
                if (node.prev == null || !node.prev.board.equals(neigh)) {
                    Node n = new Node(neigh, node, node.numOfMoves + 1);
                    minPQ.insert(n);
                }
            }

            for (Board neigh : twinNode.board.neighbors()) {
                if (twinNode.prev == null || !twinNode.prev.board.equals(neigh)) {
                    Node n = new Node(neigh, twinNode, twinNode.numOfMoves + 1);
                    twinMinPQ.insert(n);
                }
            }
        }

        isSolvable = !twinNode.board.isGoal();

        if (!isSolvable) {
            moves = -1;
            solution = null;
        }
        else {
            while (node.prev != null) {
                moves++;
                solution.push(node.board);
                node = node.prev;
            }
            solution.push(node.board);
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

    private class Node implements Comparable<Node> {
        private final Board board;
        private final Node prev;
        private final int numOfMoves;

        public Node(Board board, Node prev, int numOfMoves) {
            this.board = board;
            this.prev = prev;
            this.numOfMoves = numOfMoves;
        }

        @Override
        public int compareTo(Node that) {
            return board.manhattan() + numOfMoves - that.board.manhattan() - that.numOfMoves;
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
