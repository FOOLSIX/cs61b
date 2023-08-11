package byog.Core;

import java.io.Serializable;
/**
 * 不要存储复杂的类
 * 比如TETile, 整个Game
 * 自动评测机大概率出错(c001)
 **/
public class SaveData implements Serializable {
    long seed;
    int playerx;
    int playery;
    public SaveData(long s, int x, int y) {
        seed = s;
        playerx = x;
        playery = y;
    }

}
