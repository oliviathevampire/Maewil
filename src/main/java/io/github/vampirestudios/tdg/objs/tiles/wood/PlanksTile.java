package io.github.vampirestudios.tdg.objs.tiles.wood;

import io.github.vampirestudios.tdg.objs.items.Items;
import io.github.vampirestudios.tdg.objs.tiles.Tile;
import io.github.vampirestudios.tdg.start.MaewilEngine;

public class PlanksTile extends WoodTile {

    public PlanksTile(MaewilEngine maewilEngine) {
        super(maewilEngine, "planks");
        this.setDrop(Items.WOOD);
    }

    @Override
    public Tile newCopy() {
        return super.newCopy(new PlanksTile(maewilEngine));
    }
}
