package io.github.vampirestudios.tdg.objs.tiles.wood;

import io.github.vampirestudios.tdg.objs.tiles.Tile;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.world.colormap.WorldColors;

/**
 * @author CoffeeCatRailway
 * Created: 24/12/2018
 */
public abstract class WoodTile extends Tile {

    public WoodTile(MaewilEngine maewilEngine, String id) {
        super(maewilEngine, id, true, TileType.WOOD);
        this.setMapColor(WorldColors.WOOD);
    }
}
