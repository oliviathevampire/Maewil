package coffeecatteam.theultimatetile.objs.tiles;

import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.world.colormap.WorldColors;

public class GlitchTile extends Tile {

    public GlitchTile(TutEngine tutEngine) {
        super(tutEngine, "glitch", false, TileType.GROUND);
        this.setMapColor(WorldColors.GRASS);
    }

    @Override
    public GlitchTile newCopy() {
        return super.newCopy(new GlitchTile(tutEngine));
    }
}
