package coffeecatteam.tilegame.state;

import coffeecatteam.tilegame.Handler;
import coffeecatteam.tilegame.gfx.Assets;
import coffeecatteam.tilegame.gfx.ui.UIButton;
import coffeecatteam.tilegame.gfx.ui.UIManager;
import coffeecatteam.tilegame.tiles.Tile;
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
        uiManager.addObject(new UIButton(handler.getWidth() / 2 - btnWidth / 2, handler.getHeight() / 2 - btnHeight / 2 + btnHeight - 25, btnWidth, btnHeight, "Resume", () ->
            paused = false
        ));
        int w = btnWidth + 128;
        uiManager.addObject(new UIButton(handler.getWidth() / 2 - w / 2, handler.getHeight() / 2 - btnHeight / 2 + btnHeight - 100 + yOffset, w, btnHeight, "Main Menu", () -> {
            handler.getMouseManager().setUiManager(null);
            State.setState(handler.getGame().menuState.init());
        }));
        uiManager.addObject(new UIButton(handler.getWidth() / 2 - btnWidth / 2, handler.getHeight() / 2 - btnHeight / 2 + btnHeight - 25 + yOffset, btnWidth, btnHeight, "Quit", () -> {
            System.out.println("Exiting...");
            System.exit(0);
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
            paused = !paused && !handler.getWorld().getEntityManager().getPlayer().getInventory().isActive();

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

            int w = 80 * 6;
            int h = 48 * 6;
            g.drawImage(Assets.TITLE, w / 6, 30, w, h, null);

            uiManager.render(g);
        }
    }
}
