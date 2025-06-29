import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private static class SearchNode implements Comparable<SearchNode> {

        final int moves;
        final int priority;
        final Board board;
        final SearchNode previousNode;

        SearchNode(int moves, Board board, SearchNode previousNode) {
            this.moves = moves;
            this.priority = moves + board.manhattan();
            this.board = board;
            this.previousNode = previousNode;
        }

        @Override
        public int compareTo(SearchNode other) {
            return priority - other.priority;
        }
    }

    private final Stack<Board> solution;
    private final int moves;

    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        var primaryPQ = new MinPQ<SearchNode>();
        var twinPQ = new MinPQ<SearchNode>();
        primaryPQ.insert(new SearchNode(0, initial, null));
        twinPQ.insert(new SearchNode(0, initial.twin(), null));
        var primaryNode = primaryPQ.delMin();
        var twinNode = twinPQ.delMin();
        while (!primaryNode.board.isGoal() && !twinNode.board.isGoal()) {
            primaryNode = populateAndDelMin(primaryPQ, primaryNode);
            twinNode = populateAndDelMin(twinPQ, twinNode);
        }
        if (twinNode.board.isGoal()) {
            moves = -1;
            solution = null;
        } else {
            moves = primaryNode.moves;
            solution = new Stack<>();
            while (primaryNode != null) {
                solution.push(primaryNode.board);
                primaryNode = primaryNode.previousNode;
            }
        }
    }

    public boolean isSolvable() {
        return moves != -1;
    }

    public int moves() {
        return moves;
    }

    public Iterable<Board> solution() {
        return moves == -1 ? null : solution;
    }

    private SearchNode populateAndDelMin(MinPQ<SearchNode> pq, SearchNode node) {
        for (var neighbor : node.board.neighbors()) {
            var previousNode = node.previousNode;
            if (previousNode != null && neighbor.equals(previousNode.board)) continue;
            pq.insert(new SearchNode(node.moves + 1, neighbor, node));
        }
        return pq.delMin();
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

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
