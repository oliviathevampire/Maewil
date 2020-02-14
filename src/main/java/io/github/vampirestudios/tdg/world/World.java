package io.github.vampirestudios.tdg.world;

import coffeecatteam.coffeecatutils.logger.CatLogger;
import coffeecatteam.coffeecatutils.position.Vector2D;
import io.github.vampirestudios.tdg.jsonparsers.world.WorldJsonLoader;
import io.github.vampirestudios.tdg.manager.OverlayManager;
import io.github.vampirestudios.tdg.objs.items.Items;
import io.github.vampirestudios.tdg.objs.tiles.Tile;
import io.github.vampirestudios.tdg.objs.tiles.TilePos;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.start.MaewilLauncher;
import org.json.simple.parser.ParseException;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;

public class World {

    private MaewilEngine maewilEngine;
    private String worldName;
    private int worldWidth, worldHeight;
    private float spawnX;
    private float spawnY;

    private TileList bgTiles, fgTiles;

    private OverlayManager overlayManager;

    private boolean isForcedUpdateThread = false;
    private Thread forcedUpdateThread;
    private GameContainer container;
    private int delta;

    public World(MaewilEngine maewilEngine, String path, String worldName) {
        this.maewilEngine = maewilEngine;
        this.worldName = worldName;
        overlayManager = new OverlayManager(maewilEngine, maewilEngine.getPlayer());

        try {
            loadWorld(path);
        } catch (IOException | ParseException e) {
            MaewilLauncher.LOGGER.error(e);
        }
        maewilEngine.getPlayer().setX(spawnX * Tile.TILE_SIZE);
        maewilEngine.getPlayer().setY(spawnY * Tile.TILE_SIZE);
    }

    public World(MaewilEngine maewilEngine, String worldName, int worldWidth, int worldHeight, Vector2D spawn, TileList bgTiles, TileList fgTiles) {
        this(maewilEngine, worldName, worldWidth, worldHeight, (int) spawn.x, (int) spawn.y, bgTiles, fgTiles);
    }

    public World(MaewilEngine maewilEngine, String worldName, int worldWidth, int worldHeight, int spawnX, int spawnY, TileList bgTiles, TileList fgTiles) {
        this.maewilEngine = maewilEngine;
        this.worldName = worldName;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.spawnX = spawnX;
        this.spawnY = spawnY;
        this.bgTiles = bgTiles;
        this.fgTiles = fgTiles;

        overlayManager = new OverlayManager(maewilEngine, maewilEngine.getPlayer());
    }

    public void update(GameContainer container, StateBasedGame game, int delta) {
        int xStart = (int) Math.max(0, maewilEngine.getCamera().getXOffset() / Tile.TILE_SIZE);
        int xEnd = (int) Math.min(worldWidth, (maewilEngine.getCamera().getXOffset() + MaewilLauncher.WIDTH) / Tile.TILE_SIZE + 1);
        int yStart = (int) Math.max(0, maewilEngine.getCamera().getYOffset() / Tile.TILE_SIZE);
        int yEnd = (int) Math.min(worldHeight, (maewilEngine.getCamera().getYOffset() + MaewilLauncher.HEIGHT) / Tile.TILE_SIZE + 1);

        for (int y = yStart; y < yEnd; y++) {
            for (int x = xStart; x < xEnd; x++) {
                getBackgroundTile(x, y).updateBounds();
                getBackgroundTile(x, y).setWorldLayer(bgTiles);

                getForegroundTile(x, y).update(container, game, delta);
                getForegroundTile(x, y).setWorldLayer(fgTiles);
            }
        }

        this.container = container;
        this.delta = delta;
        updateThreadsInit();

        Items.UPDATABLE_ITEMS.forEach(i -> i.update(container, game, delta));

        maewilEngine.getEntityManager().update(container, game, delta);
        overlayManager.update(container, game, delta);

        maewilEngine.getCamera().centerOnEntity(maewilEngine.getEntityManager().getPlayer());
    }

    private void updateThreadsInit() {
        if (!isForcedUpdateThread) {
            isForcedUpdateThread = true;

            forcedUpdateThread = new Thread(() -> {
                CatLogger logger = new CatLogger();
                logger.info("Forced update thread initialized");

                while (isForcedUpdateThread) {
                    for (int y = 0; y < worldHeight; y++) {
                        for (int x = 0; x < worldWidth; x++) {
                            getBackgroundTile(x, y).forcedUpdate(container, delta);
                            getForegroundTile(x, y).forcedUpdate(container, delta);
                        }
                    }
                }

                logger.warn("Stopping force update thread...");
                try {
                    forcedUpdateThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "WorldForcedUpdate-Thread");

            forcedUpdateThread.start();
        }
    }

    public void render(GameContainer container, StateBasedGame game, Graphics graphics) {
        int xStart = (int) Math.max(0, maewilEngine.getCamera().getXOffset() / Tile.TILE_SIZE);
        int xEnd = (int) Math.min(worldWidth, (maewilEngine.getCamera().getXOffset() + MaewilLauncher.WIDTH) / Tile.TILE_SIZE + 1);
        int yStart = (int) Math.max(0, maewilEngine.getCamera().getYOffset() / Tile.TILE_SIZE);
        int yEnd = (int) Math.min(worldHeight, (maewilEngine.getCamera().getYOffset() + MaewilLauncher.HEIGHT) / Tile.TILE_SIZE + 1);

        for (int y = yStart; y < yEnd; y++)
            for (int x = xStart; x < xEnd; x++)
                getBackgroundTile(x, y).render(container, game, graphics);

        Color tint = new Color(63, 63, 63, 128);
        graphics.setColor(tint);
        graphics.fillRect(0, 0, MaewilLauncher.WIDTH, MaewilLauncher.HEIGHT);

        for (int y = yStart; y < yEnd; y++)
            for (int x = xStart; x < xEnd; x++)
                getForegroundTile(x, y).render(container, game, graphics);

        maewilEngine.getEntityManager().render(container, game, graphics);
        overlayManager.render(container, game, graphics);

        /*
         * Mini Map
         */
        WorldMiniMap.render(graphics, maewilEngine, worldWidth, worldHeight, tint);
    }

    public Tile getBackgroundTile(int x, int y) {
        return getBackgroundTile(new TilePos(x, y));
    }

    private Tile getBackgroundTile(TilePos pos) {
        checkTilePos(pos, false);

        return bgTiles.getTileAtPos(pos);
    }

    public Tile getForegroundTile(int x, int y) {
        return getForegroundTile(new TilePos(x, y));
    }

    public Tile getForegroundTile(TilePos pos) {
        checkTilePos(pos, false);

        return fgTiles.getTileAtPos(pos);
    }

    public void setFGTile(int x, int y, Tile tile) {
        setFGTile(new TilePos(x, y), tile);
    }

    private void setFGTile(TilePos pos, Tile tile) {
        checkTilePos(pos, true);

        fgTiles.setTileAtPos(pos, tile.pos(pos));
    }

    private void checkTilePos(TilePos pos, boolean wh_exact) {
        if (pos.getX() < 0)
            pos.setX(0);
        if (pos.getY() < 0)
            pos.setY(0);

        if (wh_exact) {
            if (pos.getX() >= worldWidth)
                pos.setX(worldWidth);
            if (pos.getY() >= worldHeight)
                pos.setY(worldHeight);
        } else {
            if (pos.getX() > worldWidth)
                pos.setX(worldWidth);
            if (pos.getY() > worldHeight)
                pos.setY(worldHeight);
        }
    }

    private void loadWorld(String path) throws IOException, ParseException {
        WorldJsonLoader worldJsonLoader = new WorldJsonLoader(path, maewilEngine);

        worldJsonLoader.load();
        MaewilLauncher.LOGGER.info("Loading world [" + worldJsonLoader.getName() + "]!");

        worldName = worldJsonLoader.getName();

        worldWidth = worldJsonLoader.getWidth();
        worldHeight = worldJsonLoader.getHeight();
        spawnX = worldJsonLoader.getSpawnX();
        spawnY = worldJsonLoader.getSpawnY();

        TileList bgTileIDs = worldJsonLoader.getBGTiles();
        TileList fgTileIDs = worldJsonLoader.getFGTiles();

        bgTiles = new TileList(worldWidth, worldHeight);
        fgTiles = new TileList(worldWidth, worldHeight);

        maewilEngine.setUsername(worldJsonLoader.getUsername());

        for (int y = 0; y < worldHeight; y++) {
            for (int x = 0; x < worldWidth; x++) {
                Tile tile = bgTileIDs.getTileAtPos(x, y).pos(new TilePos(x, y)).isSolid(false);
                if (x <= 0 || y <= 0 || x >= worldWidth - 1 || y >= worldHeight - 1) {
                    tile.isSolid(true);
                    tile.isUnbreakable(true);
                }
                bgTiles.setTileAtPos(x, y, tile);
            }
        }
        MaewilLauncher.LOGGER.info("Loaded background tiles!");

        for (int y = 0; y < worldHeight; y++) {
            for (int x = 0; x < worldWidth; x++) {
                Tile tile = fgTileIDs.getTileAtPos(x, y).pos(new TilePos(x, y));
                if (x <= 0 || y <= 0 || x >= worldWidth - 1 || y >= worldHeight - 1) {
                    tile.isSolid(true);
                    tile.isUnbreakable(true);
                }
                fgTiles.setTileAtPos(x, y, tile);
            }
        }
        MaewilLauncher.LOGGER.info("Loaded foreground tiles!");
    }

    public String getWorldName() {
        return worldName;
    }

    public int getWorldWidth() {
        return worldWidth;
    }

    public int getWorldHeight() {
        return worldHeight;
    }

    public float getSpawnX() {
        return spawnX;
    }

    public float getSpawnY() {
        return spawnY;
    }

    public TileList getBGTiles() {
        return bgTiles;
    }

    public TileList getFgTiles() {
        return fgTiles;
    }

    public void stopUpdateThreads() {
        this.isForcedUpdateThread = false;
    }

}
