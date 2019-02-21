package coffeecatteam.theultimatetile;

import coffeecatteam.coffeecatutils.ArgUtils;
import coffeecatteam.coffeecatutils.DevEnvUtils;
import coffeecatteam.coffeecatutils.logger.CatLoggerUtils;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import java.awt.*;
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
        int width = 912;
        int height = 768;
        String title = "The Ultimate Tile";

        boolean isFullscreen = ArgUtils.hasArgument(args, "-fullscreen");
        if (isFullscreen) {
            width = Toolkit.getDefaultToolkit().getScreenSize().width;
            height = Toolkit.getDefaultToolkit().getScreenSize().height;
        }

        /* Initialize logger */
        CatLoggerUtils.setOutputLog(ArgUtils.hasArgument(args, "-outputLog"));
        CatLoggerUtils.init();

        /* Start game */
        try {
            AppGameContainer app = new AppGameContainer(new TutEngine(args, title, width, height));
            app.setDisplayMode(width, height, false);
            app.setIcons(new String[]{"icons/32.png", "icons/64.png"});
            app.setTargetFrameRate(100);
            app.setUpdateOnlyWhenVisible(false);
            app.setAlwaysRender(true);
            app.setVSync(true);
            app.setFullscreen(isFullscreen);
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
