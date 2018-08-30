package coffeecatteam.theultimatetile.state;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.ui.UIButton;
import coffeecatteam.theultimatetile.gfx.ui.UIManager;
import coffeecatteam.theultimatetile.net.packet.Packet01Disconnect;
import coffeecatteam.theultimatetile.tiles.Tile;
import coffeecatteam.theultimatetile.utils.Logger;
import coffeecatteam.theultimatetile.worlds.World;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

public class StateGame extends State {

    private World world;

    private boolean paused = false;

    private UIManager uiManager;

    public StateGame(TheUltimateTile theUltimateTileIn) {
        this(theUltimateTileIn, "/assets/worlds/starter/world1");
    }

    public StateGame(TheUltimateTile theUltimateTileIn, String world) {
        super(theUltimateTileIn);
        uiManager = new UIManager(theUltimateTile);
        reset(world);

        int btnWidth = 192;
        int btnHeight = 64;
        int yOffset = 150;
        uiManager.addObject(new UIButton(theUltimateTile.getWidth() / 2 - btnWidth / 2, theUltimateTile.getHeight() / 2 - btnHeight / 2 + btnHeight - 25, btnWidth, btnHeight, "Resume", () ->
                paused = false
        ));
        int w = btnWidth + 128;
        uiManager.addObject(new UIButton(theUltimateTile.getWidth() / 2 - w / 2, theUltimateTile.getHeight() / 2 - btnHeight / 2 + btnHeight - 100 + yOffset, w, btnHeight, "Main Menu", () -> {
            State.setState(theUltimateTile.stateMenu);
            theUltimateTile.disconnect();
        }));
        uiManager.addObject(new UIButton(theUltimateTile.getWidth() / 2 - btnWidth / 2, theUltimateTile.getHeight() / 2 - btnHeight / 2 + btnHeight - 25 + yOffset, btnWidth, btnHeight, "Quit", () -> {
            theUltimateTile.disconnect();
            Logger.print("Exiting...");
            System.exit(0);
        }));
    }

    @Override
    public void init() {
        paused = false;
        theUltimateTile.getMouseManager().setUiManager(uiManager);
        theUltimateTile.getEntityManager().getPlayer().setX(world.getSpawnX() * Tile.TILE_WIDTH);
        theUltimateTile.getEntityManager().getPlayer().setY(world.getSpawnY() * Tile.TILE_HEIGHT);
    }

    @Override
    public void tick() {
        if (theUltimateTile.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE))
            paused = !paused && !theUltimateTile.getEntityManager().getPlayer().getInventory().isActive();

        if (paused)
            theUltimateTile.getMouseManager().setUiManager(uiManager);
        else
            theUltimateTile.getMouseManager().setUiManager(null);

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
            g.fillRect(0, 0, theUltimateTile.getWidth(), theUltimateTile.getHeight());

            int w = 80 * 6;
            int h = 48 * 6;
            g.drawImage(Assets.TITLE, w / 6, 30, w, h, null);

            uiManager.render(g);
        }
    }

    public void setWorld(String path) {
        this.world = new World(theUltimateTile, path);
        theUltimateTile.setWorld(world);
        init();
    }

    public void reset(String world) {
        theUltimateTile.getEntityManager().reset();
        theUltimateTile.getEntityManager().getPlayer().reset();
        setWorld(world);
    }
}
