package io.github.vampirestudios.tdg.objs.tiles.stone.ore;

import io.github.vampirestudios.tdg.objs.items.Items;
import io.github.vampirestudios.tdg.objs.tiles.Tile;
import io.github.vampirestudios.tdg.start.MaewilEngine;

/**
 * @author CoffeeCatRailway
 * Created: 22/12/2018
 */
public class IronOreTile extends OreTile {

    public IronOreTile(MaewilEngine maewilEngine) {
        super(maewilEngine, "iron_ore", Items.IRON_INGOT);
    }

    @Override
    public Tile newCopy() {
        return super.newCopy(new IronOreTile(maewilEngine));
    }
}
