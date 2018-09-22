package coffeecatteam.theultimatetile.state;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.gfx.ui.UIButton;
import coffeecatteam.theultimatetile.manager.UIManager;
import coffeecatteam.theultimatetile.state.options.Keybind;
import coffeecatteam.theultimatetile.tiles.Tile;
import coffeecatteam.theultimatetile.tiles.Tiles;
import coffeecatteam.theultimatetile.utils.Logger;
import coffeecatteam.theultimatetile.world.World;

import java.awt.*;

public class StateGame extends State {

    private World world;

    private boolean paused = false;

    private UIManager uiManagerPaused;
    private UIManager uiManagerDead;

    public StateGame(TheUltimateTile theUltimateTileIn) {
        this(theUltimateTileIn, "/assets/worlds/starter/world_01");
    }

    public StateGame(TheUltimateTile theUltimateTileIn, String world) {
        super(theUltimateTileIn);
        Tiles.init(theUltimateTile);

        uiManagerPaused = new UIManager(theUltimateTile);
        uiManagerDead = new UIManager(theUltimateTile);
        reset(world);
        init();

        int btnWidth = 192;
        int btnHeight = 64;
        int yOffset = 150;
        uiManagerPaused.addObject(new UIButton(theUltimateTile.getWidth() / 2 - btnWidth / 2, theUltimateTile.getHeight() / 2 - btnHeight / 2 + btnHeight - 25, btnWidth, btnHeight, "Resume", new ClickListener() {
            @Override
            public void onClick() {
                paused = false;
            }

            @Override
            public void tick() {
            }
        }));

        int w = btnWidth + 128;
        UIButton btnMainMenu = new UIButton(theUltimateTile.getWidth() / 2 - w / 2, theUltimateTile.getHeight() / 2 - btnHeight / 2 + btnHeight - 100 + yOffset, w, btnHeight, "Main Menu", new ClickListener() {
            @Override
            public void onClick() {
                State.setState(theUltimateTile.stateMenu);
            }

            @Override
            public void tick() {
            }
        });
        UIButton btnQuit = new UIButton(theUltimateTile.getWidth() / 2 - btnWidth / 2, theUltimateTile.getHeight() / 2 - btnHeight / 2 + btnHeight - 25 + yOffset, btnWidth, btnHeight, "Quit", new ClickListener() {
            @Override
            public void onClick() {
                Logger.print("Exiting...");
                System.exit(0);
            }

            @Override
            public void tick() {
            }
        });

        uiManagerPaused.addObject(btnMainMenu);
        uiManagerPaused.addObject(btnQuit);
        uiManagerDead.addObject(btnMainMenu);
        uiManagerDead.addObject(btnQuit);
    }

    @Override
    public void init() {
        paused = false;
        theUltimateTile.getMouseManager().setUiManager(uiManagerPaused);
        theUltimateTile.getEntityManager().getPlayer().setX(world.getSpawnX() * Tile.TILE_WIDTH);
        theUltimateTile.getEntityManager().getPlayer().setY(world.getSpawnY() * Tile.TILE_HEIGHT);
    }

    @Override
    public void tick() {
        if (theUltimateTile.getKeyManager().keyJustPressed(StateOptions.options.CONTROLS().get(Keybind.ESCAPE).getKeyCode()) && !theUltimateTile.getEntityManager().getPlayer().isDead)
            paused = !paused && !theUltimateTile.getEntityManager().getPlayer().getInventoryPlayer().isActive();

        if (paused)
            theUltimateTile.getMouseManager().setUiManager(uiManagerPaused);

        if (theUltimateTile.getEntityManager().getPlayer().isDead) {
            theUltimateTile.getMouseManager().setUiManager(uiManagerDead);
            uiManagerDead.tick();
        }

        if (!paused && !theUltimateTile.getEntityManager().getPlayer().isDead)
            theUltimateTile.getMouseManager().setUiManager(null);

        if (!paused)
            world.tick();
        else
            uiManagerPaused.tick();
    }

    @Override
    public void render(Graphics g) {
        world.render(g);

        if (theUltimateTile.getEntityManager().getPlayer().isDead) {
            Color tint = new Color(96, 96, 96, 127);
            g.setColor(tint);
            g.fillRect(0, 0, theUltimateTile.getWidth(), theUltimateTile.getHeight());
            g.drawImage(Assets.DEAD_OVERLAY, 0, 0, theUltimateTile.getWidth(), theUltimateTile.getHeight(), null);

            uiManagerDead.render(g);
        } else {
            if (paused) {
                Color tint = new Color(96, 96, 96, 127);
                g.setColor(tint);
                g.fillRect(0, 0, theUltimateTile.getWidth(), theUltimateTile.getHeight());

                int w = 80 * 6;
                int h = 48 * 6;
                g.drawImage(Assets.TITLE, w / 6, 30, w, h, null);

                uiManagerPaused.render(g);
            }
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
        theUltimateTile.getItemManager().reset();
        setWorld(world);
    }
}
