import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double mean;
    private double stddev;
    private double confidenceLo;
    private double confidenceHi;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Expected: n > 0; trials > 0. Got: n = " + n + "; trials = " + trials);
        }

        double[] results = new double[trials];

        for (int i = 0; i < trials; i++) {
            results[i] = (double) this.doPercolationExperiment(n) / (double) (n * n);
        }

        this.mean = StdStats.mean(results);
        this.stddev = StdStats.stddev(results);

        this.confidenceLo = this.mean - this.stddev * 1.96 / Math.sqrt(trials);
        this.confidenceHi = this.mean + this.stddev * 1.96 / Math.sqrt(trials);
    }

    public double mean() {
        return this.mean;
    }

    public double stddev() {
        return this.stddev;
    }

    public double confidenceLo() {
        return this.confidenceLo;
    }

    public double confidenceHi() {
        return this.confidenceHi;
    }

    private int doPercolationExperiment(int n) {
        Percolation p = new Percolation(n);
        int numOfBlockedSites = n * n;
        int[] blockedSites = new int[numOfBlockedSites];
        int ixOfSiteToOpen;
        int siteToOpen;
        int swap;

        for (int i = 0; i < numOfBlockedSites; i++) {
            blockedSites[i] = i;
        }

        while (!p.percolates()) {
            ixOfSiteToOpen = StdRandom.uniform(numOfBlockedSites);
            siteToOpen = blockedSites[ixOfSiteToOpen];

            p.open(siteToOpen / n + 1, siteToOpen % n + 1);

            numOfBlockedSites--;
            swap = blockedSites[numOfBlockedSites];
            blockedSites[numOfBlockedSites] = siteToOpen;
            blockedSites[ixOfSiteToOpen] = swap;
        }

        return p.numberOfOpenSites();
    }

    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean                    = "  + ps.mean());
        System.out.println("stddev                  = "  + ps.stddev());
        System.out.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }

}
