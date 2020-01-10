package io.github.vampirestudios.tdg.objs.tiles.stone;

import io.github.vampirestudios.tdg.objs.items.Items;
import io.github.vampirestudios.tdg.objs.tiles.Tile;
import io.github.vampirestudios.tdg.start.TutEngine;
import io.github.vampirestudios.tdg.world.colormap.WorldColors;

/**
 * @author CoffeeCatRailway
 * Created: 22/12/2018
 */
public class BrokenStoneTile extends Tile {

    public BrokenStoneTile(TutEngine tutEngine) {
        super(tutEngine, "broken_stone", true, TileType.STONE);
        this.setMapColor(WorldColors.STONE);
        this.setDrop(Items.ROCK);
    }

    @Override
    public BrokenStoneTile newCopy() {
        return super.newCopy(new BrokenStoneTile(tutEngine));
    }
}
