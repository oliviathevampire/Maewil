package io.github.vampirestudios.tdg.objs.tiles.wood;

import io.github.vampirestudios.tdg.objs.items.Items;
import io.github.vampirestudios.tdg.objs.tiles.Tile;
import io.github.vampirestudios.tdg.start.MaewilEngine;

public class LogTile extends WoodTile {

    public LogTile(MaewilEngine maewilEngine) {
        super(maewilEngine, "log");
        this.setDrop(Items.BARK);
    }

    @Override
    public Tile newCopy() {
        return super.newCopy(new LogTile(maewilEngine));
    }
}
