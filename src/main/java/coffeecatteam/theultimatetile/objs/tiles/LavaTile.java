package coffeecatteam.theultimatetile.objs.tiles;

import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.world.colormap.WorldColors;

public class LavaTile extends Tile {

    public LavaTile(TutEngine tutEngine) {
        super(tutEngine, "lava", false, Tile.TileType.FLUID);
        this.setMapColor(WorldColors.LAVA);
    }

    @Override
    public LavaTile newCopy() {
        return super.newCopy(new LavaTile(tutEngine));
    }
}
