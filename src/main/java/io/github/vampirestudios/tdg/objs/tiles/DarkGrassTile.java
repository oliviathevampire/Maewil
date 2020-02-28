package io.github.vampirestudios.tdg.objs.tiles;

import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.world.colormap.WorldColors;

public class DarkGrassTile extends Tile {

    public DarkGrassTile(MaewilEngine maewilEngine) {
        super(maewilEngine, "dark_grass", false, TileType.GROUND);
        this.setMapColor(WorldColors.DARK_GRASS);
    }

    @Override
    public DarkGrassTile newCopy() {
        return super.newCopy(new DarkGrassTile(maewilEngine));
    }
}
