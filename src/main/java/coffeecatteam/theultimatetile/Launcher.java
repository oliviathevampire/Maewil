package coffeecatteam.theultimatetile;

import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.levelcreator.CreatorEngine;
import coffeecatteam.theultimatetile.utils.DiscordHandler;
import coffeecatteam.coffeecatutils.Logger;

public class Launcher {

    public static void main(String[] args) {
        int width = 700;
        int height = 700;
        String title = "The Ultimate Tile";
        Logger.init();

        boolean LEVEL_CREATE = (args.length > 0 && args[0].equals("LEVEL_CREATE"));
        DiscordHandler.INSTANCE.LEVEL_CREATE(LEVEL_CREATE);
        if (LEVEL_CREATE) {
            CreatorEngine creatorEngine = new CreatorEngine(args, title + " - Level Creator", width, height);
            creatorEngine.start();
        } else {
            GameEngine gameEngine = new GameEngine(args, title, width, height);
            gameEngine.start();
        }
    }
}
