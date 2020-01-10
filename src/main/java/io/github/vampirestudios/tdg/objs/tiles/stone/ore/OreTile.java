package io.github.vampirestudios.tdg.objs.tiles.stone.ore;

import io.github.vampirestudios.tdg.objs.items.Item;
import io.github.vampirestudios.tdg.objs.tiles.Tile;
import io.github.vampirestudios.tdg.start.TutEngine;
import io.github.vampirestudios.tdg.world.colormap.WorldColors;

/**
 * @author CoffeeCatRailway
 * Created: 22/12/2018
 */
public abstract class OreTile extends Tile {

    public OreTile(TutEngine tutEngine, String id, Item drop) {
        super(tutEngine, id, true, TileType.STONE);
        this.setMapColor(WorldColors.STONE);
        this.setDrop(drop.newCopy());
    }
}
