package coffeecatteam.theultimatetile.objs.tiles;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.world.colormap.WorldColors;

public class TileGlitch extends Tile {

    public TileGlitch(TutEngine tutEngine) {
        super(tutEngine, "glitch", false, TileType.GROUND);
        this.setMapColor(WorldColors.GRASS);
    }

    @Override
    public TileGlitch newTile() {
        return super.newTile(new TileGlitch(tutEngine));
    }
}
