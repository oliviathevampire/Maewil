package io.github.vampirestudios.tdg.objs.tiles.wood;

import io.github.vampirestudios.tdg.objs.items.Items;
import io.github.vampirestudios.tdg.objs.tiles.Tile;
import io.github.vampirestudios.tdg.start.TutEngine;

/**
 * @author CoffeeCatRailway
 * Created: 29/03/2019
 */
public class LogTile extends WoodTile {

    public LogTile(TutEngine tutEngine) {
        super(tutEngine, "log");
        this.setDrop(Items.BARK);
    }

    @Override
    public Tile newCopy() {
        return super.newCopy(new LogTile(tutEngine));
    }
}
