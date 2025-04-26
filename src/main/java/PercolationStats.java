import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double mean;
    private final double stddev;
    private final double confidenceLo;
    private final double confidenceHi;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0) {
            throw new IllegalArgumentException("n <= 0");
        }
        if (trials <= 0) {
            throw new IllegalArgumentException("trials <= 0");
        }
        var results = new double[trials];
        for (int i = 0; i < trials; i++) {
            var percolation = new Percolation(n);
            while (!percolation.percolates()) {
                var row = StdRandom.uniformInt(1, n + 1);
                var col = StdRandom.uniformInt(1, n + 1);
                percolation.open(row, col);
            }
            results[i] = percolation.numberOfOpenSites() / ((double) n * n);
        }
        this.mean = StdStats.mean(results);
        this.stddev = StdStats.stddev(results);
        var interval = 1.96 * this.stddev / Math.sqrt(trials);
        this.confidenceLo = this.mean - interval;
        this.confidenceHi = this.mean + interval;
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return confidenceHi;
    }

    // test client (see below)
    public static void main(String[] args) {
        var n = Integer.parseInt(args[0]);
        var trials = Integer.parseInt(args[1]);
        var stats = new PercolationStats(n, trials);
        StdOut.printf("mean                    = %f%n", stats.mean);
        StdOut.printf("stddev                  = %f%n", stats.stddev);
        StdOut.printf("95%% confidence interval = [%f, %f]%n", stats.confidenceLo, stats.confidenceHi);
    }
}
