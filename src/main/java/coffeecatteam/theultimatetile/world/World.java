package coffeecatteam.theultimatetile.world;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.manager.OverlayManager;
import coffeecatteam.theultimatetile.tiles.Tile;
import coffeecatteam.theultimatetile.tiles.Tiles;
import coffeecatteam.theultimatetile.utils.Logger;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class World {

    private TheUltimateTile theUltimateTile;
    private int width, height;
    private int spawnX, spawnY;

    private int[][] bg_tile_ids;
    private int[][] fg_tile_ids;
    private List<Tile> BG_TILES = new ArrayList<>();
    private List<Tile> FG_TILES = new ArrayList<>();

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
        BG_TILES.forEach(Tile::updateBounds);
        FG_TILES.forEach(Tile::tick);

        theUltimateTile.getItemManager().tick();
        theUltimateTile.getEntityManager().tick();
        overlayManager.tick();
    }

    public void render(Graphics g) {
        BG_TILES.forEach(t -> t.render(g, new Color(63, 63, 63, 127)));
        FG_TILES.forEach(t -> t.render(g));

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

        return BG_TILES.get(bg_tile_ids[x][y]);
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

        BG_TILES.set(bg_tile_ids[x][y], tile.setPos(x, y));
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

        return FG_TILES.get(fg_tile_ids[x][y]);
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

        FG_TILES.set(fg_tile_ids[x][y], tile.setPos(x, y));
    }

    private void loadWorld(String path) throws IOException, ParseException {
        WorldLoader worldLoader = new WorldLoader(path, theUltimateTile); // "/assets/worlds/dev_tests/json_format"

        worldLoader.loadWorld();
        Logger.print("\nLoading world [" + worldLoader.getName() + "]!");

        width = worldLoader.getWidth();
        height = worldLoader.getHeight();
        spawnX = worldLoader.getSpawnX();
        spawnY = worldLoader.getSpawnY();

        bg_tile_ids = worldLoader.getBg_tiles().clone();
        fg_tile_ids = worldLoader.getFg_tiles().clone();

        int bgid;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                bgid = bg_tile_ids[x][y];
                BG_TILES.add(Tiles.getTile(theUltimateTile, bgid).setPos(x, y).setSolid(false));
            }
        }
        Logger.print("Loaded background tiles!");

        int fgid;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                fgid = fg_tile_ids[x][y];
                FG_TILES.add(Tiles.getTile(theUltimateTile, fgid).setPos(x, y));
            }
        }
        Logger.print("Loaded foreground tiles!");

        worldLoader.loadObjects();
        Logger.print("Loaded entities!");
        Logger.print("Loaded items!");
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
