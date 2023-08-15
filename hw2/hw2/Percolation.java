package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF PERCOLATION_UNION;
    private final int VIRTUAL_TOP;
    private boolean isPercolates = false;
    private final int[][] NEAR = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    private int numOfOpenSites = 0;
    private boolean[][] g;
    private final int N;
    /** create N-by-N grid, with all sites initially blocked */
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }

        PERCOLATION_UNION = new WeightedQuickUnionUF(N * N + 1);
        VIRTUAL_TOP = N * N;
        g = new boolean[N][N];
        this.N = N;
        for (int i = 0; i < N; ++i) {
            PERCOLATION_UNION.union(VIRTUAL_TOP, i);
        }
    }
    private boolean validPos(int row, int col) {
        return (row >= 0 && col >= 0 && row < N && col < N);
    }

    /** open the site (row, col) if it is not open already */
    public void open(int row, int col) {
        if (!validPos(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        if (g[row][col]) {
            return;
        }

        g[row][col] = true;
        ++numOfOpenSites;
        for (int i = 0; i < 4; ++i) {
            int nx = row + NEAR[i][0];
            int ny = col + NEAR[i][1];
            if (validPos(nx, ny) && g[nx][ny]) {
                PERCOLATION_UNION.union(nx * N + ny, row * N + col);
            }
        }
        if (row == N - 1 && validPos(row - 1, col) && isFull(row - 1, col)) {
            isPercolates = true;
        }
    }
    public boolean isOpen(int row, int col) {
        if (!validPos(row, col)) {
            throw new IndexOutOfBoundsException();
        }

        return g[row][col];
    }
    public boolean isFull(int row, int col) {
        if (!validPos(row, col)) {
            throw new IndexOutOfBoundsException();
        }

        return g[row][col] && PERCOLATION_UNION.connected(VIRTUAL_TOP, row * N + col);
    }

    /** number of open sites
     */
    public int numberOfOpenSites() {
        return numOfOpenSites;
    }

    /** does the system percolate?
     */
    public boolean percolates() {
        if (isPercolates) {
            return isPercolates;
        }
        for (int i = 0; i < N; ++i) {
            if (isFull(N - 1, i)) {
                isPercolates = true;
                return true;
            }
        }

        return false;
    }
    public static void main(String[] args) {
    }
}
