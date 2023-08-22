package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState {

    private final int[][] grid;
    private final int N;
    public Board(int[][] tiles) {
        N = tiles.length;
        grid = new int[N][N];
        for (int i = 0; i < N; ++i) {
            System.arraycopy(tiles[i], 0, grid[i], 0, N);
        }
    }
    public int tileAt(int i, int j) {
        if (i < 0 || j < 0 || i > N - 1 || j > N - 1) {
            throw new IndexOutOfBoundsException();
        }
        return grid[i][j];
    }
    public int size() {
        return N;
    }
    @Override
    //source from http://joshh.ug/neighbors.html
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int n = size();
        int blankX = -1;
        int blankY = -1;
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (tileAt(r, c) == 0) {
                    blankX = r;
                    blankY = c;
                }
            }
        }
        int[][] newGrid = new int[n][n];
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                newGrid[r][c] = tileAt(r, c);
            }
        }
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (Math.abs(-blankX + r) + Math.abs(c - blankY) - 1 == 0) {
                    newGrid[blankX][blankY] = newGrid[r][c];
                    newGrid[r][c] = 0;
                    Board neighbor = new Board(newGrid);
                    neighbors.enqueue(neighbor);
                    newGrid[r][c] = newGrid[blankX][blankY];
                    newGrid[blankX][blankY] = 0;
                }
            }
        }
        return neighbors;
    }
    @Override
    public boolean isGoal() {
        for (int r = 0; r < N; ++r) {
            for (int c = 0; c < N; ++c) {
                int num = r * N + c + 1;
                if (r == N - 1 && c == N - 1) {
                    return true;
                } else if (grid[r][c] != num) {
                    return false;
                }
            }
        }
        return true;
    }
    public int hamming() {
        int cnt = 0;
        for (int r = 0; r < N; ++r) {
            for (int c = 0; c < N; ++c) {
                int num = r * N + c + 1;
                if (r == N - 1 && c == N - 1) {
                    return cnt;
                } else if (grid[r][c] != num) {
                    ++cnt;
                }
            }
        }
        return cnt;
    }
    private int calculateManhattanHelper(int num, int realX, int realY) {
        if (num == 0) {
            return 0;
        }
        --num;
        int expectedX = num / N;
        int expectedY = num % N;
        return Math.abs(realX - expectedX) + Math.abs(realY - expectedY);
    }
    public int manhattan() {
        int cnt = 0;
        for (int r = 0; r < N; ++r) {
            for (int c = 0; c < N; ++c) {
                cnt += calculateManhattanHelper(grid[r][c], r, c);
            }
        }
        return cnt;
    }
    @Override
    public int estimatedDistanceToGoal() {
        return manhattan();
    }
    public boolean equals(Object y) {
        if (y.getClass() != this.getClass()) {
            return false;
        }
        Board Y = (Board) y;
        if (N != Y.size()) {
            return false;
        }
        for (int r = 0; r < N; ++r) {
            for (int c = 0; c < N; ++c) {
                if (grid[r][c] != Y.tileAt(r, c)) {
                    return false;
                }
            }
        }
        return true;
    }
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
