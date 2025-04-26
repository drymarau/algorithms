import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private static final byte FLAG_OPEN = 1;
    private static final byte FLAG_TOP = 1 << 1;
    private static final byte FLAG_BOTTOM = 1 << 2;
    private static final int[][] DIRECTIONS = new int[][]{
            new int[]{0, 1},
            new int[]{1, 0},
            new int[]{0, -1},
            new int[]{-1, 0}
    };

    private final int n;
    private final byte[] status;
    private final WeightedQuickUnionUF uf;

    private boolean percolates;
    private int numberOfOpenSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("n <= 0");
        this.n = n;
        this.numberOfOpenSites = 0;
        this.status = new byte[n * n];
        this.uf = new WeightedQuickUnionUF(n * n);
        for (int i = 0; i < n; i++) {
            this.status[index(1, i + 1)] |= FLAG_TOP;
            this.status[index(n, i + 1)] |= FLAG_BOTTOM;
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isOpen(row, col)) return;
        var index = index(row, col);
        status[index] |= FLAG_OPEN;
        numberOfOpenSites++;
        for (int[] direction : DIRECTIONS) {
            var r = row + direction[0];
            var c = col + direction[1];
            if (!inRange(r)) continue;
            if (!inRange(c)) continue;
            var i = index(r, c);
            if (!isOpen(i)) continue;
            var p = uf.find(i);
            var s = status[p];
            uf.union(index, i);
            var newP = uf.find(index);
            status[newP] |= s;
            status[index] |= s;
        }
        var flag = FLAG_OPEN | FLAG_TOP | FLAG_BOTTOM;
        if ((status[index] & flag) == flag) {
            percolates = true;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        require(row, col);
        return isOpen(index(row, col));
    }

    private boolean isOpen(int index) {
        return (status[index] & FLAG_OPEN) == FLAG_OPEN;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        require(row, col);
        var p = uf.find(index(row, col));
        var flag = FLAG_OPEN | FLAG_TOP;
        return (status[p] & flag) == flag;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return percolates;
    }

    // test client (optional)
    public static void main(String[] args) {
        var n = 20;
        var percolation = new Percolation(n);
        while (!percolation.percolates()) {
            var row = StdRandom.uniformInt(1, n + 1);
            var col = StdRandom.uniformInt(1, n + 1);
            percolation.open(row, col);
        }
    }

    private void require(int row, int col) {
        if (!inRange(row)) throw new IllegalArgumentException("row is out of range.");
        if (!inRange(col)) throw new IllegalArgumentException("col is out of range.");
    }

    private boolean inRange(int i) {
        return i >= 1 && i <= n;
    }

    private int index(int row, int col) {
        return (row - 1) * n + col - 1;
    }
}
