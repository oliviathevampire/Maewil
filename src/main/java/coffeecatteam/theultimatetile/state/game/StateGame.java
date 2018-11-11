package coffeecatteam.theultimatetile.state.game;

import coffeecatteam.theultimatetile.GameEngine;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.gfx.ui.UIButton;
import coffeecatteam.theultimatetile.jsonparsers.world.WorldJsonSaver;
import coffeecatteam.theultimatetile.manager.UIManager;
import coffeecatteam.theultimatetile.state.State;
import coffeecatteam.theultimatetile.state.StateOptions;
import coffeecatteam.theultimatetile.state.options.controls.Keybind;
import coffeecatteam.theultimatetile.tiles.Tile;
import coffeecatteam.theultimatetile.tiles.Tiles;
import coffeecatteam.utils.DiscordHandler;
import coffeecatteam.theultimatetile.world.World;

import java.awt.*;
import java.io.IOException;

public class StateGame extends State {

    private World world;
    private String worldName;

    private boolean paused = false;

    private UIManager uiManagerPaused;
    private UIManager uiManagerDead;

    public StateGame(GameEngine gameEngineIn) {
        this(gameEngineIn, "/assets/worlds/starter/world_01", null);
    }

    public StateGame(GameEngine gameEngineIn, String world, String worldName) {
        super(gameEngineIn);
        this.worldName = worldName;
        Tiles.init(gameEngine);

        uiManagerPaused = new UIManager(gameEngine);
        uiManagerDead = new UIManager(gameEngine);
        reset(world);
        init();

        int btnWidth = 192;
        int btnHeight = 64;
        int yOffset = 150;
        uiManagerPaused.addObject(new UIButton(gameEngine.getWidth() / 2 - btnWidth / 2, gameEngine.getHeight() / 2 - btnHeight / 2 + btnHeight - 25, btnWidth, btnHeight, "Resume", new ClickListener() {
            @Override
            public void onClick() {
                paused = false;
            }

            @Override
            public void tick() {
            }
        }));

        int w = btnWidth + 128;
        UIButton btnMainMenu = new UIButton(gameEngine.getWidth() / 2 - w / 2, gameEngine.getHeight() / 2 - btnHeight / 2 + btnHeight - 100 + yOffset, w, btnHeight, "Main Menu", new ClickListener() {
            @Override
            public void onClick() {
                State.setState(gameEngine.stateMenu);

                DiscordHandler.INSTANCE.updatePresence("Main Menu");
            }

            @Override
            public void tick() {
            }
        });
        UIButton btnQuit = new UIButton(gameEngine.getWidth() / 2 - btnWidth / 2, gameEngine.getHeight() / 2 - btnHeight / 2 + btnHeight - 25 + yOffset, btnWidth, btnHeight, "Quit", new ClickListener() {
            @Override
            public void onClick() {
                saveWorld();
                gameEngine.setRunning(false);
            }

            @Override
            public void tick() {
            }
        });

        uiManagerPaused.addObject(btnMainMenu);
        uiManagerPaused.addObject(btnQuit);
        uiManagerDead.addObject(btnMainMenu);
        uiManagerDead.addObject(btnQuit);

//        saveWorldInfo();
    }

    @Override
    public void init() {
        paused = false;
        gameEngine.getMouseManager().setUiManager(uiManagerPaused);
        gameEngine.getEntityManager().getPlayer().setX(world.getSpawnX() * Tile.TILE_WIDTH);
        gameEngine.getEntityManager().getPlayer().setY(world.getSpawnY() * Tile.TILE_HEIGHT);
    }

    @Override
    public void tick() {
        if (gameEngine.getKeyManager().keyJustPressed(StateOptions.OPTIONS.controls().get(Keybind.ESCAPE).getKeyCode()) && !gameEngine.getEntityManager().getPlayer().isDead) {
            if (!gameEngine.getEntityManager().getPlayer().isGuiOpen())
                paused = !paused && !gameEngine.getEntityManager().getPlayer().getInventoryPlayer().isActive();
            saveWorld();
        }

        if (paused)
            gameEngine.getMouseManager().setUiManager(uiManagerPaused);

        if (gameEngine.getEntityManager().getPlayer().isDead) {
            gameEngine.getMouseManager().setUiManager(uiManagerDead);
            uiManagerDead.tick();
        }

        if (!paused && !gameEngine.getEntityManager().getPlayer().isDead)
            gameEngine.getMouseManager().setUiManager(null);

        if (!paused)
            world.tick();
        else
            uiManagerPaused.tick();
    }

    @Override
    public void render(Graphics g) {
        world.render(g);

        if (gameEngine.getEntityManager().getPlayer().isDead) {
            Color tint = new Color(96, 96, 96, 127);
            g.setColor(tint);
            g.fillRect(0, 0, gameEngine.getWidth(), gameEngine.getHeight());
            g.drawImage(Assets.DEAD_OVERLAY, 0, 0, gameEngine.getWidth(), gameEngine.getHeight(), null);

            uiManagerDead.render(g);
        } else {
            if (paused) {
                Color tint = new Color(96, 96, 96, 127);
                g.setColor(tint);
                g.fillRect(0, 0, gameEngine.getWidth(), gameEngine.getHeight());

                int w = 80 * 6;
                int h = 48 * 6;
                g.drawImage(Assets.TITLE, gameEngine.getWidth() / 2 - w / 2, 30, w, h, null);

                uiManagerPaused.render(g);
            }
        }
    }

    public void saveWorld() {
        WorldJsonSaver saver = new WorldJsonSaver("./saves/" + worldName, world, gameEngine);
        try {
            saver.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setWorld(String path) {
        this.world = new World(gameEngine, path, worldName);
        gameEngine.setWorld(world);
        init();
    }

    public void reset(String world) {
        gameEngine.getEntityManager().reset();
        gameEngine.getEntityManager().getPlayer().reset();
        gameEngine.getItemManager().reset();
        setWorld(world);
    }
}
