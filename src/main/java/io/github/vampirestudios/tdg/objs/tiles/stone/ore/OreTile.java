package io.github.vampirestudios.tdg.objs.tiles.stone.ore;

import io.github.vampirestudios.tdg.objs.items.Item;
import io.github.vampirestudios.tdg.objs.tiles.Tile;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.world.colormap.WorldColors;

public abstract class OreTile extends Tile {

    public OreTile(MaewilEngine maewilEngine, String id, Item drop) {
        super(maewilEngine, id, true, TileType.STONE);
        this.setMapColor(WorldColors.STONE);
        this.setDrop(drop.newCopy());
    }
}
