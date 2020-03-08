package io.github.vampirestudios.tdg.start;

import coffeecatteam.coffeecatutils.NumberUtils;
import com.badlogic.gdx.graphics.Color;
import io.github.vampirestudios.tdg.gfx.Animation;
import io.github.vampirestudios.tdg.gfx.assets.Assets;
import io.github.vampirestudios.tdg.gfx.assets.Sounds;
import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.screen.GameScreen;
import org.mini2Dx.core.screen.ScreenManager;
import org.mini2Dx.core.screen.Transition;
import org.mini2Dx.core.screen.transition.FadeInTransition;
import org.mini2Dx.core.screen.transition.NullTransition;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class SplashScreen implements GameScreen {

    private float titleScale = 0;
    private float endTimer = 0;

    // Player movement steps
    private Animation player;
    private float playerTimer = 0, maxPlayerTimer = 3.5f;
    private float psize = 150f, px, py, p1, p2;
    private boolean playerExit = false;

    @Override
    public void initialise(org.mini2Dx.core.game.GameContainer container) {
        Assets.init();
        try {
            Sounds.init();
        } catch (SlickException e) {
            e.printStackTrace();
        }
//        container.setDefaultFont(Assets.FONTS.get("10"));
//        container.setMouseCursor(Assets.GUI_CURSOR, 0, 0);

        player = new Animation(135, Assets.GUI_SPLASH_PLAYER);
        p1 = MaewilLauncher.HEIGHT;
        px = MaewilLauncher.WIDTH - psize * 2.5f;
        py = MaewilLauncher.HEIGHT - psize / 2f;
        p2 = px;
    }

    @Override
    public void update(org.mini2Dx.core.game.GameContainer gc, ScreenManager<? extends GameScreen> screenManager, float delta) {
        float smoothness = 0.05f;
        titleScale = NumberUtils.lerp(titleScale, 1.0f, smoothness);
        playerTimer = NumberUtils.lerp(playerTimer, maxPlayerTimer, smoothness);

        float maxEndTimer = 2.5f;
        if (p2 > MaewilLauncher.WIDTH)
            endTimer = NumberUtils.lerp(endTimer, maxEndTimer, smoothness / 5f);
        if (endTimer >= maxEndTimer - 0.5f)
            screenManager.enterGameScreen(MaewilLauncher.ID_GAME, new NullTransition(), new FadeInTransition(Color.BLACK));
        player.update();


        if (playerTimer >= maxPlayerTimer - 0.5f) {
            p1 = NumberUtils.lerp(p1, py, smoothness);
            float newX = px - 20f;
            if (p1 <= py + 1f && !playerExit)
                p2 = NumberUtils.lerp(p2, newX, smoothness);
            if (p2 <= newX + 0.5f)
                playerExit = true;
            if (playerExit)
                p2 = NumberUtils.lerp(p2, MaewilLauncher.WIDTH * 1.25f, smoothness);
        }
    }

    /**
     * Interpolate between the previous and current state
     *
     * @param gc
     * @param alpha The interpolation alpha value
     */
    @Override
    public void interpolate(GameContainer gc, float alpha) {

    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        Image title = Assets.GUI_TITLE_SMALL;
        float size = 10f;
        float width = (title.getWidth() / size) * titleScale;
        float height = (title.getHeight() / size) * titleScale;
        title.draw(MaewilLauncher.WIDTH / 2f - width / 2f, MaewilLauncher.HEIGHT / 2f - height / 2f, width, height);

        if (playerTimer >= maxPlayerTimer - 0.5f) {
            Image frame = player.getCurrentFrame();
            frame.draw(p2, p1, psize, psize);
        }
    }

    /**
     * Called when the game window's dimensions changes.
     * On mobile devices this is called when the screen is rotated.
     *
     * @param width  The new game window width
     * @param height The new game window height
     */
    @Override
    public void onResize(int width, int height) {

    }

    /**
     * Called when the game window is no longer active or visible.
     * On
     */
    @Override
    public void onPause() {

    }

    /**
     * Called when the game window becomes active or visible again
     */
    @Override
    public void onResume() {

    }

    /**
     * Called before the transition in
     *
     * @param transitionIn The {@link Transition} in to this screen
     */
    @Override
    public void preTransitionIn(Transition transitionIn) {

    }

    /**
     * Called after the transition in
     *
     * @param transitionIn The {@link Transition} in to this screen
     */
    @Override
    public void postTransitionIn(Transition transitionIn) {

    }

    /**
     * Called before the transition out
     *
     * @param transitionOut The {@link Transition} out from this screen
     */
    @Override
    public void preTransitionOut(Transition transitionOut) {

    }

    /**
     * Called after the transition out
     *
     * @param transitionOut The {@link Transition} out from this screen
     */
    @Override
    public void postTransitionOut(Transition transitionOut) {

    }

    /**
     * Returns the identifier of the screen
     *
     * @return A unique identifier
     */
    @Override
    public int getId() {
        return MaewilLauncher.ID_SPLASHSCREEN;
    }

}
