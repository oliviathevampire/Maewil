package coffeecatteam.theultimatetile.tile;

public class TileStack {

    private Tile tile;

    public TileStack(Tile tile) {
        this.tile = tile;
    }

    public TileStack copy() {
        TileStack tileStack = new TileStack(this.tile);
        return tileStack;
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }
}
