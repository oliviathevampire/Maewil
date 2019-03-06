package coffeecatteam.theultimatetile.world;

import coffeecatteam.theultimatetile.objs.tiles.Tile;
import coffeecatteam.theultimatetile.objs.tiles.TilePos;
import coffeecatteam.theultimatetile.objs.tiles.Tiles;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CoffeeCatRailway
 * Created: 3/03/2019
 */
public class TileList {

    private final List<List<Tile>> TILES = new ArrayList<>();

    private int width, height;

    public TileList(int size) {
        this(size, size);
    }

    public TileList(int width, int height) {
        this.width = width;
        this.height = height;

        for (int y = 0; y < this.height; y++) {
            TILES.add(new ArrayList<>());
            for (int x = 0; x < this.width; x++) {
                TILES.get(y).add(Tiles.AIR.newCopy());
            }
        }
    }

    public void setTile(TilePos pos, Tile tile) {
        setTile(pos.getX(), pos.getY(), tile);
    }

    public void setTile(int x, int y, Tile tile) {
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        if (x >= width) x = width - 1;
        if (y >= height) y = height - 1;

        TILES.get(y).set(x, tile);
    }

    public Tile getTile(TilePos pos) {
        return getTile(pos.getX(), pos.getY());
    }

    public Tile getTile(int x, int y) {
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        if (x >= width) x = width - 1;
        if (y >= height) y = height - 1;

        return TILES.get(y).get(x);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
