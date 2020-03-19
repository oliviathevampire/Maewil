package io.github.vampirestudios.tdg.objs.tiles;

import io.github.vampirestudios.tdg.gfx.assets.Assets;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.world.colormap.WorldColors;

public class SandTile extends ConnectingTile {

    public SandTile(MaewilEngine maewilEngine) {
        super(maewilEngine, Assets.GRASS, "sand", false, TileType.GROUND, Assets.GRASS_ALTS);
        this.setMapColor(WorldColors.SAND);
        this.connectsTo("grass");
        this.ignores("sand", "red_sand");
    }

    @Override
    public SandTile newCopy() {
        return super.newCopy(new SandTile(maewilEngine));
    }
}
