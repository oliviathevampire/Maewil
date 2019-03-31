package coffeecatteam.theultimatetile;

import coffeecatteam.coffeecatutils.ArgUtils;
import coffeecatteam.coffeecatutils.DevEnvUtils;
import coffeecatteam.coffeecatutils.logger.CatLogger;
import coffeecatteam.coffeecatutils.logger.CatLoggerUtils;
import coffeecatteam.theultimatetile.gfx.assets.Sounds;
import coffeecatteam.theultimatetile.state.StateManager;
import coffeecatteam.theultimatetile.state.game.StateGame;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeInTransition;

import java.awt.*;
import java.io.File;

public class TutLauncher extends StateBasedGame {

    /*
     * State Ids
     */
    public static final int ID_SPLASHSCREEN = 0;
    public static final int ID_GAME = 1;

    /*
     * Properties
     */
    private static String[] args;
    public static boolean FULLSCREEN;
    public static int WIDTH = 912, HEIGHT = 768;
    public static final String TITLE = "The Ultimate Tile";

    public static CatLogger LOGGER;

    public TutLauncher() {
        super(TITLE);
        LOGGER = new CatLogger("Client-Thread");
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        this.addState(new SplashScreen());
        this.addState(new TutEngine(args));

        this.enterState(ID_SPLASHSCREEN, new FadeInTransition(Color.black), new EmptyTransition());
    }

    @Override
    public boolean closeRequested() {
        if (TutEngine.getTutEngine().isPlayBGMusic() && Sounds.BG_MUSIC.playing())
            Sounds.BG_MUSIC.stop();
        LOGGER.warn("Shutting down [" + TITLE + "] engine!");
        if (StateManager.getCurrentState() instanceof StateGame) {
            LOGGER.info("Saving world!");
            ((StateGame) StateManager.getCurrentState()).saveWorld(true);
            LOGGER.info("World saved!");
        }

        LOGGER.warn("Exiting [" + TITLE + "]..");
        return true;
    }

    public static void main(String[] argsIn) {
        /* Set natives path */
        if (!DevEnvUtils.isRunningFromDevEnviroment()) {
            final String nativesPath = new File("natives").getAbsolutePath();
            System.setProperty("org.lwjgl.librarypath", nativesPath);
            System.setProperty("java.library.path", nativesPath);
        }

        args = argsIn;
        FULLSCREEN = ArgUtils.hasArgument(args, "-fullscreen");
        if (FULLSCREEN) {
            WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
            HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
        }

        /* Initialize logger */
        CatLoggerUtils.setOutputLog(ArgUtils.hasArgument(args, "-outputLog"));
        CatLoggerUtils.init();

        /* Start game */
        try {
            AppGameContainer app = new AppGameContainer(new TutLauncher());
            app.setShowFPS(false);
            app.setDisplayMode(WIDTH, HEIGHT, false);
            app.setIcons(new String[]{"icons/32.png", "icons/64.png"});
            app.setTargetFrameRate(100);
            app.setUpdateOnlyWhenVisible(false);
            app.setAlwaysRender(true);
            app.setVSync(true);
            app.setFullscreen(FULLSCREEN);
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
