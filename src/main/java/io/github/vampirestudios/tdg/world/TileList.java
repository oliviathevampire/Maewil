package io.github.vampirestudios.tdg.world;

import io.github.vampirestudios.tdg.objs.tiles.Tile;
import io.github.vampirestudios.tdg.objs.tiles.TilePos;
import io.github.vampirestudios.tdg.objs.tiles.Tiles;

import java.util.ArrayList;
import java.util.List;

public class TileList {

//    private final Tile[][][] TILES = new Tile[10][32][32];
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

    public void setTileAtPos(TilePos pos, Tile tile) {
        setTileAtPos(pos.getX(), pos.getY(), tile);
    }

    public void setTileAtPos(int x, int y, Tile tile) {
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        if (x >= width) x = width - 1;
        if (y >= height) y = height - 1;

        TILES.get(y).set(x, tile);
    }

    public Tile getTileAtPos(TilePos pos) {
        return getTileAtPos(pos.getX(), pos.getY());
    }

    public Tile getTileAtPos(int x, int y) {
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
