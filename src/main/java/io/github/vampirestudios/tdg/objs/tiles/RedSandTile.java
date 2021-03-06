package io.github.vampirestudios.tdg.objs.tiles;

import io.github.vampirestudios.tdg.gfx.assets.Assets;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.world.colormap.WorldColors;

public class RedSandTile extends ConnectingTile {

    public RedSandTile(MaewilEngine maewilEngine) {
        super(maewilEngine, Assets.RED_SAND, "red_sand", false, TileType.GROUND, Assets.RED_SAND_ALTS);
        this.setMapColor(WorldColors.RED_SAND);
        this.connectsTo("grass", "dirt");
        this.ignores("red_sand", "sand");
    }

    @Override
    public RedSandTile newCopy() {
        return super.newCopy(new RedSandTile(maewilEngine));
    }
}
