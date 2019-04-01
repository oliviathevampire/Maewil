package coffeecatteam.theultimatetile;

import coffeecatteam.coffeecatutils.ArgUtils;
import coffeecatteam.coffeecatutils.DevEnvUtils;
import coffeecatteam.coffeecatutils.logger.CatLoggerUtils;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import java.awt.*;
import java.io.File;

/**
 * @author CoffeeCatRailway
 * Created: 2/04/2019
 */
public class Start {

    public static void main(String[] args) {
        /* Set natives path */
        if (!DevEnvUtils.isRunningFromDevEnviroment()) {
            final String nativesPath = new File("data/natives").getAbsolutePath();
            System.setProperty("org.lwjgl.librarypath", nativesPath);
            System.setProperty("java.library.path", nativesPath);
        }

        TutLauncher.ARGS = args;
        TutLauncher.FULLSCREEN = ArgUtils.hasArgument(TutLauncher.ARGS, "-fullscreen");
        if (TutLauncher.FULLSCREEN) {
            TutLauncher.WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
            TutLauncher.HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
        }

        /* Initialize logger */
        CatLoggerUtils.setOutputLog(ArgUtils.hasArgument(TutLauncher.ARGS, "-outputLog"));
        CatLoggerUtils.init();

        /* Start game */
        try {
            AppGameContainer app = new AppGameContainer(new TutLauncher());
            app.setShowFPS(false);
            app.setDisplayMode(TutLauncher.WIDTH, TutLauncher.HEIGHT, TutLauncher.FULLSCREEN);
            app.setIcons(new String[]{"data/icons/32.png", "data/icons/64.png"});
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
