package coffeecatteam.theultimatetile.tiles;

import java.awt.image.BufferedImage;

public class TileBase extends Tile {

    private boolean isSolid;

    public TileBase(BufferedImage texture, int id, boolean isSolid) {
        super(texture, id);
        this.isSolid = isSolid;
    }

    @Override
    public boolean isSolid() {
        return isSolid;
    }
}
