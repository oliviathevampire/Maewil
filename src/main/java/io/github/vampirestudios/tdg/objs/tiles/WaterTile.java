package io.github.vampirestudios.tdg.objs.tiles;

import io.github.vampirestudios.tdg.gfx.assets.Assets;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.world.colormap.WorldColors;

/**
 * @author CoffeeCatRailway
 * Created: 26/12/2018
 */
public class WaterTile extends OverlapTile {

    public WaterTile(MaewilEngine maewilEngine) {
        super(maewilEngine, Assets.SAND, "water", false, TileType.FLUID, Assets.SAND_ALTS);
        this.setMapColor(WorldColors.WATER);
        this.setConnect("sand", "grass");
        this.setIgnore("water");
    }

    @Override
    public WaterTile newCopy() {
        return super.newCopy(new WaterTile(maewilEngine));
    }
}
