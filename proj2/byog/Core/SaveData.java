package byog.Core;

import java.io.Serializable;

public class SaveData implements Serializable {
    public long seed;
    public int playerx;
    public int playery;
    public SaveData(long s, int x, int y) {
        seed = s;
        playerx = x;
        playery = y;
    }

}
