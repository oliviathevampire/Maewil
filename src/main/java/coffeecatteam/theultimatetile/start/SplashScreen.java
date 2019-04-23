package coffeecatteam.theultimatetile.start;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.gfx.assets.Sounds;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeInTransition;

/**
 * @author CoffeeCatRailway
 * Created: 31/03/2019
 */
public class SplashScreen extends BasicGameState {

    private float titleScale = 0, smoothness = 0.05f;
    private float endTimer = 0, maxEndTimer = 2.5f;

    // Player movement steps
    private Animation player;
    private float playerTimer = 0, maxPlayerTimer = 3.5f;
    private float psize = 150f, px, py, p1, p2;
    private boolean playerExit = false;

    @Override
    public int getID() {
        return TutLauncher.ID_SPLASHSCREEN;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        Assets.init();
        Sounds.init();
        container.setDefaultFont(Assets.FONTS.get("10"));
        container.setMouseCursor(Assets.GUI_CURSOR, 0, 0);

        player = new Animation(135, Assets.GUI_SPLASH_PLAYER);
        p1 = TutLauncher.HEIGHT;
        px = TutLauncher.WIDTH - psize * 2.5f;
        py = TutLauncher.HEIGHT - psize / 2f;
        p2 = px;
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        titleScale = NumberUtils.lerp(titleScale, 1.0f, smoothness);
        playerTimer = NumberUtils.lerp(playerTimer, maxPlayerTimer, smoothness);

        if (p2 > TutLauncher.WIDTH)
            endTimer = NumberUtils.lerp(endTimer, maxEndTimer, smoothness / 5f);
        if (endTimer >= maxEndTimer - 0.5f)
            game.enterState(TutLauncher.ID_GAME, new EmptyTransition(), new FadeInTransition(Color.black));
        player.update();
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        Image title = Assets.GUI_TITLE_SMALL;
        float size = 10f;
        float width = (title.getWidth() / size) * titleScale;
        float height = (title.getHeight() / size) * titleScale;
        title.draw(TutLauncher.WIDTH / 2f - width / 2f, TutLauncher.HEIGHT / 2f - height / 2f, width, height);

        if (playerTimer >= maxPlayerTimer - 0.5f) {
            Image frame = player.getCurrentFrame();
            frame.draw(p2, p1, psize, psize);

            p1 = NumberUtils.lerp(p1, py, smoothness);
            float newX = px - 20f;
            if (p1 <= py + 1f && !playerExit)
                p2 = NumberUtils.lerp(p2, newX, smoothness);
            if (p2 <= newX + 0.5f)
                playerExit = true;
            if (playerExit)
                p2 = NumberUtils.lerp(p2, TutLauncher.WIDTH * 1.25f, smoothness);
        }
    }
}
