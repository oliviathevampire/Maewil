package io.github.vampirestudios.tdg.objs.tiles;

public class TileStack {

    private Tile tile;

    public TileStack(Tile tile) {
        this.tile = tile;
    }

    public TileStack copy() {
        return new TileStack(this.tile);
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }
}
