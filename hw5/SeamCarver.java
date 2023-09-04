import edu.princeton.cs.algs4.Picture;
import java.util.Arrays;

public class SeamCarver {
    private final Picture picture;

    public SeamCarver(Picture pic) {
        picture = pic;
    }

    public Picture picture() {
        return picture;
    }

    public int width() {
        return picture.width();
    }

    public int height() {
        return picture.height();
    }

    public double energy(int x, int y) {
        if (x < 0 || y < 0 || x >= width() || y >= height()) {
            throw new IndexOutOfBoundsException();
        }

        int x1 = x - 1 < 0 ? width() - 1 : x - 1;
        int x2 = x + 1 >= width() ? 0 : x + 1;
        int y1 = y - 1 < 0 ? height() - 1 : y - 1;
        int y2 = y + 1 >= height() ? 0 : y + 1;
        return cal(x1, y, x2, y) + cal(x, y1, x, y2);
    }
    private int cal(int x1, int y1, int x2, int y2) {
        int rgb1 = picture().getRGB(x1, y1);
        int rgb2 = picture().getRGB(x2, y2);
        int rx2 = ((rgb1 >> 16) - (rgb2 >> 16));
        int gx2 = (((rgb1 >> 8) & 0xff) - ((rgb2 >> 8) & 0xff));
        int bx2 = ((rgb1 & 0xff) - (rgb2 & 0xff));
        return rx2 * rx2 + gx2 * gx2 + bx2 * bx2;
    }


    public int[] findHorizontalSeam() {
        return findSeamHelper(height(), width(), true);
    }            // sequence of indices for horizontal seam

    public int[] findVerticalSeam() {
        return findSeamHelper(width(), height(), false);
    }
    private int[] findSeamHelper(int w,int h, boolean mode) {
        int[] ans = new int[h];
        if (w == 1) {
            Arrays.fill(ans, 0);
            return ans;
        }

        double[][] M = new double[h][w];
        int[][] path = new int[h][w];
        for (int y = 0; y < h; ++y) {
            if (y == 0) {
                for (int x = 0; x < w; ++x) {
                    M[y][x] = (mode) ? energy(y, x) : energy(x, y);
                }
            } else {
                for (int x = 0; x < w; ++x) {
                    double e = (mode) ? energy(y, x) : energy(x, y);
                    if (x == 0) {
                        if (M[y - 1][x] < M[y - 1][x + 1]) {
                            M[y][x] = M[y - 1][x] + e;
                            path[y][x] = x;
                        } else {
                            M[y][x] = M[y - 1][x + 1] + e;
                            path[y][x] = x + 1;
                        }
                    } else if (x < w - 1) {
                        double minVal = 0.0;
                        int pos = x;
                        if (M[y - 1][x] < M[y - 1][x - 1]) {
                            minVal = M[y - 1][x];
                        } else {
                            minVal = M[y - 1][x - 1];
                            pos = x - 1;
                        }
                        if (minVal > M[y - 1][x + 1]) {
                            minVal = M[y - 1][x + 1];
                            pos = x + 1;
                        }
                        M[y][x] = minVal + e;
                        path[y][x] = pos;
                    } else {
                        if (M[y - 1][x] < M[y - 1][x - 1]) {
                            M[y][x] = M[y - 1][x] + e;
                            path[y][x] = x;
                        } else {
                            M[y][x] = M[y - 1][x - 1] + e;
                            path[y][x] = x - 1;
                        }
                    }
                }
            }
        }
        int hIndex = h - 1;
        int wIndex = 0;
        double minVal = Double.MAX_VALUE;
        for (int i = 0; i < w; ++i) {
            if (minVal > M[h - 1][i]) {
                minVal = M[h - 1][i];
                wIndex = i;
            }
        }
        while (hIndex >= 0) {
            ans[hIndex] = wIndex;
            wIndex = path[hIndex][wIndex];
            hIndex--;
        }
        return ans;
    }

    public void removeHorizontalSeam(int[] seam) {
        isValidSeam(seam, width());
        SeamRemover.removeHorizontalSeam(picture, seam);
    }   // remove horizontal seam from picture

    public void removeVerticalSeam(int[] seam) {
        isValidSeam(seam, height());
        SeamRemover.removeHorizontalSeam(picture, seam);
    }
    private void isValidSeam(int[] seam, int len) {
        if (seam.length != len) {
            throw new IllegalArgumentException();
        }
        for (int i = 1; i < len; ++i) {
            if (Math.abs(seam[i] - seam[i - 1]) > 1) {
                throw new IllegalArgumentException();
            }
        }
    }
}
