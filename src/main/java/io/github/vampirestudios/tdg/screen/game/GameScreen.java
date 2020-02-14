package io.github.vampirestudios.tdg.screen.game;

import io.github.vampirestudios.tdg.gfx.assets.Assets;
import io.github.vampirestudios.tdg.gfx.ui.ClickListener;
import io.github.vampirestudios.tdg.gfx.ui.button.WidgetButton;
import io.github.vampirestudios.tdg.jsonparsers.world.WorldJsonSaver;
import io.github.vampirestudios.tdg.manager.UIManager;
import io.github.vampirestudios.tdg.objs.tiles.Tile;
import io.github.vampirestudios.tdg.screen.Screen;
import io.github.vampirestudios.tdg.screen.ScreenManager;
import io.github.vampirestudios.tdg.screen.options.OptionsScreen;
import io.github.vampirestudios.tdg.screen.options.controls.Keybind;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.start.MaewilLauncher;
import io.github.vampirestudios.tdg.utils.DiscordHandler;
import io.github.vampirestudios.tdg.world.World;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;

public class GameScreen extends Screen {

    private World world;
    private String worldName;

    private boolean paused = false;

    private UIManager uiManagerPaused;
    private UIManager uiManagerDead;

    public GameScreen(MaewilEngine maewilEngine, String worldPath, String worldName) {
        this(maewilEngine, worldPath, worldName, null);
    }

    public GameScreen(MaewilEngine maewilEngine, String worldPath, String worldName, World newWorld) {
        super(maewilEngine);
        this.worldName = worldName;

        uiManagerPaused = new UIManager();
        uiManagerDead = new UIManager();
        setWorld(worldPath, newWorld);
        init();

        int yOffset = 30;
//        uiManagerPaused.addObject(new WidgetTitleRender());
        uiManagerPaused.addObject(new WidgetButton(maewilEngine, true, MaewilLauncher.HEIGHT / 2 - yOffset, "Resume", new ClickListener() {
            @Override
            public void onClick() {
                paused = false;
            }

            @Override
            public void update(GameContainer container, StateBasedGame game, int delta) {
            }
        }));

        WidgetButton btnSettings = new WidgetButton(maewilEngine, true, MaewilLauncher.HEIGHT / 2 - yOffset + 30, "Options", new ClickListener() {
            @Override
            public void onClick() {
                ScreenManager.setCurrentScreen(maewilEngine.stateOptions);

                DiscordHandler.INSTANCE.updatePresence("Main Menu", "Options");
            }

            @Override
            public void update(GameContainer container, StateBasedGame game, int delta) {
            }
        });

        WidgetButton btnMainMenu = new WidgetButton(maewilEngine, true, MaewilLauncher.HEIGHT / 2 + yOffset + 20, "Main Menu", new ClickListener() {
            @Override
            public void onClick() {
                DiscordHandler.INSTANCE.updatePresence("Main Menu");
                saveWorld(true);

                ScreenManager.setCurrentScreen(maewilEngine.stateMenu);
            }

            @Override
            public void update(GameContainer container, StateBasedGame game, int delta) {
            }
        });
        WidgetButton btnQuit = new WidgetButton(maewilEngine, true, MaewilLauncher.HEIGHT / 2 + yOffset * 3 + 30, "Quit", new ClickListener() {
            @Override
            public void onClick() {
                saveWorld(true);
                maewilEngine.close();
            }

            @Override
            public void update(GameContainer container, StateBasedGame game, int delta) {
            }
        });

        uiManagerPaused.addObject(btnMainMenu);
        uiManagerPaused.addObject(btnSettings);
        uiManagerPaused.addObject(btnQuit);
        uiManagerDead.addObject(btnMainMenu);
        uiManagerDead.addObject(btnQuit);
    }

    @Override
    public void init() {
        paused = false;
        maewilEngine.getPlayer().setX(world.getSpawnX() * Tile.TILE_SIZE);
        maewilEngine.getPlayer().setY(world.getSpawnY() * Tile.TILE_SIZE);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {
        if (maewilEngine.getKeyManager().keyJustPressed(OptionsScreen.OPTIONS.controls().get(Keybind.ESCAPE).getKeyCode()) && !maewilEngine.getPlayer().isDead) {
            if (!maewilEngine.getPlayer().isGuiOpen())
                paused = !paused && !maewilEngine.getPlayer().getInventoryPlayer().isActive();
            saveWorld(false);
        }

        if (!paused)
            world.update(container, game, delta);
        else
            uiManagerPaused.update(container, game, delta);

        if(paused)
            saveWorld(maewilEngine.getUsername());
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        world.render(container, game, g);

        if (maewilEngine.getPlayer().isDead) {
            Color tint = new Color(96, 96, 96, 127);
            g.setColor(tint);
            g.fillRect(0, 0, MaewilLauncher.WIDTH, MaewilLauncher.HEIGHT);
            Assets.GUI_DEAD_OVERLAY.draw(0, 0, MaewilLauncher.WIDTH, MaewilLauncher.HEIGHT);

            uiManagerDead.render(container, game, g);
        } else {
            if (paused) {
                Color tint = new Color(96, 96, 96, 127);
                g.setColor(tint);
                g.fillRect(0, 0, MaewilLauncher.WIDTH, MaewilLauncher.HEIGHT);

                uiManagerPaused.render(container, game, g);
            }
        }
    }

    public void saveWorld(String username) {
        WorldJsonSaver saver = new WorldJsonSaver("./data/saves/" + worldName, world, maewilEngine);
        try {
            saver.save(username);
        } catch (IOException e) {
            logger.error(e);
        }
        maewilEngine.setUsername(username);
    }

    public void saveWorld(boolean stopWorldFU) {
        if (stopWorldFU)
            world.stopUpdateThreads();

        WorldJsonSaver saver = new WorldJsonSaver("./data/saves/" + worldName, world, maewilEngine);
        try {
            saver.save();
        } catch (IOException e) {
            logger.error(e);
        }
    }

    private void setWorld(World world) {
        this.world = world;
        maewilEngine.setWorld(world);
        init();
    }

    private void setWorld(String worldPath, World newWorld) {
        if (newWorld == null)
            setWorld(new World(maewilEngine, worldPath, worldName));
        else
            setWorld(newWorld);
    }
}
