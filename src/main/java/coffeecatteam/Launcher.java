package coffeecatteam;

import coffeecatteam.levelcreator.LevelCreator;
import coffeecatteam.theultimatetile.GameEngine;
import coffeecatteam.utils.DiscordHandler;
import coffeecatteam.utils.Logger;

public class Launcher {

    public static void main(String[] args) {
        int width = 700;
        int height = 700;
        String title = "The Ultimate Tile";
        Logger.init();

        boolean LEVEL_CREATE = args[0].equals("LEVEL_CREATE");
        DiscordHandler.INSTANCE.LEVEL_CREATE(LEVEL_CREATE);
        if (LEVEL_CREATE) {
            LevelCreator levelCreator = new LevelCreator(args, title + " - Level Creator", width, height);
            levelCreator.start();
        } else {
            GameEngine gameEngine = new GameEngine(args, title, width, height);
            gameEngine.start();
        }
    }
}
