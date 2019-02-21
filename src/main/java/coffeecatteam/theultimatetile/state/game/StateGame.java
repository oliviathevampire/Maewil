package coffeecatteam.theultimatetile.state.game;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.state.State;
import coffeecatteam.theultimatetile.state.StateManager;
import coffeecatteam.theultimatetile.state.StateOptions;
import coffeecatteam.theultimatetile.state.options.controls.Keybind;
import coffeecatteam.theultimatetile.objs.tiles.Tile;
import coffeecatteam.theultimatetile.world.World;
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

    public StateGame(TutEngine tutEngine, String world, String worldName) {
        this(tutEngine, world, worldName, null);
    }

    public StateGame(TutEngine tutEngine, String worldS, String worldName, World wWorld) {
        super(tutEngine);
        this.worldName = worldName;

        uiManagerPaused = new UIManager(tutEngine);
        uiManagerDead = new UIManager(tutEngine);
        reset(worldS, wWorld);
        init();

        int yOffset = 30;
        uiManagerPaused.addObject(new UIButton(tutEngine, true, tutEngine.getHeight() / 2 - yOffset, "Resume", new ClickListener() {
            @Override
            public void onClick() {
                paused = false;
            }

            @Override
            public void update(GameContainer container, int delta) {
            }
        }));

        UIButton btnMainMenu = new UIButton(tutEngine, true, tutEngine.getHeight() / 2 + yOffset + 10, "Main Menu", new ClickListener() {
            @Override
            public void onClick() {
                DiscordHandler.INSTANCE.updatePresence("Main Menu");
                saveWorld(true);

                StateManager.setCurrentState(tutEngine.stateMenu);
            }

            @Override
            public void update(GameContainer container, int delta) {
            }
        });
        UIButton btnQuit = new UIButton(tutEngine, true, tutEngine.getHeight() / 2 + yOffset * 3 + 20, "Quit", new ClickListener() {
            @Override
            public void onClick() {
                saveWorld(true);
                tutEngine.close();
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
        tutEngine.getEntityManager().getPlayer().setX(world.getSpawnX() * Tile.TILE_WIDTH);
        tutEngine.getEntityManager().getPlayer().setY(world.getSpawnY() * Tile.TILE_HEIGHT);
    }

    @Override
    public void update(GameContainer container, int delta) {
        if (tutEngine.getKeyManager().keyJustPressed(StateOptions.OPTIONS.controls().get(Keybind.ESCAPE).getKeyCode()) && !tutEngine.getEntityManager().getPlayer().isDead) {
            if (!tutEngine.getEntityManager().getPlayer().isGuiOpen())
                paused = !paused && !tutEngine.getEntityManager().getPlayer().getInventoryPlayer().isActive();
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

        if (tutEngine.getEntityManager().getPlayer().isDead) {
            Color tint = new Color(96, 96, 96, 127);
            g.setColor(tint);
            g.fillRect(0, 0, tutEngine.getWidth(), tutEngine.getHeight());
            Assets.DEAD_OVERLAY.draw(0, 0, tutEngine.getWidth(), tutEngine.getHeight());

            uiManagerDead.render(g);
        } else {
            if (paused) {
                Color tint = new Color(96, 96, 96, 127);
                g.setColor(tint);
                g.fillRect(0, 0, tutEngine.getWidth(), tutEngine.getHeight());

                int w = 80 * 6;
                int h = 48 * 6;
                Assets.TITLE.draw(tutEngine.getWidth() / 2f - w / 2f, 30, w, h);

                uiManagerPaused.render(g);
            }
        }
    }

    public void saveWorld(String username) {
        WorldJsonSaver saver = new WorldJsonSaver("./saves/" + worldName, world, tutEngine);
        try {
            saver.save(username);
        } catch (IOException e) {
            logger.print(e);
        }
        tutEngine.setUsername(username);
    }

    public void saveWorld(boolean stopWorldFU) {
        if (stopWorldFU)
            world.stopForcedUpdateThread();

        WorldJsonSaver saver = new WorldJsonSaver("./saves/" + worldName, world, tutEngine);
        try {
            saver.save();
        } catch (IOException e) {
            logger.print(e);
        }
    }

    public void setWorld(World world) {
        this.world = world;
        tutEngine.setWorld(world);
        init();
    }

    public void setWorld(String path) {
        setWorld(new World(tutEngine, path, worldName));
    }

    public void reset(String world, World wWorld) {
        tutEngine.getEntityManager().reset();
        tutEngine.getEntityManager().getPlayer().reset();
        tutEngine.getItems().reset();
        if (wWorld == null)
            setWorld(world);
        else
            setWorld(wWorld);
    }
}
