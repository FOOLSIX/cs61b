package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class BYoGPlayer {
    int x;
    int y;
    boolean isValid;
    public BYoGPlayer(int px, int py, boolean statue) {
        x = px;
        y = py;
        isValid = statue;
    }

    public void setPos(TETile[][] t, int nx, int ny) {
        if (isValid) {
            t[x][y] = Tileset.FLOOR;
        }
        x = nx;
        y = ny;
        t[x][y] = Tileset.PLAYER;
    }
    public void loadPos(TETile[][] t) {
        t[x][y] = Tileset.PLAYER;
    }
    public void playerMove(TETile[][] t, Game.DIRECTION d) {
        int dir = d.ordinal();
        int nx = x + Game.NXT[dir][0];
        int ny = y + Game.NXT[dir][1];
        if (t[nx][ny].character() == Tileset.FLOOR.character()) {
            setPos(t, nx, ny);
        }
    }
    public void fromKeyboardMove(TETile[][] t, char key) {
        switch (key) {
            case 'w':
            case 'W':
                playerMove(t, Game.DIRECTION.UP);
                break;
            case 'd':
            case 'D':
                playerMove(t, Game.DIRECTION.RIGHT);
                break;
            case 's':
            case 'S':
                playerMove(t, Game.DIRECTION.DOWN);
                break;
            case 'a':
            case 'A':
                playerMove(t, Game.DIRECTION.LEFT);
                break;
            default:
                break;
        }
    }
}
