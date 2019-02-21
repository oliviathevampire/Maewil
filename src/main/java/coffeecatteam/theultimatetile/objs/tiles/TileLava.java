package coffeecatteam.theultimatetile.objs.tiles;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.world.colormap.WorldColors;

public class TileLava extends Tile {

    public TileLava(TutEngine tutEngine) {
        super(tutEngine, "lava", false, Tile.TileType.FLUID);
        this.setMapColor(WorldColors.LAVA);
    }

    @Override
    public TileLava newTile() {
        return super.newTile(new TileLava(tutEngine));
    }
}