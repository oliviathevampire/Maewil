package coffeecatteam.theultimatetile.state;

import coffeecatteam.theultimatetile.Handler;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.ui.UIButton;
import coffeecatteam.theultimatetile.gfx.ui.UIManager;
import coffeecatteam.theultimatetile.net.packet.Packet01Disconnect;
import coffeecatteam.theultimatetile.tiles.Tile;
import coffeecatteam.theultimatetile.utils.Logger;
import coffeecatteam.theultimatetile.worlds.World;

import java.awt.*;
import java.awt.event.KeyEvent;

public class StateGame extends State {

    private World world;

    private boolean paused = false;

    private UIManager uiManager;

    public StateGame(Handler handlerIn) {
        this(handlerIn, "/assets/worlds/starter/world1");
    }

    public StateGame(Handler handlerIn, String world) {
        super(handlerIn);
        uiManager = new UIManager(handler);
        reset(world);

        int btnWidth = 192;
        int btnHeight = 64;
        int yOffset = 150;
        uiManager.addObject(new UIButton(handler.getWidth() / 2 - btnWidth / 2, handler.getHeight() / 2 - btnHeight / 2 + btnHeight - 25, btnWidth, btnHeight, "Resume", () ->
                paused = false
        ));
        int w = btnWidth + 128;
        uiManager.addObject(new UIButton(handler.getWidth() / 2 - w / 2, handler.getHeight() / 2 - btnHeight / 2 + btnHeight - 100 + yOffset, w, btnHeight, "Main Menu", () -> {
            State.setState(handler.getGame().stateMenu);
            Packet01Disconnect packet = new Packet01Disconnect(handler.getGame().getEntityManager().getPlayer().getUsername());
            packet.writeData(handler.getGame().getClient());
        }));
        uiManager.addObject(new UIButton(handler.getWidth() / 2 - btnWidth / 2, handler.getHeight() / 2 - btnHeight / 2 + btnHeight - 25 + yOffset, btnWidth, btnHeight, "Quit", () -> {
            Logger.print("Exiting...");
            Packet01Disconnect packet = new Packet01Disconnect(handler.getGame().getEntityManager().getPlayer().getUsername());
            packet.writeData(handler.getGame().getClient());
            System.exit(0);
        }));
    }

    @Override
    public void init() {
        paused = false;
        handler.getMouseManager().setUiManager(uiManager);
        handler.getEntityManager().getPlayer().setX(handler.getWorld().getSpawnX() * Tile.TILE_WIDTH);
        handler.getEntityManager().getPlayer().setY(handler.getWorld().getSpawnY() * Tile.TILE_HEIGHT);
    }

    @Override
    public void tick() {
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE))
            paused = !paused && !handler.getEntityManager().getPlayer().getInventory().isActive();

        if (paused)
            handler.getMouseManager().setUiManager(uiManager);
        else
            handler.getMouseManager().setUiManager(null);

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

    public void setWorld(String path) {
        this.world = new World(handler, path);
        handler.setWorld(world);
        init();
    }

    public void reset(String world) {
        handler.getEntityManager().reset();
        handler.getEntityManager().getPlayer().reset();
        setWorld(world);
    }
}
