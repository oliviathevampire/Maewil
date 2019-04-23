package coffeecatteam.theultimatetile.state.game;

import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.gfx.ui.UITitleRender;
import coffeecatteam.theultimatetile.gfx.ui.button.UIButton;
import coffeecatteam.theultimatetile.jsonparsers.world.WorldJsonSaver;
import coffeecatteam.theultimatetile.manager.UIManager;
import coffeecatteam.theultimatetile.objs.tiles.Tile;
import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.start.TutLauncher;
import coffeecatteam.theultimatetile.state.State;
import coffeecatteam.theultimatetile.state.StateManager;
import coffeecatteam.theultimatetile.state.options.StateOptions;
import coffeecatteam.theultimatetile.state.options.controls.Keybind;
import coffeecatteam.theultimatetile.utils.DiscordHandler;
import coffeecatteam.theultimatetile.world.World;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;

public class StateGame extends State {

    private World world;
    private String worldName;

    private boolean paused = false;

    private UIManager uiManagerPaused;
    private UIManager uiManagerDead;

    public StateGame(TutEngine tutEngine, String worldPath, String worldName) {
        this(tutEngine, worldPath, worldName, null);
    }

    public StateGame(TutEngine tutEngine, String worldPath, String worldName, World newWorld) {
        super(tutEngine);
        this.worldName = worldName;

        uiManagerPaused = new UIManager();
        uiManagerDead = new UIManager();
        setWorld(worldPath, newWorld);
        init();

        int yOffset = 30;
        uiManagerPaused.addObject(new UITitleRender());
        uiManagerPaused.addObject(new UIButton(tutEngine, true, TutLauncher.HEIGHT / 2 - yOffset, "Resume", new ClickListener() {
            @Override
            public void onClick() {
                paused = false;
            }

            @Override
            public void update(GameContainer container, StateBasedGame game, int delta) {
            }
        }));

        UIButton btnMainMenu = new UIButton(tutEngine, true, TutLauncher.HEIGHT / 2 + yOffset + 10, "Main Menu", new ClickListener() {
            @Override
            public void onClick() {
                DiscordHandler.INSTANCE.updatePresence("Main Menu");
                saveWorld(true);

                StateManager.setCurrentState(tutEngine.stateMenu);
            }

            @Override
            public void update(GameContainer container, StateBasedGame game, int delta) {
            }
        });
        UIButton btnQuit = new UIButton(tutEngine, true, TutLauncher.HEIGHT / 2 + yOffset * 3 + 20, "Quit", new ClickListener() {
            @Override
            public void onClick() {
                saveWorld(true);
                tutEngine.close();
            }

            @Override
            public void update(GameContainer container, StateBasedGame game, int delta) {
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
        tutEngine.getPlayer().setX(world.getSpawnX() * Tile.TILE_SIZE);
        tutEngine.getPlayer().setY(world.getSpawnY() * Tile.TILE_SIZE);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {
        if (tutEngine.getKeyManager().keyJustPressed(StateOptions.OPTIONS.controls().get(Keybind.ESCAPE).getKeyCode()) && !tutEngine.getPlayer().isDead) {
            if (!tutEngine.getPlayer().isGuiOpen())
                paused = !paused && !tutEngine.getPlayer().getInventoryPlayer().isActive();
            saveWorld(false);
        }

        if (!paused)
            world.update(container, game, delta);
        else
            uiManagerPaused.update(container, game, delta);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        world.render(container, game, g);

        if (tutEngine.getPlayer().isDead) {
            Color tint = new Color(96, 96, 96, 127);
            g.setColor(tint);
            g.fillRect(0, 0, TutLauncher.WIDTH, TutLauncher.HEIGHT);
            Assets.GUI_DEAD_OVERLAY.draw(0, 0, TutLauncher.WIDTH, TutLauncher.HEIGHT);

            uiManagerDead.render(container, game, g);
        } else {
            if (paused) {
                Color tint = new Color(96, 96, 96, 127);
                g.setColor(tint);
                g.fillRect(0, 0, TutLauncher.WIDTH, TutLauncher.HEIGHT);

                uiManagerPaused.render(container, game, g);
            }
        }
    }

    public void saveWorld(String username) {
        WorldJsonSaver saver = new WorldJsonSaver("./data/saves/" + worldName, world, tutEngine);
        try {
            saver.save(username);
        } catch (IOException e) {
            logger.error(e);
        }
        tutEngine.setUsername(username);
    }

    public void saveWorld(boolean stopWorldFU) {
        if (stopWorldFU)
            world.stopUpdateThreads();

        WorldJsonSaver saver = new WorldJsonSaver("./data/saves/" + worldName, world, tutEngine);
        try {
            saver.save();
        } catch (IOException e) {
            logger.error(e);
        }
    }

    private void setWorld(World world) {
        this.world = world;
        tutEngine.setWorld(world);
        init();
    }

    public void setWorld(String worldPath, World newWorld) {
        if (newWorld == null)
            setWorld(new World(tutEngine, worldPath, worldName));
        else
            setWorld(newWorld);
    }
}
