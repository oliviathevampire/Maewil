package coffeecatteam.theultimatetile.game.state.game;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.state.State;
import coffeecatteam.theultimatetile.game.state.StateManager;
import coffeecatteam.theultimatetile.game.state.StateOptions;
import coffeecatteam.theultimatetile.game.state.options.controls.Keybind;
import coffeecatteam.theultimatetile.game.tile.Tile;
import coffeecatteam.theultimatetile.game.world.World;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.gfx.ui.button.UIButton;
import coffeecatteam.theultimatetile.jsonparsers.world.WorldJsonSaver;
import coffeecatteam.theultimatetile.manager.UIManager;
import coffeecatteam.theultimatetile.utils.DiscordHandler;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.io.IOException;

public class StateGame extends State {

    private World world;
    private String worldName;

    private boolean paused = false;

    private UIManager uiManagerPaused;
    private UIManager uiManagerDead;

    public StateGame(Engine engine, String world, String worldName) {
        this(engine, world, worldName, null);
    }

    public StateGame(Engine engine, String worldS, String worldName, World wWorld) {
        super(engine);
        this.worldName = worldName;

        uiManagerPaused = new UIManager(engine);
        uiManagerDead = new UIManager(engine);
        reset(worldS, wWorld);
        init();

        int yOffset = 30;
        uiManagerPaused.addObject(new UIButton(engine, true, engine.getHeight() / 2 - yOffset, "Resume", new ClickListener() {
            @Override
            public void onClick() {
                paused = false;
            }

            @Override
            public void update(GameContainer container, int delta) {
            }
        }));

        UIButton btnMainMenu = new UIButton(engine, true, engine.getHeight() / 2 + yOffset + 10, "Main Menu", new ClickListener() {
            @Override
            public void onClick() {
                StateManager.setCurrentState(GameEngine.getGameEngine().stateMenu);

                DiscordHandler.INSTANCE.updatePresence("Main Menu");
                saveWorld(true);
            }

            @Override
            public void update(GameContainer container, int delta) {
            }
        });
        UIButton btnQuit = new UIButton(engine, true, engine.getHeight() / 2 + yOffset * 3 + 20, "Quit", new ClickListener() {
            @Override
            public void onClick() {
                saveWorld(true);
                engine.close();
            }

            @Override
            public void update(GameContainer container, int delta) {
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
        GameEngine.getGameEngine().getEntityManager().getPlayer().setX(world.getSpawnX() * Tile.TILE_WIDTH);
        GameEngine.getGameEngine().getEntityManager().getPlayer().setY(world.getSpawnY() * Tile.TILE_HEIGHT);
    }

    @Override
    public void update(GameContainer container, int delta) {
        if (engine.getKeyManager().keyJustPressed(StateOptions.OPTIONS.controls().get(Keybind.ESCAPE).getKeyCode()) && !GameEngine.getGameEngine().getEntityManager().getPlayer().isDead) {
            if (!GameEngine.getGameEngine().getEntityManager().getPlayer().isGuiOpen())
                paused = !paused && !GameEngine.getGameEngine().getEntityManager().getPlayer().getInventoryPlayer().isActive();
            saveWorld(false);
        }

        if (!paused)
            world.update(container, delta);
        else
            uiManagerPaused.update(container, delta);
    }

    @Override
    public void render(Graphics g) {
        world.render(g);

        if (GameEngine.getGameEngine().getEntityManager().getPlayer().isDead) {
            Color tint = new Color(96, 96, 96, 127);
            g.setColor(tint);
            g.fillRect(0, 0, engine.getWidth(), engine.getHeight());
            Assets.DEAD_OVERLAY.draw(0, 0, engine.getWidth(), engine.getHeight());

            uiManagerDead.render(g);
        } else {
            if (paused) {
                Color tint = new Color(96, 96, 96, 127);
                g.setColor(tint);
                g.fillRect(0, 0, engine.getWidth(), engine.getHeight());

                int w = 80 * 6;
                int h = 48 * 6;
                Assets.TITLE.draw(engine.getWidth() / 2f - w / 2f, 30, w, h);

                uiManagerPaused.render(g);
            }
        }
    }

    public void saveWorld(String username) {
        WorldJsonSaver saver = new WorldJsonSaver("./saves/" + worldName, world, engine);
        try {
            saver.save(username);
        } catch (IOException e) {
            logger.print(e);
        }
        GameEngine.getGameEngine().setUsername(username);
    }

    public void saveWorld(boolean stopWorldFU) {
        if (stopWorldFU)
            world.stopForcedUpdateThread();

        WorldJsonSaver saver = new WorldJsonSaver("./saves/" + worldName, world, engine);
        try {
            saver.save();
        } catch (IOException e) {
            logger.print(e);
        }
    }

    public void setWorld(World world) {
        this.world = world;
        GameEngine.getGameEngine().setWorld(world);
        init();
    }

    public void setWorld(String path) {
        setWorld(new World(engine, path, worldName));
    }

    public void reset(String world, World wWorld) {
        GameEngine.getGameEngine().getEntityManager().reset();
        GameEngine.getGameEngine().getEntityManager().getPlayer().reset();
        GameEngine.getGameEngine().getItemManager().reset();
        if (wWorld == null)
            setWorld(world);
        else
            setWorld(wWorld);
    }
}
