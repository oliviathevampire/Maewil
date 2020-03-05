package io.github.vampirestudios.tdg.objs.tiles;

import io.github.vampirestudios.tdg.gfx.assets.Assets;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.world.colormap.WorldColors;

public class LavaTile extends OverlapTile {

    public LavaTile(MaewilEngine maewilEngine) {
        super(maewilEngine, Assets.LAVA, "lava", false, Tile.TileType.FLUID, Assets.LAVA_ALTS);
        this.setMapColor(WorldColors.LAVA);
    }

    @Override
    public LavaTile newCopy() {
        return super.newCopy(new LavaTile(maewilEngine));
    }
}
