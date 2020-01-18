package io.github.vampirestudios.tdg.objs.tiles.wood;

import io.github.vampirestudios.tdg.objs.tiles.Tile;
import io.github.vampirestudios.tdg.start.MaewilEngine;

/**
 * @author CoffeeCatRailway
 * Created: 29/03/2019
 */
public class ChestTile extends WoodTile {

    public ChestTile(MaewilEngine maewilEngine) {
        super(maewilEngine, "chest");
    }

    @Override
    public Tile newCopy() {
        return super.newCopy(new ChestTile(maewilEngine));
    }
}
