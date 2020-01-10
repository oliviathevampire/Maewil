package io.github.vampirestudios.tdg.objs.tiles;

import io.github.vampirestudios.tdg.gfx.assets.Assets;
import io.github.vampirestudios.tdg.start.TutEngine;
import io.github.vampirestudios.tdg.world.colormap.WorldColors;

/**
 * @author CoffeeCatRailway
 * Created: 24/12/2018
 */
public class SandTile extends OverlapTile {

    public SandTile(TutEngine tutEngine) {
        super(tutEngine, Assets.SAND, "sand", false, TileType.GROUND, Assets.SAND_ALTS);
        this.setMapColor(WorldColors.SAND);
        this.setConnect("grass", "dirt");
        this.setIgnore("sand", "red_sand");
    }

    @Override
    public SandTile newCopy() {
        return super.newCopy(new SandTile(tutEngine));
    }
}
