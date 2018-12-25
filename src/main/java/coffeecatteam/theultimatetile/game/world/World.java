package coffeecatteam.theultimatetile.game.world;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.tile.Tile;
import coffeecatteam.theultimatetile.game.tile.TilePos;
import coffeecatteam.theultimatetile.game.tile.tiles.TileBreakable;
import coffeecatteam.theultimatetile.jsonparsers.world.WorldJsonLoader;
import coffeecatteam.theultimatetile.manager.OverlayManager;
import coffeecatteam.theultimatetile.utils.PositionOutOfBoundsException;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.IOException;

public class World {

    private Engine engine;
    private String worldName;
    private int width, height;
    private float spawnX;
    private float spawnY;

    private Tile[][] bg_tiles;
    private Tile[][] fg_tiles;

    private OverlayManager overlayManager;

    public World(Engine engine, String path, String worldName) {
        this.engine = engine;
        this.worldName = worldName;
        overlayManager = new OverlayManager(engine, ((GameEngine) engine).getEntityManager().getPlayer());

        try {
            loadWorld(path);
        } catch (IOException | ParseException e) {
            engine.getLogger().print(e);
        }
        ((GameEngine) engine).getEntityManager().getPlayer().setX(spawnX * Tile.TILE_WIDTH);
        ((GameEngine) engine).getEntityManager().getPlayer().setY(spawnY * Tile.TILE_HEIGHT);
    }

    public World(Engine engine, String worldName, int width, int height, int spawnX, int spawnY, Tile[][] bg_tiles, Tile[][] fg_tiles) {
        this.engine = engine;
        this.worldName = worldName;
        this.width = width;
        this.height = height;
        this.spawnX = spawnX;
        this.spawnY = spawnY;
        this.bg_tiles = bg_tiles;
        this.fg_tiles = fg_tiles;
    }

    public void tick() {
        int xStart = (int) Math.max(0, ((GameEngine) engine).getCamera().getxOffset() / Tile.TILE_WIDTH);
        int xEnd = (int) Math.min(width, (((GameEngine) engine).getCamera().getxOffset() + engine.getWidth()) / Tile.TILE_WIDTH + 1);
        int yStart = (int) Math.max(0, ((GameEngine) engine).getCamera().getyOffset() / Tile.TILE_HEIGHT);
        int yEnd = (int) Math.min(height, (((GameEngine) engine).getCamera().getyOffset() + engine.getHeight()) / Tile.TILE_HEIGHT + 1);

        for (int y = yStart; y < yEnd; y++) {
            for (int x = xStart; x < xEnd; x++) {
                getBGTile(x, y).updateBounds();
                getFGTile(x, y).tick();
            }
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                getBGTile(x, y).forcedTick();
                getBGTile(x, y).setWorldLayer(bg_tiles);
                getFGTile(x, y).forcedTick();
                getFGTile(x, y).setWorldLayer(fg_tiles);
            }
        }

        ((GameEngine) engine).getItemManager().tick();
        ((GameEngine) engine).getEntityManager().tick();
        overlayManager.tick();
    }

    public void render(Graphics2D g) {
        int xStart = (int) Math.max(0, ((GameEngine) engine).getCamera().getxOffset() / Tile.TILE_WIDTH);
        int xEnd = (int) Math.min(width, (((GameEngine) engine).getCamera().getxOffset() + engine.getWidth()) / Tile.TILE_WIDTH + 1);
        int yStart = (int) Math.max(0, ((GameEngine) engine).getCamera().getyOffset() / Tile.TILE_HEIGHT);
        int yEnd = (int) Math.min(height, (((GameEngine) engine).getCamera().getyOffset() + engine.getHeight()) / Tile.TILE_HEIGHT + 1);

        for (int y = yStart; y < yEnd; y++)
            for (int x = xStart; x < xEnd; x++)
                getBGTile(x, y).render(g);


        g.setColor(new Color(63, 63, 63, 127));
        g.fillRect(0, 0, engine.getWidth(), engine.getHeight());

        for (int y = yStart; y < yEnd; y++)
            for (int x = xStart; x < xEnd; x++)
                getFGTile(x, y).render(g);

        ((GameEngine) engine).getItemManager().render(g);
        ((GameEngine) engine).getEntityManager().render(g);
        overlayManager.render(g);
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
        try {
            if (pos.getX() < 0 || pos.getY() < 0)
                throw new PositionOutOfBoundsException(pos.toVector2D());
            else if (wh_exact) {
                if (pos.getX() >= width || pos.getY() >= height)
                    throw new PositionOutOfBoundsException(pos.toVector2D());
            } else {
                if (pos.getX() > width || pos.getY() > height)
                    throw new PositionOutOfBoundsException(pos.toVector2D());
            }
        } catch (PositionOutOfBoundsException e) {
            engine.getLogger().print(e);
            engine.setRunning(false);
        }
    }

    private void loadWorld(String path) throws IOException, ParseException {
        WorldJsonLoader worldJsonLoader = new WorldJsonLoader(path, engine); // "/assets/worlds/dev_tests/json_format"

        worldJsonLoader.load();
        engine.getLogger().print("Loading world [" + worldJsonLoader.getName() + "]!");

        worldName = worldJsonLoader.getName();

        width = worldJsonLoader.getWidth();
        height = worldJsonLoader.getHeight();
        spawnX = worldJsonLoader.getSpawnX();
        spawnY = worldJsonLoader.getSpawnY();

        Tile[][] bg_tile_ids = worldJsonLoader.getBg_tiles().clone();
        Tile[][] fg_tile_ids = worldJsonLoader.getFg_tiles().clone();
        bg_tiles = new Tile[width][height];
        fg_tiles = new Tile[width][height];

        GameEngine.getGameEngine().setUsername(worldJsonLoader.getUsername());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Tile tile = bg_tile_ids[x][y].setPos(new TilePos(x, y)).setSolid(false);
                if (x <= 0 || y <= 0 || x >= width - 1 || y >= height - 1) {
                    tile.setSolid(true);
                    if (tile instanceof TileBreakable)
                        ((TileBreakable) tile).setMineable(false);
                }
                bg_tiles[x][y] = tile;
            }
        }
        engine.getLogger().print("Loaded background tiles!");

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Tile tile = fg_tile_ids[x][y].setPos(new TilePos(x, y));
                if (x <= 0 || y <= 0 || x >= width - 1 || y >= height - 1) {
                    tile.setSolid(true);
                    if (tile instanceof TileBreakable)
                        ((TileBreakable) tile).setMineable(false);
                }
                fg_tiles[x][y] = tile;
            }
        }
        engine.getLogger().print("Loaded foreground tiles!");
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
}
