package hw2;

import edu.princeton.cs.introcs.StdRandom;

public class PercolationStats {
    private Percolation tes;
    private int T;
    private  int N;
    private PercolationFactory pf;
    private double u;
    private double standardDeviation;
    private double[] x;
    /** perform T independent experiments on an N-by-N grid
     *
     */
    private void startTest() {
        double sum = 0;
        for (int i = 0; i < T; ++i) {
            int times = 0;
            Percolation p = pf.make(N);
            while (!p.percolates()) {
                int ax = StdRandom.uniform(N);
                int ay = StdRandom.uniform(N);
                if (!p.isOpen(ax, ay)) {
                    ++times;
                    p.open(ax, ay);
                }
            }
            double xi = (double) times / (N * N);
            sum += xi;
            x[i] = xi;
        }
        u = sum / T;
    }
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }

        this.N = N;
        this.T = T;
        this.pf = pf;
        x = new double[T];
        startTest();
        double s = 0;
        for (int i = 0; i < T; ++i) {
            s += (x[i] - u) * (x[i] - u);
        }
        standardDeviation = Math.sqrt(s / (T - 1));
    }

    /** sample mean of percolation threshold
     *
     */
    public double mean() {
        return u;
    }

    /** sample standard deviation of percolation threshold
     *
     */
    public double stddev() {
        if (T == 1) {
            return Double.NaN;
        }
        return standardDeviation;
    }

    /** low endpoint of 95% confidence interval
     *
     */
    public double confidenceLow()  {
        return (u - 1.96 * standardDeviation / Math.sqrt(T));
    }

    public double confidenceHigh() {
        return (u + 1.96 * standardDeviation / Math.sqrt(T));
    }
}
