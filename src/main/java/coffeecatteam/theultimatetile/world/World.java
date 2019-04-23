package coffeecatteam.theultimatetile.world;

import coffeecatteam.coffeecatutils.logger.CatLogger;
import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.start.TutLauncher;
import coffeecatteam.theultimatetile.jsonparsers.world.WorldJsonLoader;
import coffeecatteam.theultimatetile.manager.OverlayManager;
import coffeecatteam.theultimatetile.objs.items.Items;
import coffeecatteam.theultimatetile.objs.tiles.Tile;
import coffeecatteam.theultimatetile.objs.tiles.TilePos;
import org.json.simple.parser.ParseException;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;

public class World {

    private TutEngine tutEngine;
    private String worldName;
    private int width, height;
    private float spawnX;
    private float spawnY;
    private long seed = 0;

    private TileList bg_tiles, fg_tiles;

    private OverlayManager overlayManager;

    private boolean isForcedUpdateThread = false;
    private Thread forcedUpdateThread;
    private GameContainer container;
    private int delta;

    public World(TutEngine tutEngine, String path, String worldName) {
        this.tutEngine = tutEngine;
        this.worldName = worldName;
        overlayManager = new OverlayManager(tutEngine, tutEngine.getPlayer());

        try {
            loadWorld(path);
        } catch (IOException | ParseException e) {
            TutLauncher.LOGGER.error(e);
        }
        tutEngine.getPlayer().setX(spawnX * Tile.TILE_SIZE);
        tutEngine.getPlayer().setY(spawnY * Tile.TILE_SIZE);
    }

    public World(TutEngine tutEngine, String worldName, int width, int height, Vector2D spawn, TileList bg_tiles, TileList fg_tiles) {
        this(tutEngine, worldName, width, height, (int) spawn.x, (int) spawn.y, bg_tiles, fg_tiles);
    }

    public World(TutEngine tutEngine, String worldName, int width, int height, int spawnX, int spawnY, TileList bg_tiles, TileList fg_tiles) {
        this.tutEngine = tutEngine;
        this.worldName = worldName;
        this.width = width;
        this.height = height;
        this.spawnX = spawnX;
        this.spawnY = spawnY;
        this.bg_tiles = bg_tiles;
        this.fg_tiles = fg_tiles;

        overlayManager = new OverlayManager(tutEngine, tutEngine.getPlayer());
    }

    public void update(GameContainer container, StateBasedGame game, int delta) {
        int xStart = (int) Math.max(0, tutEngine.getCamera().getxOffset() / Tile.TILE_SIZE);
        int xEnd = (int) Math.min(width, (tutEngine.getCamera().getxOffset() + TutLauncher.WIDTH) / Tile.TILE_SIZE + 1);
        int yStart = (int) Math.max(0, tutEngine.getCamera().getyOffset() / Tile.TILE_SIZE);
        int yEnd = (int) Math.min(height, (tutEngine.getCamera().getyOffset() + TutLauncher.HEIGHT) / Tile.TILE_SIZE + 1);

        for (int y = yStart; y < yEnd; y++) {
            for (int x = xStart; x < xEnd; x++) {
                getBGTile(x, y).updateBounds();
                getBGTile(x, y).setWorldLayer(bg_tiles);

                getFGTile(x, y).update(container, game, delta);
                getFGTile(x, y).setWorldLayer(fg_tiles);
            }
        }

        this.container = container;
        this.delta = delta;
        updateThreadsInit();

        Items.UPDATABLE_TIEMS.forEach(i -> i.update(container, game, delta));

        tutEngine.getEntityManager().update(container, game, delta);
        overlayManager.update(container, game, delta);

        tutEngine.getCamera().centerOnEntity(tutEngine.getEntityManager().getPlayer());
    }

    private void updateThreadsInit() {
        if (!isForcedUpdateThread) {
            isForcedUpdateThread = true;

            forcedUpdateThread = new Thread(() -> {
                CatLogger logger = new CatLogger();
                logger.info("Forced update thread initialized");

                while (isForcedUpdateThread) {
                    for (int y = 0; y < height; y++) {
                        for (int x = 0; x < width; x++) {
                            getBGTile(x, y).forcedUpdate(container, delta);
                            getFGTile(x, y).forcedUpdate(container, delta);
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

    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        int xStart = (int) Math.max(0, tutEngine.getCamera().getxOffset() / Tile.TILE_SIZE);
        int xEnd = (int) Math.min(width, (tutEngine.getCamera().getxOffset() + TutLauncher.WIDTH) / Tile.TILE_SIZE + 1);
        int yStart = (int) Math.max(0, tutEngine.getCamera().getyOffset() / Tile.TILE_SIZE);
        int yEnd = (int) Math.min(height, (tutEngine.getCamera().getyOffset() + TutLauncher.HEIGHT) / Tile.TILE_SIZE + 1);

        for (int y = yStart; y < yEnd; y++)
            for (int x = xStart; x < xEnd; x++)
                getBGTile(x, y).render(container, game, g);

        Color tint = new Color(63, 63, 63, 128);
        g.setColor(tint);
        g.fillRect(0, 0, TutLauncher.WIDTH, TutLauncher.HEIGHT);

        for (int y = yStart; y < yEnd; y++)
            for (int x = xStart; x < xEnd; x++)
                getFGTile(x, y).render(container, game, g);

        tutEngine.getEntityManager().render(container, game, g);
        overlayManager.render(container, game, g);

        /*
         * Mini Map
         */
        WorldMiniMap.render(g, tutEngine, width, height, tint);
    }

    public Tile getBGTile(int x, int y) {
        return getBGTile(new TilePos(x, y));
    }

    public Tile getBGTile(TilePos pos) {
        pos = checkTilePos(pos, false);

        return bg_tiles.getTile(pos);
    }

    public void setBGTile(int x, int y, Tile tile) {
        setBGTile(new TilePos(x, y), tile);
    }

    public void setBGTile(TilePos pos, Tile tile) {
        pos = checkTilePos(pos, true);

        bg_tiles.setTile(pos, tile.setPos(pos));
    }

    public Tile getFGTile(int x, int y) {
        return getFGTile(new TilePos(x, y));
    }

    public Tile getFGTile(TilePos pos) {
        pos = checkTilePos(pos, false);

        return fg_tiles.getTile(pos);
    }

    public void setFGTile(int x, int y, Tile tile) {
        setFGTile(new TilePos(x, y), tile);
    }

    public void setFGTile(TilePos pos, Tile tile) {
        pos = checkTilePos(pos, true);

        fg_tiles.setTile(pos, tile.setPos(pos));
    }

    private TilePos checkTilePos(TilePos pos, boolean wh_exact) {
        if (pos.getX() < 0)
            pos.setX(0);
        if (pos.getY() < 0)
            pos.setY(0);

        if (wh_exact) {
            if (pos.getX() >= width)
                pos.setX(width);
            if (pos.getY() >= height)
                pos.setY(height);
        } else {
            if (pos.getX() > width)
                pos.setX(width);
            if (pos.getY() > height)
                pos.setY(height);
        }
        return pos;
    }

    private void loadWorld(String path) throws IOException, ParseException {
        WorldJsonLoader worldJsonLoader = new WorldJsonLoader(path, tutEngine); // "/assets/worlds/dev_tests/json_format"

        worldJsonLoader.load();
        TutLauncher.LOGGER.info("Loading world [" + worldJsonLoader.getName() + "]!");

        worldName = worldJsonLoader.getName();

        width = worldJsonLoader.getWidth();
        height = worldJsonLoader.getHeight();
        spawnX = worldJsonLoader.getSpawnX();
        spawnY = worldJsonLoader.getSpawnY();

        TileList bg_tile_ids = worldJsonLoader.getBg_tiles();
        TileList fg_tile_ids = worldJsonLoader.getFg_tiles();

        bg_tiles = new TileList(width, height);
        fg_tiles = new TileList(width, height);

        tutEngine.setUsername(worldJsonLoader.getUsername());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Tile tile = bg_tile_ids.getTile(x, y).setPos(new TilePos(x, y)).setSolid(false);
                if (x <= 0 || y <= 0 || x >= width - 1 || y >= height - 1) {
                    tile.setSolid(true);
                    tile.setUnbreakable(true);
                }
                bg_tiles.setTile(x, y, tile);
            }
        }
        TutLauncher.LOGGER.info("Loaded background tiles!");

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Tile tile = fg_tile_ids.getTile(x, y).setPos(new TilePos(x, y));
                if (x <= 0 || y <= 0 || x >= width - 1 || y >= height - 1) {
                    tile.setSolid(true);
                    tile.setUnbreakable(true);
                }
                fg_tiles.setTile(x, y, tile);
            }
        }
        TutLauncher.LOGGER.info("Loaded foreground tiles!");
    }

    public String getWorldName() {
        return worldName;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getSpawnX() {
        return spawnX;
    }

    public float getSpawnY() {
        return spawnY;
    }

    public TileList getBg_tiles() {
        return bg_tiles;
    }

    public TileList getFg_tiles() {
        return fg_tiles;
    }

    public void stopUpdateThreads() {
        this.isForcedUpdateThread = false;
    }

    public long getSeed() {
        return seed;
    }

    public World setSeed(long seed) {
        this.seed = seed;
        return this;
    }
}
