package byog.Core;

import byog.TileEngine.TETile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.IOException;

/** This is the main entry point for the program. This class simply parses
 *  the command line inputs, and lets the byog.Core.Game class take over
 *  in either keyboard or input string mode.
 */
public class Main {

    public static void main(String[] args) {
        Game game = loadworld();
        if (args.length > 1) {
            System.out.println("Can only have one argument - the input string");
            System.exit(0);
        } else if (args.length == 1) {
            if (game == null) {
                game = new Game();
            }
            TETile[][] worldState = game.playWithInputString(args[0]);
            System.out.println(TETile.toString(worldState));
        } else {
            if (game == null) {
                game = new Game();
            }
            game.playWithKeyboard();

        }
    }
    private static Game loadworld() {
        File f = new File("./savefile.txt");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                Game loadWorld = (Game) os.readObject();
                os.close();
                return loadWorld;
            } catch (ClassNotFoundException | IOException e) {
                System.exit(0);
            }
        }

        return new Game();
    }
}
