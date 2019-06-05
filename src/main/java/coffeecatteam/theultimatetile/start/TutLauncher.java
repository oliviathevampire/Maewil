package coffeecatteam.theultimatetile.start;

import coffeecatteam.coffeecatutils.ArgUtils;
import coffeecatteam.coffeecatutils.logger.CatLogger;
import coffeecatteam.coffeecatutils.logger.CatLoggerUtils;
import coffeecatteam.theultimatetile.gfx.assets.Sounds;
import coffeecatteam.theultimatetile.objs.tiles.Tile;
import coffeecatteam.theultimatetile.state.StateManager;
import coffeecatteam.theultimatetile.state.game.StateGame;
import coffeecatteam.theultimatetile.utils.DiscordHandler;
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
    public static String[] ARGS;
    public static boolean FULLSCREEN;
    public static int WIDTH = 854, HEIGHT = 480, WIDTH_TILE_SIZE, HEIGHT_TILE_SIZE;
    public static final String TITLE = "The Ultimate Tile";

    public static CatLogger LOGGER;

    public TutLauncher() {
        super(TITLE);
        LOGGER = new CatLogger("TheUltimateTile-Main");

        StringBuilder args = new StringBuilder();
        for (String arg : ARGS)
            args.append(arg).append((arg.equals(ARGS[ARGS.length - 1]) ? "" : ","));
        LOGGER.info("System args [" + args.toString() + "]");
        LOGGER.info("Window size set to [" + WIDTH + "x" + HEIGHT + "]");
        LOGGER.info("Fullscreen set to [" + FULLSCREEN + "]");
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        this.addState(new SplashScreen());
        this.addState(new TutEngine());

        if (ArgUtils.hasArgument("-uiTest"))
            this.enterState(ID_GAME, new EmptyTransition(), new EmptyTransition());
        else
            this.enterState(ID_SPLASHSCREEN, new FadeInTransition(Color.black), new EmptyTransition());
    }

    @Override
    public boolean closeRequested() {
        if (TutEngine.INSTANCE.isPlayBGMusic() && Sounds.BG_MUSIC.playing())
            Sounds.BG_MUSIC.stop();
        LOGGER.warn("Shutting down [" + TITLE + "] engine!");

        if (StateManager.getCurrentState() instanceof StateGame) {
            LOGGER.info("Saving world!");
            ((StateGame) StateManager.getCurrentState()).saveWorld(true);
            LOGGER.info("World saved!");
        }

        DiscordHandler.INSTANCE.shutdown();

        LOGGER.warn("Exiting [" + TITLE + "]..");
        return true;
    }

    public static void main(String[] args) {
        /* Set natives path */
        final String nativesPath = new File("data/natives").getAbsolutePath();
        System.setProperty("net.java.games.input.librarypath", nativesPath);
        System.setProperty("org.lwjgl.librarypath", nativesPath);
        System.setProperty("java.library.path", nativesPath);

        ARGS = args;
        ArgUtils.setARGS(ARGS);
        FULLSCREEN = ArgUtils.hasArgument("-fullscreen");
        if (FULLSCREEN) {
            WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
            HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
        }
        WIDTH_TILE_SIZE = WIDTH / Tile.TILE_SIZE;
        HEIGHT_TILE_SIZE = HEIGHT / Tile.TILE_SIZE;

        /* Initialize logger */
        CatLoggerUtils.setOutputLog(!ArgUtils.hasArgument("-dontOutputLog"));
        CatLoggerUtils.init();

        /* Start game */
        try {
            AppGameContainer app = new AppGameContainer(new TutLauncher());
            app.setShowFPS(false);
            app.setDisplayMode(WIDTH, HEIGHT, FULLSCREEN);
            app.setIcons(new String[]{"data/icons/32.png", "data/icons/64.png"});
            app.setTargetFrameRate(100);
            app.setUpdateOnlyWhenVisible(false);
            app.setAlwaysRender(true);
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
