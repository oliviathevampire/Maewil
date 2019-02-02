package coffeecatteam.theultimatetile;

import coffeecatteam.coffeecatutils.ArgUtils;
import coffeecatteam.coffeecatutils.DevEnvUtils;
import coffeecatteam.coffeecatutils.logger.CatLoggerUtils;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.levelcreator.CreatorEngine;
import coffeecatteam.theultimatetile.utils.DiscordHandler;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import java.io.File;

public class Launcher {

    public static void main(String[] args) {
        /* Set natives path */
        if (!DevEnvUtils.isRunningFromDevEnviroment()) {
            final String nativesPath = new File("natives").getAbsolutePath();
            System.setProperty("org.lwjgl.librarypath", nativesPath);
            System.setProperty("java.library.path", nativesPath);
        }

        /* Width, height & title */
        int width = 850;
        int height = 800;
        String title = "The Ultimate Tile";

        /* Initialize logger */
        CatLoggerUtils.setOutputLog(ArgUtils.hasArgument(args, "-outputLog"));
        CatLoggerUtils.init();


        /* Check if your using the level creator */
        boolean LEVEL_CREATE = ArgUtils.hasArgument(args, "-startLevelCreator");
        DiscordHandler.INSTANCE.LEVEL_CREATE(LEVEL_CREATE);
        Engine engine;

        if (LEVEL_CREATE)
            engine = new CreatorEngine(args, title + " - Level Creator", width, height);
        else
            engine = new GameEngine(args, title, width, height);

        /* Start game */
        try {
            AppGameContainer app = new AppGameContainer(engine);
            app.setDisplayMode(width, height, false);
            app.setIcons(new String[]{"icons/32.png", "icons/64.png"});
            app.setTargetFrameRate(100);
            app.setUpdateOnlyWhenVisible(false);
            app.setAlwaysRender(true);
            app.setVSync(true);
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
