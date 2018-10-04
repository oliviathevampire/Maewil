package coffeecatteam.theultimatetile.world;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.jsonparsers.world.WorldJsonLoader;
import coffeecatteam.theultimatetile.manager.OverlayManager;
import coffeecatteam.theultimatetile.tiles.Tile;
import coffeecatteam.theultimatetile.tiles.TileBreakable;
import coffeecatteam.theultimatetile.tiles.Tiles;
import coffeecatteam.theultimatetile.utils.Logger;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.IOException;

public class World {

    private TheUltimateTile theUltimateTile;
    private String name;
    private int width, height;
    private float spawnX;
    private float spawnY;

    private Tile[][] bg_tiles;
    private Tile[][] fg_tiles;

    private OverlayManager overlayManager;

    public World(TheUltimateTile theUltimateTile, String path) {
        this.theUltimateTile = theUltimateTile;
        overlayManager = new OverlayManager(theUltimateTile, theUltimateTile.getEntityManager().getPlayer());

        try {
            loadWorld(path);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        theUltimateTile.getEntityManager().getPlayer().setX(spawnX * Tile.TILE_WIDTH);
        theUltimateTile.getEntityManager().getPlayer().setY(spawnY * Tile.TILE_HEIGHT);
    }

    public void tick() {
        int xStart = (int) Math.max(0, theUltimateTile.getCamera().getxOffset() / Tile.TILE_WIDTH);
        int xEnd = (int) Math.min(width, (theUltimateTile.getCamera().getxOffset() + theUltimateTile.getWidth()) / Tile.TILE_WIDTH + 1);
        int yStart = (int) Math.max(0, theUltimateTile.getCamera().getyOffset() / Tile.TILE_HEIGHT);
        int yEnd = (int) Math.min(height, (theUltimateTile.getCamera().getyOffset() + theUltimateTile.getHeight()) / Tile.TILE_HEIGHT + 1);

        for (int y = yStart; y < yEnd; y++) {
            for (int x = xStart; x < xEnd; x++) {
                getBGTile(x, y).updateBounds();
                getFGTile(x, y).tick();
            }
        }

        theUltimateTile.getItemManager().tick();
        theUltimateTile.getEntityManager().tick();
        overlayManager.tick();
    }

    public void render(Graphics g) {
        int xStart = (int) Math.max(0, theUltimateTile.getCamera().getxOffset() / Tile.TILE_WIDTH);
        int xEnd = (int) Math.min(width, (theUltimateTile.getCamera().getxOffset() + theUltimateTile.getWidth()) / Tile.TILE_WIDTH + 1);
        int yStart = (int) Math.max(0, theUltimateTile.getCamera().getyOffset() / Tile.TILE_HEIGHT);
        int yEnd = (int) Math.min(height, (theUltimateTile.getCamera().getyOffset() + theUltimateTile.getHeight()) / Tile.TILE_HEIGHT + 1);

        for (int y = yStart; y < yEnd; y++) {
            for (int x = xStart; x < xEnd; x++) {
                getBGTile(x, y).render(g, new Color(63, 63, 63, 127));
                getFGTile(x, y).render(g);
            }
        }

        theUltimateTile.getItemManager().render(g);
        theUltimateTile.getEntityManager().render(g);
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
        WorldJsonLoader worldJsonLoader = new WorldJsonLoader(path, theUltimateTile); // "/assets/worlds/dev_tests/json_format"

        worldJsonLoader.load();
        Logger.print("Loading world [" + worldJsonLoader.getName() + "]!");

        name = worldJsonLoader.getName();

        width = worldJsonLoader.getWidth();
        height = worldJsonLoader.getHeight();
        spawnX = worldJsonLoader.getSpawnX();
        spawnY = worldJsonLoader.getSpawnY();

        int[][] bg_tile_ids = worldJsonLoader.getBg_tiles().clone();
        int[][] fg_tile_ids = worldJsonLoader.getFg_tiles().clone();
        bg_tiles = new Tile[width][height];
        fg_tiles = new Tile[width][height];

        int bgid;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                bgid = bg_tile_ids[x][y];
                Tile tile = Tiles.getTile(theUltimateTile, bgid).setPos(x, y).setSolid(false);
                if (x <= 0 || y <= 0 || x >= width - 1 || y >= height - 1) {
                    tile.setSolid(true);
                    if (tile instanceof TileBreakable)
                        ((TileBreakable) tile).setMineable(false);
                }
                bg_tiles[x][y] = tile;
            }
        }
        Logger.print("Loaded background tiles!");

        int fgid;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                fgid = fg_tile_ids[x][y];
                Tile tile = Tiles.getTile(theUltimateTile, fgid).setPos(x, y);
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

    public TheUltimateTile getTheUltimateTile() {
        return theUltimateTile;
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
