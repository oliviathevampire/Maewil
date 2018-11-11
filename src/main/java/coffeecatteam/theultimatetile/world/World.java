package coffeecatteam.theultimatetile.world;

import coffeecatteam.theultimatetile.GameEngine;
import coffeecatteam.theultimatetile.jsonparsers.world.WorldJsonLoader;
import coffeecatteam.theultimatetile.manager.OverlayManager;
import coffeecatteam.theultimatetile.tiles.Tile;
import coffeecatteam.theultimatetile.tiles.TileBreakable;
import coffeecatteam.utils.DiscordHandler;
import coffeecatteam.utils.Logger;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.IOException;

public class World {

    private GameEngine gameEngine;
    private String name;
    private int width, height;
    private float spawnX;
    private float spawnY;

    private Tile[][] bg_tiles;
    private Tile[][] fg_tiles;

    private OverlayManager overlayManager;

    public World(GameEngine gameEngine, String path, String worldName) {
        this.gameEngine = gameEngine;
        overlayManager = new OverlayManager(gameEngine, gameEngine.getEntityManager().getPlayer());

        try {
            loadWorld(path);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        gameEngine.getEntityManager().getPlayer().setX(spawnX * Tile.TILE_WIDTH);
        gameEngine.getEntityManager().getPlayer().setY(spawnY * Tile.TILE_HEIGHT);

        DiscordHandler.INSTANCE.updatePresence("In Game - " + gameEngine.getEntityManager().getPlayer().getUsername(),
                "World: " + name + " - Save: [" + worldName + "]", true);
    }

    public void tick() {
        int xStart = (int) Math.max(0, gameEngine.getCamera().getxOffset() / Tile.TILE_WIDTH);
        int xEnd = (int) Math.min(width, (gameEngine.getCamera().getxOffset() + gameEngine.getWidth()) / Tile.TILE_WIDTH + 1);
        int yStart = (int) Math.max(0, gameEngine.getCamera().getyOffset() / Tile.TILE_HEIGHT);
        int yEnd = (int) Math.min(height, (gameEngine.getCamera().getyOffset() + gameEngine.getHeight()) / Tile.TILE_HEIGHT + 1);

        for (int y = yStart; y < yEnd; y++) {
            for (int x = xStart; x < xEnd; x++) {
                getBGTile(x, y).updateBounds();
                getFGTile(x, y).tick();
            }
        }

        gameEngine.getItemManager().tick();
        gameEngine.getEntityManager().tick();
        overlayManager.tick();
    }

    public void render(Graphics g) {
        int xStart = (int) Math.max(0, gameEngine.getCamera().getxOffset() / Tile.TILE_WIDTH);
        int xEnd = (int) Math.min(width, (gameEngine.getCamera().getxOffset() + gameEngine.getWidth()) / Tile.TILE_WIDTH + 1);
        int yStart = (int) Math.max(0, gameEngine.getCamera().getyOffset() / Tile.TILE_HEIGHT);
        int yEnd = (int) Math.min(height, (gameEngine.getCamera().getyOffset() + gameEngine.getHeight()) / Tile.TILE_HEIGHT + 1);

        for (int y = yStart; y < yEnd; y++) {
            for (int x = xStart; x < xEnd; x++) {
                getBGTile(x, y).render(g, new Color(63, 63, 63, 127));
                getFGTile(x, y).render(g);
            }
        }

        gameEngine.getItemManager().render(g);
        gameEngine.getEntityManager().render(g);
        overlayManager.render(g);
    }

    public Tile getBGTile(int x, int y) {
        if (x < 0)
            x = 0;
        if (y < 0)
            y = 0;
        if (x >= width)
            x = width - 1;
        if (y >= height)
            y = height - 1;

        return bg_tiles[x][y];
    }

    public void setBGTile(int x, int y, Tile tile) {
        if (x < 0)
            x = 0;
        if (y < 0)
            y = 0;
        if (x > width)
            x = width;
        if (y > height)
            y = height;

        bg_tiles[x][y] = tile.setPos(x, y);
    }

    public Tile getFGTile(int x, int y) {
        if (x < 0)
            x = 0;
        if (y < 0)
            y = 0;
        if (x >= width)
            x = width - 1;
        if (y >= height)
            y = height - 1;

        return fg_tiles[x][y];
    }

    public void setFGTile(int x, int y, Tile tile) {
        if (x < 0)
            x = 0;
        if (y < 0)
            y = 0;
        if (x > width)
            x = width;
        if (y > height)
            y = height;

        fg_tiles[x][y] = tile.setPos(x, y);
    }

    private void loadWorld(String path) throws IOException, ParseException {
        WorldJsonLoader worldJsonLoader = new WorldJsonLoader(path, gameEngine); // "/assets/worlds/dev_tests/json_format"

        worldJsonLoader.load();
        Logger.print("Loading world [" + worldJsonLoader.getName() + "]!");

        name = worldJsonLoader.getName();

        width = worldJsonLoader.getWidth();
        height = worldJsonLoader.getHeight();
        spawnX = worldJsonLoader.getSpawnX();
        spawnY = worldJsonLoader.getSpawnY();

        Tile[][] bg_tile_ids = worldJsonLoader.getBg_tiles().clone();
        Tile[][] fg_tile_ids = worldJsonLoader.getFg_tiles().clone();
        bg_tiles = new Tile[width][height];
        fg_tiles = new Tile[width][height];

        gameEngine.getEntityManager().getPlayer().setUsername(worldJsonLoader.getUsername());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Tile tile = bg_tile_ids[x][y].setPos(x, y).setSolid(false);
                if (x <= 0 || y <= 0 || x >= width - 1 || y >= height - 1) {
                    tile.setSolid(true);
                    if (tile instanceof TileBreakable)
                        ((TileBreakable) tile).setMineable(false);
                }
                bg_tiles[x][y] = tile;
            }
        }
        Logger.print("Loaded background tiles!");

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Tile tile = fg_tile_ids[x][y].setPos(x, y);
                if (x <= 0 || y <= 0 || x >= width - 1 || y >= height - 1) {
                    tile.setSolid(true);
                    if (tile instanceof TileBreakable)
                        ((TileBreakable) tile).setMineable(false);
                }
                fg_tiles[x][y] = tile;
            }
        }
        Logger.print("Loaded foreground tiles!\n");
    }

    public GameEngine getGameEngine() {
        return gameEngine;
    }

    public String getName() {
        return name;
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
}
