package io.github.vampirestudios.tdg.start;

import coffeecatteam.coffeecatutils.ArgUtils;
import coffeecatteam.coffeecatutils.DevEnvUtils;
import coffeecatteam.coffeecatutils.logger.CatLogger;
import coffeecatteam.coffeecatutils.logger.CatLoggerUtils;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.DesktopMini2DxGame;
import com.badlogic.gdx.graphics.Color;
import io.github.vampirestudios.tdg.objs.tiles.Tile;
import io.github.vampirestudios.tdg.screen.game.GameScreen;
import org.mini2Dx.core.game.ScreenBasedGame;
import org.mini2Dx.core.screen.transition.FadeInTransition;
import org.mini2Dx.core.screen.transition.NullTransition;
import org.mini2Dx.desktop.DesktopMini2DxConfig;

import java.awt.*;
import java.io.File;

public class MaewilLauncher extends ScreenBasedGame {

    /*
     * Screen Ids
     */
    public static final int ID_SPLASHSCREEN = 0;
    public static final int ID_GAME = 1;

    /*
     * Properties
     */
    public static String[] ARGS;
    public static boolean FULLSCREEN;
    public static int WIDTH = 854, HEIGHT = 480, WIDTH_TILE_SIZE, HEIGHT_TILE_SIZE;
    public static final String TITLE = "Maewil";
    public static final String GAME_IDENTIFIER = "io.github.vampirestudios.maewil";

    public static CatLogger LOGGER;

    public MaewilLauncher() {
        super();
        LOGGER = new CatLogger("Maewil-Main");
        StringBuilder args = new StringBuilder();
        for (String arg : ARGS)
            args.append(arg).append((arg.equals(ARGS[ARGS.length - 1]) ? "" : ","));
        LOGGER.info("System args [" + args.toString() + "]");
        LOGGER.info("Window size set to [" + WIDTH + "x" + HEIGHT + "]");
        LOGGER.info("Fullscreen set to [" + FULLSCREEN + "]");
    }

    /**
     * Returns the identifier of the {@link GameScreen} that should be shown
     * when the game starts
     *
     * @return The {@link GameScreen} identifier via {@link GameScreen}.getId()
     */
    @Override
    public int getInitialScreenId() {
        return ID_GAME;
    }

    /**
     * Initialse the game
     */
    @Override
    public void initialise() {
        this.addScreen(new SplashScreen());
        this.addScreen(new MaewilEngine());

        if (ArgUtils.hasArgument("-uiTest"))
            this.enterGameScreen(ID_GAME, new NullTransition(), new NullTransition());
        else
            this.enterGameScreen(ID_SPLASHSCREEN, new FadeInTransition(Color.BLACK), new NullTransition());
    }

    /**
     * Update the game
     *
     * @param delta The time in seconds since the last update
     */
    @Override
    public void update(float delta) {
        this.getScreenManager().update(this, delta);
    }

    /**
     * Interpolate the game state
     *
     * @param alpha The alpha value to use during interpolation
     */
    @Override
    public void interpolate(float alpha) {
        this.getScreenManager().interpolate(this, alpha);
    }

    /**
     * Render the game
     *
     * @param g The {@link Graphics} context available for rendering
     */
    @Override
    public void render(org.mini2Dx.core.graphics.Graphics g) {
        this.getScreenManager().render(this, g);
    }

    @Override
    protected void postinit() {
        super.postinit();
        /*if (MaewilEngine.INSTANCE.isPlayBGMusic() && Sounds.BG_MUSIC.playing())
            Sounds.BG_MUSIC.stop();*/
        /*LOGGER.warn("Shutting down [" + TITLE + "] engine!");

        if (ScreenManager.getCurrentScreen() instanceof GameScreen) {
            LOGGER.info("Saving world!");
            ((GameScreen) ScreenManager.getCurrentScreen()).saveWorld(true);
            LOGGER.info("World saved!");
        }

        DiscordHandler.INSTANCE.shutdown();

        LOGGER.warn("Exiting [" + TITLE + "]..");*/
    }

    public static void main(String[] args) {
        String icons;
        /* Set natives path */
        if (!DevEnvUtils.isRunningFromDevEnviroment()) {
            final String nativesPath = new File("data/natives").getAbsolutePath();
            System.setProperty("org.lwjgl.librarypath", nativesPath);
            System.setProperty("java.library.path", nativesPath);
            icons = "data/icons/64.png";
        } else {
            final String nativesPath = new File("../libs/natives").getAbsolutePath();
            System.setProperty("org.lwjgl.librarypath", nativesPath);
            System.setProperty("java.library.path", nativesPath);
            icons = "../data/icons/64.png";
        }

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
        DesktopMini2DxConfig cfg = new DesktopMini2DxConfig(GAME_IDENTIFIER);
        cfg.title = TITLE;
        cfg.width = WIDTH;
        cfg.height = HEIGHT;
        cfg.vSyncEnabled = true;
        cfg.addIcon(icons, Files.FileType.Local);
        new DesktopMini2DxGame(new MaewilLauncher(), cfg);
    }

}
