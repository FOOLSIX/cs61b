package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 15;
    private static final int HEIGHT = 15;

    public static void addHexagon(TETile[][] g, int x, int y, int sz, TETile t) {
        for(int i = 0; i < sz;++i) {
            int startx = sz - 1 - i;
            for(int j = 0; j < sz + 2 * i; ++j) {
                g[x + startx + j][y + i] = t;
                g[x + startx + j][y + 2 * sz - 1 - i] = t;
            }
        }
        return;
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile grid[][] = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                grid[x][y] = Tileset.NOTHING;
            }
        }
        addHexagon(grid, 0, 0, 2, Tileset.FLOWER);
        addHexagon(grid, 3, 2, 2, Tileset.WALL);
        addHexagon(grid, 6, 0, 2, Tileset.FLOWER);

        ter.renderFrame(grid);
    }
}
