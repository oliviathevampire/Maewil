package io.github.vampirestudios.tdg.objs.tiles;

import io.github.vampirestudios.tdg.gfx.assets.Assets;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.world.colormap.WorldColors;

public class DarkGrassTile extends OverlapTile {

    public DarkGrassTile(MaewilEngine maewilEngine) {
        super(maewilEngine, Assets.DARK_GRASS, "dark_grass", false, TileType.GROUND, Assets.DARK_GRASS_ALTS);
        this.setMapColor(WorldColors.DARK_GRASS);
        this.setConnect("grass");
    }

    @Override
    public DarkGrassTile newCopy() {
        return super.newCopy(new DarkGrassTile(maewilEngine));
    }
}
