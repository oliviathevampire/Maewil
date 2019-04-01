package coffeecatteam.theultimatetile;

import coffeecatteam.coffeecatutils.logger.CatLogger;
import coffeecatteam.theultimatetile.gfx.assets.Sounds;
import coffeecatteam.theultimatetile.state.StateManager;
import coffeecatteam.theultimatetile.state.game.StateGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeInTransition;

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
        this.addState(new TutEngine(ARGS));

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
}
