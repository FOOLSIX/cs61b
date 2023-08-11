package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;
import java.awt.Font;
import java.awt.Color;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.Random;

public class Game {

    private boolean isloaded = false;
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private long seed;
    enum DIRECTION {
        UP, RIGHT, DOWN, LEFT;

    }
    private BYoGPlayer player;

    TETile[][] t = new TETile[WIDTH][HEIGHT];
    public Game() {
        player = new BYoGPlayer(0, 0, false);
    }
    private void getPlayerPos(Random rand) {
        while (!player.isValid) {
            for (int i = 1; i < WIDTH - 1; ++i) {
                for (int j = 1; j < HEIGHT - 1; ++j) {
                    if (t[i][j] == Tileset.FLOOR) {
                        if (RandomUtils.uniform(rand, 0, 100) == 0) {
                            player.setPos(t, i, j);
                            player.isValid = true;
                            return;
                        }
                    }
                }
            }
        }
    }
    static final int[][] NXT = {
            {0, 1}, {1, 0}, {0, -1}, {-1, 0},
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
    };
    /**return true if launch a new game*/
    private boolean menu() {
        StdDraw.clear(Color.BLACK);
        double midwidth = (double) WIDTH / 2;
        double midheight = (double) HEIGHT / 2;

        Font sFont = new Font("Monaco", Font.BOLD, 40);
        StdDraw.setFont(sFont);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(midwidth, midheight + 5, "CS61B");
        sFont = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(sFont);
        StdDraw.text(midwidth, midheight - 5, "(N) NEW GAME");
        StdDraw.text(midwidth, midheight - 7, "(L) LOAD GAME");
        StdDraw.text(midwidth, midheight - 9, "(Q) QUIT");
        StdDraw.show();

        char key;
        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                StdDraw.pause(500);
                continue;
            }
            key = StdDraw.nextKeyTyped();
            switch (key) {
                case 'n':
                    return true;
                case 'l':
                    loadGame();
                    return false;
                case 'q':
                    exitGame(false);
                    break;
                default:
                    break;
            }
            
        }
    }
    private void saveGame() {
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("../saved.txt"));
            SaveData save = new SaveData(seed, player.x, player.y);
            os.writeObject(save);
            os.close();
        }  catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void exitGame(boolean needSave) {
        if (needSave) {
            saveGame();
        }
        System.exit(0);
    }
    private void loadGame() {
        isloaded = true;
        try {
            ObjectInputStream os = new ObjectInputStream(new FileInputStream("../saved.txt"));
            SaveData loadWorld = (SaveData) os.readObject();
            seed = loadWorld.seed;
            generateWorld();
            player = new BYoGPlayer(loadWorld.playerx, loadWorld.playery, true);
            player.loadPos(t);
            os.close();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    private long strToSeed(String s) {
        int idx = 1;
        long ret = 0L;
        while (s.charAt(idx) != 'S' && s.charAt(idx) != 's') {
            ret = ret * 10 + (s.charAt(idx) - '0');
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
                        int nx = i + NXT[k][0], ny = j + NXT[k][1];
                        if (t[nx][ny] == Tileset.NOTHING) {
                            t[nx][ny] = Tileset.WALL;
                        }
                    }
                }
            }
        }
    }

    private void drawRectangle(int x, int y, int w, int h) {
        for (int i = 0; i <= w; ++i) {
            if (!inValid(x + i, y)) {
                break;
            }
            for (int j = 0; j <= h; ++j) {
                if (inValid(x + i, y + j)) {
                    t[x + i][y + j] = Tileset.FLOOR;
                } else {
                    break;
                }
            }
        }
    }
    private void drawLine(int x, int y, int len, DIRECTION d) {
        int dir = d.ordinal();
        while (len-- >= 0) {
            if (inValid(x, y)) {
                t[x][y] = Tileset.FLOOR;
            } else {
                break;
            }
            x += NXT[dir][0];
            y += NXT[dir][1];
        }
    }

    private String inputSeed() {
        char key;
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font sFont = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(sFont);
        StdDraw.show();
        key = ' ';
        StringBuilder strseed = new StringBuilder();
        while (key != 's') {
            if (!StdDraw.hasNextKeyTyped()) {
                StdDraw.pause(50);
                continue;
            }
            key = StdDraw.nextKeyTyped();
            strseed.append(String.valueOf(key));
            StdDraw.clear(Color.BLACK);
            StdDraw.text((double) WIDTH / 2, (double) HEIGHT / 2, strseed.toString());
            StdDraw.show();
        }
        return strseed.toString();
    }

    private void generateWorld() {
        Random rand = new Random(seed);
        //Init
        t = new TETile[WIDTH][HEIGHT];
        for (int i = 0; i < WIDTH; ++i) {
            for (int j = 0; j < HEIGHT; ++j) {
                t[i][j] = Tileset.NOTHING;
            }
        }

        int x = 1;
        int y = RandomUtils.uniform(rand, 1, HEIGHT - 1);
        int dir = 1;
        for (int i = 0; i < 30; ++i) {
            int len = RandomUtils.uniform(rand, 3, 40);
            DIRECTION d = DIRECTION.values()[dir];
            drawLine(x, y, len, d);
            x += len * NXT[dir][0];
            y += len * NXT[dir][1];
            if (x >= WIDTH - 1) {
                x = WIDTH - 2;
            }
            if (x <= 0) {
                x = 0;
            }
            if (y >= HEIGHT - 1) {
                y = HEIGHT - 2;
            }
            if (y <= 0) {
                y = 0;
            }
            int nxtdir = RandomUtils.uniform(rand, 0, 4);
            while (dir == nxtdir) {
                nxtdir = RandomUtils.uniform(rand, 0, 4);
            }
            dir = nxtdir;
        }
        for (int i = 1; i < WIDTH - 1; ++i) {
            for (int j = 1; j < HEIGHT - 1; ++j) {
                if (t[i][j] == Tileset.FLOOR) {
                    if (RandomUtils.uniform(rand, 0, 15) == 0) {
                        int w = RandomUtils.uniform(rand, 0, 10);
                        int h = RandomUtils.uniform(rand, 0, 10);
                        drawRectangle(i, j, w, h);
                    }
                }
            }
        }

        drawWall();
        if (!isloaded) {
            player.isValid = false;
            getPlayerPos(rand);
        }

    }
    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        TERenderer ter = new TERenderer();
        char key;
        ter.initialize(WIDTH, HEIGHT + 2);
        if (menu()) {
            seed = (strToSeed("N" + inputSeed() + "S"));
            Random rand = new Random(seed);
            isloaded = false;
            generateWorld();
        } else {
            loadGame();
        }

        Font sFont = new Font("Monaco", Font.BOLD, 16);
        StdDraw.setFont(sFont);
        StdDraw.clear(Color.BLACK);
        player.loadPos(t);
        ter.renderFrame(t);

        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                StdDraw.pause(50);
                continue;
            }
            key = StdDraw.nextKeyTyped();
            player.fromKeyboardMove(t, key);
            if (key == 'Q' || key == 'q') {
                exitGame(true);
            }
            StdDraw.clear(Color.BLACK);
            ter.renderFrame(t);
        }
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
        //Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        //TERenderer ter = new TERenderer();
        //ter.initialize(WIDTH, HEIGHT);
        switch (input.charAt(0)) {
            case 'N':
            case 'n':
                seed = strToSeed(input);
                isloaded = false;
                generateWorld();
                break;
            case 'Q':
            case 'q':
                exitGame(false);
                break;
            case 'L':
            case 'l':
                loadGame();
                break;
            default:
                break;
        }
        int len = input.length();
        int idx;
        if (!isloaded) {
            idx = input.indexOf('S');
            if (idx == -1) {
                idx = input.indexOf('s');
            }
        } else {
            idx = 0;
        }
        ++idx;
        while (idx < len) {
            switch (input.charAt(idx)) {
                case 'Q':
                case 'q':
                    saveGame();
                    return t;
                default:
                    player.fromKeyboardMove(t, input.charAt(idx));
            }
            ++idx;
        }
        //tes
        //ter.renderFrame(t);
        return t;
    }
}
