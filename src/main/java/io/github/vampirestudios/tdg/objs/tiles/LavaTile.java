package io.github.vampirestudios.tdg.objs.tiles;

import io.github.vampirestudios.tdg.start.TutEngine;
import io.github.vampirestudios.tdg.world.colormap.WorldColors;

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
