package coffeecatteam.tilegame.state;

import coffeecatteam.tilegame.Handler;
import coffeecatteam.tilegame.gfx.ui.UIButton;
import coffeecatteam.tilegame.gfx.ui.UIManager;
import coffeecatteam.tilegame.worlds.World;

import java.awt.*;
import java.awt.event.KeyEvent;

public class StateGame extends State {

    private World world;

    private boolean paused = false;

    private UIManager uiManager;

    public StateGame(Handler handler) {
        super(handler);
        world = new World(handler, "/assets/worlds/test_world2");
        handler.setWorld(world);
        uiManager = new UIManager(handler);
        init();

        int btnWidth = 192;
        int btnHeight = 64;
        int yOffset = 150;
        uiManager.addObject(new UIButton(handler.getWidth() / 2 - btnWidth / 2, handler.getHeight() / 2 - btnHeight / 2 + btnHeight - 50, btnWidth, btnHeight, "Resume", () ->
            paused = false
        ));
        uiManager.addObject(new UIButton(handler.getWidth() / 2 - btnWidth / 2, handler.getHeight() / 2 - btnHeight / 2 + btnHeight - 50 + yOffset, btnWidth, btnHeight, "Exit", () -> {
            handler.getMouseManager().setUiManager(null);
            State.setState(handler.getGame().menuState.init());
        }));
    }

    @Override
    public State init() {
        paused = false;
        handler.getMouseManager().setUiManager(uiManager);
        return this;
    }

    @Override
    public void tick() {
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE))
            paused = !paused;

        if (!paused)
            world.tick();
        else
            uiManager.tick();
    }

    @Override
    public void render(Graphics g) {
        world.render(g);
        if (paused) {
            Color tint = new Color(96, 96, 96, 127);
            g.setColor(tint);
            g.fillRect(0, 0, handler.getWidth(), handler.getHeight());
            uiManager.render(g);
        }
    }
}
