package io.github.vampirestudios.tdg.objs.tiles;

import io.github.vampirestudios.tdg.start.TutEngine;
import io.github.vampirestudios.tdg.world.colormap.WorldColors;

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
