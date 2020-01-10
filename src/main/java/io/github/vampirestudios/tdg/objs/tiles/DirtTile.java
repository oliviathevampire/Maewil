package io.github.vampirestudios.tdg.objs.tiles;

import io.github.vampirestudios.tdg.gfx.assets.Assets;
import io.github.vampirestudios.tdg.start.TutEngine;
import io.github.vampirestudios.tdg.world.colormap.WorldColors;

/**
 * @author CoffeeCatRailway
 * Created: 22/12/2018
 */
public class DirtTile extends OverlapTile {

    public DirtTile(TutEngine tutEngine) {
        super(tutEngine, Assets.GRASS, "dirt", false, Tile.TileType.GROUND, Assets.GRASS_ALTS);
        this.setMapColor(WorldColors.DIRT);
        this.setConnect("grass");
        this.setIgnore("dirt");
    }

    @Override
    public DirtTile newCopy() {
        return super.newCopy(new DirtTile(tutEngine));
    }
}
