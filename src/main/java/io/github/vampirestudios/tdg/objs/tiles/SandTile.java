package io.github.vampirestudios.tdg.objs.tiles;

import io.github.vampirestudios.tdg.gfx.assets.Assets;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.world.colormap.WorldColors;

public class SandTile extends OverlapTile {

    public SandTile(MaewilEngine maewilEngine) {
        super(maewilEngine, Assets.SAND, "sand", false, TileType.GROUND, Assets.SAND_ALTS);
        this.setMapColor(WorldColors.SAND);
        this.setConnect("grass", "dirt");
        this.setIgnore("sand", "red_sand");
    }

    @Override
    public SandTile newCopy() {
        return super.newCopy(new SandTile(maewilEngine));
    }
}
