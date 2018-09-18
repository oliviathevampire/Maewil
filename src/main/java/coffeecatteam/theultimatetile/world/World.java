package coffeecatteam.theultimatetile.world;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.manager.OverlayManager;
import coffeecatteam.theultimatetile.tiles.Tile;
import coffeecatteam.theultimatetile.tiles.Tiles;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class World {

    private TheUltimateTile theUltimateTile;
    private int width, height;
    private int spawnX, spawnY;

    private int[][] bg_tiles;
    private int[][] tiles;

    private OverlayManager overlayManager;

    public World(TheUltimateTile theUltimateTile, String path) {
        this.theUltimateTile = theUltimateTile;
        overlayManager = new OverlayManager(theUltimateTile, theUltimateTile.getEntityManager().getPlayer());

        loadWorld(path);
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
                // getTile(bg_tiles, x, y).tick(); <-- Not 100% sure if this should be here -CoffeeCat
                getTile(tiles, x, y).tick();
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
                getTile(bg_tiles, x, y).render(g, (int) (x * Tile.TILE_WIDTH - theUltimateTile.getCamera().getxOffset()), (int) (y * Tile.TILE_HEIGHT - theUltimateTile.getCamera().getyOffset()), new Color(63, 63, 63, 127));
                getTile(tiles, x, y).render(g, (int) (x * Tile.TILE_WIDTH - theUltimateTile.getCamera().getxOffset()), (int) (y * Tile.TILE_HEIGHT - theUltimateTile.getCamera().getyOffset()));
            }
        }

        theUltimateTile.getItemManager().render(g);
        theUltimateTile.getEntityManager().render(g);
        overlayManager.render(g);
    }

    public void setTile(int x, int y, Tile tile) {
        if (x < 0)
            x = 0;
        if (y < 0)
            y = 0;
        if (x >= width)
            x = width;
        if (y >= height)
            y = height;

        tiles[x][y] = tile.getId();
    }

    public Tile getTile(int x, int y) {
        return getTile(tiles, x, y);
    }

    public Tile getTile(int[][] grid, int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height)
            return Tiles.DIRT;

        Tile t = Tile.tiles[grid[x][y]];
        if (t == null)
            return Tiles.DIRT;
        t.setPos(x, y);
        return t;
    }

    private void loadWorld(String path) {
        WorldLoader worldLoader = new WorldLoader(path, theUltimateTile); // "/assets/worlds/dev_tests/json_format"
        try {
            worldLoader.loadWorld(true);
        } catch (IOException | ParseException | URISyntaxException e) {
            e.printStackTrace();
        }
        width = worldLoader.getWidth();
        height = worldLoader.getHeight();
        spawnX = worldLoader.getSpawnX();
        spawnY = worldLoader.getSpawnY();

        bg_tiles = worldLoader.getBg_tiles();
        tiles = worldLoader.getFg_tiles();

        try {
            worldLoader.loadObjects(true);
        } catch (IOException | ParseException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public TheUltimateTile getTheUltimateTile() {
        return theUltimateTile;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getSpawnX() {
        return spawnX;
    }

    public int getSpawnY() {
        return spawnY;
    }
}
