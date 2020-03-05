package io.github.vampirestudios.tdg.objs.tiles;

import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.world.colormap.WorldColors;

public class WaterTile extends Tile {

    public WaterTile(MaewilEngine maewilEngine) {
        super(maewilEngine, "water", false, TileType.FLUID);
        this.setMapColor(WorldColors.WATER);
    }

    @Override
    public WaterTile newCopy() {
        return super.newCopy(new WaterTile(maewilEngine));
    }
}
