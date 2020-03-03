package io.github.vampirestudios.tdg.objs.tiles.stone.ore;

import io.github.vampirestudios.tdg.objs.items.Items;
import io.github.vampirestudios.tdg.objs.tiles.Tile;
import io.github.vampirestudios.tdg.start.MaewilEngine;

public class EmeraldOreTile extends OreTile {

    public EmeraldOreTile(MaewilEngine maewilEngine) {
        super(maewilEngine, "emerald_ore", Items.EMERALD);
    }

    @Override
    public Tile newCopy() {
        return super.newCopy(new EmeraldOreTile(maewilEngine));
    }

}
