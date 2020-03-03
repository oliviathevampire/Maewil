package io.github.vampirestudios.tdg.objs.tiles.stone.ore;

import io.github.vampirestudios.tdg.objs.items.Items;
import io.github.vampirestudios.tdg.objs.tiles.Tile;
import io.github.vampirestudios.tdg.start.MaewilEngine;

public class CoalOreTile extends OreTile {

    public CoalOreTile(MaewilEngine maewilEngine) {
        super(maewilEngine, "coal_ore", Items.COAL);
    }

    @Override
    public Tile newCopy() {
        return super.newCopy(new CoalOreTile(maewilEngine));
    }
}
