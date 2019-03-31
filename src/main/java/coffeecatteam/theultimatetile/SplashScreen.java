package coffeecatteam.theultimatetile;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.gfx.assets.Sounds;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;

/**
 * @author CoffeeCatRailway
 * Created: 31/03/2019
 */
public class SplashScreen extends BasicGameState {

    private Vector2D titleScale = new Vector2D(0, 0);
    private Animation player;
    private float playerTimer = 0, maxPlayerTimer = 3.5f, py;
    private float endTimer = 0, maxEndTimer = 6f;

    public SplashScreen() {
    }

    @Override
    public int getID() {
        return TutLauncher.ID_SPLASHSCREEN;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        TutLauncher.LOGGER.warn("Fullscreen is set to [" + container.isFullscreen() + "]");

        Assets.init();
        Sounds.init();
        container.setDefaultFont(Assets.FONTS.get("10"));
        container.setMouseCursor(Assets.CURSOR, 0, 0);

        player = new Animation(135, Assets.SPLASH_PLAYER);
        py = TutLauncher.HEIGHT;
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        titleScale.x = NumberUtils.lerp((float) titleScale.x, 1.0f, 0.05f);
        playerTimer = NumberUtils.lerp(playerTimer, maxPlayerTimer, 0.05f);
        player.update();

        if (endTimer >= maxEndTimer - 0.5f)
            game.enterState(TutLauncher.ID_GAME, new FadeInTransition(Color.black), new FadeInTransition(Color.black));
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        Image title = Assets.TITLE_SMALL;
        float size = 10f;
        float width = (float) ((title.getWidth() / size) * titleScale.x);
        float height = (float) ((title.getHeight() / size) * titleScale.x);
        title.draw(TutLauncher.WIDTH / 2f - width / 2f, TutLauncher.HEIGHT / 2f - height / 2f, width, height);

        if (playerTimer >= maxPlayerTimer - 0.5f) {
            Image frame = player.getCurrentFrame();
            float psize = 150f;
            frame.draw(TutLauncher.WIDTH - psize * 2.5f, py, psize, psize);
            py = NumberUtils.lerp(py, TutLauncher.HEIGHT - psize / 2f, 0.05f);

            endTimer = NumberUtils.lerp(endTimer, maxEndTimer, 0.01f);
        }
    }
}
