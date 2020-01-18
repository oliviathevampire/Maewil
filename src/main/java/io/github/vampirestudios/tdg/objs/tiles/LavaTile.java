package io.github.vampirestudios.tdg.objs.tiles;

import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.world.colormap.WorldColors;

public class LavaTile extends Tile {

    public LavaTile(MaewilEngine maewilEngine) {
        super(maewilEngine, "lava", false, Tile.TileType.FLUID);
        this.setMapColor(WorldColors.LAVA);
    }

    @Override
    public LavaTile newCopy() {
        return super.newCopy(new LavaTile(maewilEngine));
    }
}
