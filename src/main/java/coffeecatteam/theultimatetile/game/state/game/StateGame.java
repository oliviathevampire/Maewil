package coffeecatteam.theultimatetile.game.state.game;

import coffeecatteam.coffeecatutils.Logger;
import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.state.State;
import coffeecatteam.theultimatetile.game.state.StateOptions;
import coffeecatteam.theultimatetile.game.state.options.controls.Keybind;
import coffeecatteam.theultimatetile.game.tiles.Tile;
import coffeecatteam.theultimatetile.game.tiles.Tiles;
import coffeecatteam.theultimatetile.game.world.World;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.gfx.ui.UIButton;
import coffeecatteam.theultimatetile.jsonparsers.world.WorldJsonSaver;
import coffeecatteam.theultimatetile.manager.UIManager;
import coffeecatteam.theultimatetile.utils.DiscordHandler;

import java.awt.*;
import java.io.IOException;

public class StateGame extends State {

    private World world;
    private String worldName;

    private boolean paused = false;

    private UIManager uiManagerPaused;
    private UIManager uiManagerDead;

    public StateGame(Engine engine) {
        this(engine, "/assets/worlds/starter/world_01", null);
    }

    public StateGame(Engine engine, String world, String worldName) {
        super(engine);
        this.worldName = worldName;
        Tiles.init(engine);

        uiManagerPaused = new UIManager(engine);
        uiManagerDead = new UIManager(engine);
        reset(world);
        init();

        int yOffset = 30;
        uiManagerPaused.addObject(new UIButton(engine, true, engine.getHeight() / 2 - yOffset, "Resume", new ClickListener() {
            @Override
            public void onClick() {
                paused = false;
            }

            @Override
            public void tick() {
            }
        }));

        UIButton btnMainMenu = new UIButton(engine, true, engine.getHeight() / 2 + yOffset + 10, "Main Menu", new ClickListener() {
            @Override
            public void onClick() {
                State.setState(((GameEngine) engine).stateMenu);

                DiscordHandler.INSTANCE.updatePresence("Main Menu");
                saveWorld();
            }

            @Override
            public void tick() {
            }
        });
        UIButton btnQuit = new UIButton(engine, true, engine.getHeight() / 2 + yOffset * 3 + 20, "Quit", new ClickListener() {
            @Override
            public void onClick() {
                saveWorld();
                engine.setRunning(false);
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
        engine.getMouseManager().setUiManager(uiManagerPaused);
        ((GameEngine) engine).getEntityManager().getPlayer().setX(world.getSpawnX() * Tile.TILE_WIDTH);
        ((GameEngine) engine).getEntityManager().getPlayer().setY(world.getSpawnY() * Tile.TILE_HEIGHT);
    }

    @Override
    public void tick() {
        if (engine.getKeyManager().keyJustPressed(StateOptions.OPTIONS.controls().get(Keybind.ESCAPE).getKeyCode()) && !((GameEngine) engine).getEntityManager().getPlayer().isDead) {
            if (!((GameEngine) engine).getEntityManager().getPlayer().isGuiOpen())
                paused = !paused && !((GameEngine) engine).getEntityManager().getPlayer().getInventoryPlayer().isActive();
            saveWorld();
        }

        if (paused)
            engine.getMouseManager().setUiManager(uiManagerPaused);

        if (((GameEngine) engine).getEntityManager().getPlayer().isDead) {
            engine.getMouseManager().setUiManager(uiManagerDead);
            uiManagerDead.tick();
        }

        if (!paused && !((GameEngine) engine).getEntityManager().getPlayer().isDead)
            engine.getMouseManager().setUiManager(null);

        if (!paused)
            world.tick();
        else
            uiManagerPaused.tick();
    }

    @Override
    public void render(Graphics2D g) {
        world.render(g);

        if (((GameEngine) engine).getEntityManager().getPlayer().isDead) {
            Color tint = new Color(96, 96, 96, 127);
            g.setColor(tint);
            g.fillRect(0, 0, engine.getWidth(), engine.getHeight());
            g.drawImage(Assets.DEAD_OVERLAY, 0, 0, engine.getWidth(), engine.getHeight(), null);

            uiManagerDead.render(g);
        } else {
            if (paused) {
                Color tint = new Color(96, 96, 96, 127);
                g.setColor(tint);
                g.fillRect(0, 0, engine.getWidth(), engine.getHeight());

                int w = 80 * 6;
                int h = 48 * 6;
                g.drawImage(Assets.TITLE, engine.getWidth() / 2 - w / 2, 30, w, h, null);

                uiManagerPaused.render(g);
            }
        }
    }

    public void saveWorld() {
        WorldJsonSaver saver = new WorldJsonSaver("./saves/" + worldName, world, engine);
        try {
            saver.save();
        } catch (IOException e) {
            Logger.print(e);
        }
    }

    public void setWorld(String path) {
        this.world = new World(engine, path, worldName);
        ((GameEngine) engine).setWorld(world);
        init();
    }

    public void reset(String world) {
        ((GameEngine) engine).getEntityManager().reset();
        ((GameEngine) engine).getEntityManager().getPlayer().reset();
        ((GameEngine) engine).getItemManager().reset();
        setWorld(world);
    }
}
