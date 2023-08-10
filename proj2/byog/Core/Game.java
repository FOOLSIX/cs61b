package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    private TETile[][] t;
    private enum DIRECTION {
        UP, RIGHT, DOWN, LEFT;

    }
    public Game() {
        //ter.initialize(WIDTH, HEIGHT);
        t = new TETile[WIDTH][HEIGHT];
        //Init TETile
        for (int i = 0; i < WIDTH; ++i) {
            for (int j = 0; j < HEIGHT; ++j) {
                t[i][j] = Tileset.NOTHING;
            }
        }
    }
    private final int[][] nxt = {{0, 1}, {1, 0}, {0, -1}, {-1, 0},
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
    private void exitGame() {
        //TODO: save

        System.exit(0);
    }
    private void loadGame() {
        //TODO
    }

    private long strToSeed(String s) {
        int idx = 1;
        long ret = 0L;
        while(s.charAt(idx) != 'S' && s.charAt(idx) != 's') {
            ret = ret * 10 +(s.charAt(idx) - '0');
            idx++;
        }
        return ret;
    }

    private boolean inValid(int x, int y) {
        return (x > 0 && y > 0 && x < WIDTH - 1 && y < HEIGHT - 1);
    }

    private void drawWall() {
        for (int i = 1; i < WIDTH - 1; ++i) {
            for (int j = 1; j < HEIGHT - 1; ++j) {
                if (t[i][j] == Tileset.FLOOR) {
                    for (int k = 0; k < 8; ++k) {
                        int nx = i + nxt[k][0], ny = j + nxt[k][1];
                        if(t[nx][ny] == Tileset.NOTHING) {
                            t[nx][ny] = Tileset.WALL;
                        }
                    }
                }
            }
        }
    }
    private void drawLine(int x, int y, int len, DIRECTION d) {
        int dir = d.ordinal();
        while (len-- >= 0) {
            if(inValid(x, y)) {
                t[x][y] = Tileset.FLOOR;
            } else {
                break;
            }
            x += nxt[dir][0];
            y += nxt[dir][1];
        }
    }

    private void generateTile(long seed) {
        Random rand = new Random(seed);
        //a tes
        for (int i = 0; i < 20; ++i) {
            int x = RandomUtils.uniform(rand, 1, WIDTH - 1);
            int y = RandomUtils.uniform(rand, 1, HEIGHT - 1);
            int len = RandomUtils.uniform(rand, 3, 15);
            DIRECTION d = DIRECTION.values()[RandomUtils.uniform(rand, 0, 4)];;
            drawLine(x, y, len, d);
        }
        drawWall();
        return;
    }
    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        long seed = 0L;
        switch (input.charAt(0)) {
            case 'N':
            case 'n':
                seed = strToSeed(input);
                break;
            case 'Q':
            case 'q':
                exitGame();
            case 'L':
            case 'l':
                loadGame();
            default:
                break;
        }

        generateTile(seed);
        //tes
        //ter.renderFrame(t);
        return t;
    }
}
