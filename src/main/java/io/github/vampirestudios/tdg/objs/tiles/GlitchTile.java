package io.github.vampirestudios.tdg.objs.tiles;

import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.world.colormap.WorldColors;

public class GlitchTile extends Tile {

    public GlitchTile(MaewilEngine maewilEngine) {
        super(maewilEngine, "glitch", false, TileType.GROUND);
        this.setMapColor(WorldColors.GRASS);
    }

    @Override
    public GlitchTile newCopy() {
        return super.newCopy(new GlitchTile(maewilEngine));
    }
}
