package io.github.vampirestudios.tdg.objs.tiles;

import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.world.colormap.WorldColors;

public class GrassTile extends Tile {

    public GrassTile(MaewilEngine maewilEngine) {
        super(maewilEngine, "grass", false, Tile.TileType.GROUND);
        this.setMapColor(WorldColors.GRASS);
    }

    @Override
    public GrassTile newCopy() {
        return super.newCopy(new GrassTile(maewilEngine));
    }
}
