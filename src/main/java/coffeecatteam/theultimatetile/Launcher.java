package coffeecatteam.theultimatetile;

import coffeecatteam.coffeecatutils.ArgUtils;
import coffeecatteam.coffeecatutils.logger.CatLoggerUtils;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.levelcreator.CreatorEngine;
import coffeecatteam.theultimatetile.utils.DiscordHandler;

public class Launcher {

    public static void main(String[] args) {
        int width = 700;
        int height = 700;
        String title = "The Ultimate Tile";

        CatLoggerUtils.setOutputLog(ArgUtils.hasArgument(args, "-outputLog"));
        CatLoggerUtils.init();

        boolean LEVEL_CREATE = ArgUtils.hasArgument(args, "-startLevelCreator");
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
