package coffeecatteam.theultimatetile.world;

import coffeecatteam.coffeecatutils.logger.CatLogger;
import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.jsonparsers.world.WorldJsonLoader;
import coffeecatteam.theultimatetile.manager.OverlayManager;
import coffeecatteam.theultimatetile.tile.Tile;
import coffeecatteam.theultimatetile.tile.TilePos;
import org.json.simple.parser.ParseException;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.io.IOException;

public class World {

    private TutEngine tutEngine;
    private String worldName;
    private int width, height;
    private float spawnX;
    private float spawnY;
    private long seed = 0;

    private Tile[][] bg_tiles;
    private Tile[][] fg_tiles;

    private OverlayManager overlayManager;

    private final Color tint = new Color(63, 63, 63, 127);

    private boolean isForcedUpdateThread = false;
    private Thread forcedUpdateThread;

    public World(TutEngine tutEngine, String path, String worldName) {
        this.tutEngine = tutEngine;
        this.worldName = worldName;
        overlayManager = new OverlayManager(tutEngine, tutEngine.getEntityManager().getPlayer());

        try {
            loadWorld(path);
        } catch (IOException | ParseException e) {
            tutEngine.getLogger().print(e);
        }
        tutEngine.getEntityManager().getPlayer().setX(spawnX * Tile.TILE_WIDTH);
        tutEngine.getEntityManager().getPlayer().setY(spawnY * Tile.TILE_HEIGHT);
    }

    public World(TutEngine tutEngine, String worldName, int width, int height, Vector2D spawn, Tile[][] bg_tiles, Tile[][] fg_tiles) {
        this(tutEngine, worldName, width, height, (int) spawn.x, (int) spawn.y, bg_tiles, fg_tiles);
    }

    public World(TutEngine tutEngine, String worldName, int width, int height, int spawnX, int spawnY, Tile[][] bg_tiles, Tile[][] fg_tiles) {
        this.tutEngine = tutEngine;
        this.worldName = worldName;
        this.width = width;
        this.height = height;
        this.spawnX = spawnX;
        this.spawnY = spawnY;
        this.bg_tiles = bg_tiles;
        this.fg_tiles = fg_tiles;

        overlayManager = new OverlayManager(tutEngine, tutEngine.getEntityManager().getPlayer());
    }

    public void update(GameContainer container, int delta) {
        int xStart = (int) Math.max(0, tutEngine.getCamera().getxOffset() / Tile.TILE_WIDTH);
        int xEnd = (int) Math.min(width, (tutEngine.getCamera().getxOffset() + tutEngine.getWidth()) / Tile.TILE_WIDTH + 1);
        int yStart = (int) Math.max(0, tutEngine.getCamera().getyOffset() / Tile.TILE_HEIGHT);
        int yEnd = (int) Math.min(height, (tutEngine.getCamera().getyOffset() + tutEngine.getHeight()) / Tile.TILE_HEIGHT + 1);

        for (int y = yStart; y < yEnd; y++) {
            for (int x = xStart; x < xEnd; x++) {
                getBGTile(x, y).updateBounds();
                getBGTile(x, y).setWorldLayer(bg_tiles);

                getFGTile(x, y).update(container, delta);
                getFGTile(x, y).setWorldLayer(fg_tiles);
            }
        }

        forcedUpdateThreadInit(container, delta);

        tutEngine.getItemManager().update(container, delta);
        tutEngine.getEntityManager().update(container, delta);
        overlayManager.update(container, delta);
    }

    /*
     * Update all tiles with a forcedUpdate method in-use/override
     */
    private void forcedUpdateThreadInit(GameContainer container, int delta) {
        if (!isForcedUpdateThread) {
            isForcedUpdateThread = true;

            forcedUpdateThread = new Thread(() -> {
                CatLogger logger = new CatLogger();
                logger.print("Forced update thread initialized");

                while (isForcedUpdateThread) {
                    for (int y = 0; y < height; y++) {
                        for (int x = 0; x < width; x++) {
                            getBGTile(x, y).forcedUpdate(container, delta);
                            getFGTile(x, y).forcedUpdate(container, delta);
                        }
                    }
                }

                logger.print("Stopping force update thread...");
                try {
                    forcedUpdateThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "WorldForcedUpdate-Thread");

            forcedUpdateThread.start();
        }
    }

    public void render(Graphics g) {
        int xStart = (int) Math.max(0, tutEngine.getCamera().getxOffset() / Tile.TILE_WIDTH);
        int xEnd = (int) Math.min(width, (tutEngine.getCamera().getxOffset() + tutEngine.getWidth()) / Tile.TILE_WIDTH + 1);
        int yStart = (int) Math.max(0, tutEngine.getCamera().getyOffset() / Tile.TILE_HEIGHT);
        int yEnd = (int) Math.min(height, (tutEngine.getCamera().getyOffset() + tutEngine.getHeight()) / Tile.TILE_HEIGHT + 1);

        for (int y = yStart; y < yEnd; y++)
            for (int x = xStart; x < xEnd; x++)
                getBGTile(x, y).render(g);

        g.setColor(tint);
        g.fillRect(0, 0, tutEngine.getWidth(), tutEngine.getHeight());

        for (int y = yStart; y < yEnd; y++)
            for (int x = xStart; x < xEnd; x++)
                getFGTile(x, y).render(g);

        tutEngine.getItemManager().render(g);
        tutEngine.getEntityManager().render(g);
        overlayManager.render(g);

        /*
         * Mini Map
         */
        WorldMiniMap.render(g, tutEngine, width, height, tint);
    }

    public Tile getBGTile(int x, int y) {
        return getBGTile(new TilePos(x, y));
    }

    public Tile getBGTile(TilePos pos) {
        checkTilePos(pos, false);

        return bg_tiles[pos.getX()][pos.getY()];
    }

    public void setBGTile(int x, int y, Tile tile) {
        setBGTile(new TilePos(x, y), tile);
    }

    public void setBGTile(TilePos pos, Tile tile) {
        checkTilePos(pos, true);

        bg_tiles[pos.getX()][pos.getY()] = tile.setPos(pos);
    }

    public Tile getFGTile(int x, int y) {
        return getFGTile(new TilePos(x, y));
    }

    public Tile getFGTile(TilePos pos) {
        checkTilePos(pos, false);

        return fg_tiles[pos.getX()][pos.getY()];
    }

    public void setFGTile(int x, int y, Tile tile) {
        setFGTile(new TilePos(x, y), tile);
    }

    public void setFGTile(TilePos pos, Tile tile) {
        checkTilePos(pos, true);

        fg_tiles[pos.getX()][pos.getY()] = tile.setPos(pos);
    }

    private void checkTilePos(TilePos pos, boolean wh_exact) {
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
    }

    private void loadWorld(String path) throws IOException, ParseException {
        WorldJsonLoader worldJsonLoader = new WorldJsonLoader(path, tutEngine); // "/assets/worlds/dev_tests/json_format"

        worldJsonLoader.load();
        tutEngine.getLogger().print("Loading world [" + worldJsonLoader.getName() + "]!");

        worldName = worldJsonLoader.getName();

        width = worldJsonLoader.getWidth();
        height = worldJsonLoader.getHeight();
        spawnX = worldJsonLoader.getSpawnX();
        spawnY = worldJsonLoader.getSpawnY();

        Tile[][] bg_tile_ids = worldJsonLoader.getBg_tiles().clone();
        Tile[][] fg_tile_ids = worldJsonLoader.getFg_tiles().clone();

        bg_tiles = new Tile[width][height];
        fg_tiles = new Tile[width][height];

        tutEngine.setUsername(worldJsonLoader.getUsername());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Tile tile = bg_tile_ids[x][y].setPos(new TilePos(x, y)).setSolid(false);
                if (x <= 0 || y <= 0 || x >= width - 1 || y >= height - 1) {
                    tile.setSolid(true);
                    tile.setUnbreakable(true);
                }
                bg_tiles[x][y] = tile;
            }
        }
        tutEngine.getLogger().print("Loaded background tiles!");

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Tile tile = fg_tile_ids[x][y].setPos(new TilePos(x, y));
                if (x <= 0 || y <= 0 || x >= width - 1 || y >= height - 1) {
                    tile.setSolid(true);
                    tile.setUnbreakable(true);
                }
                fg_tiles[x][y] = tile;
            }
        }
        tutEngine.getLogger().print("Loaded foreground tiles!");
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

    public Tile[][] getBg_tiles() {
        return bg_tiles;
    }

    public Tile[][] getFg_tiles() {
        return fg_tiles;
    }

    public void stopForcedUpdateThread() {
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
