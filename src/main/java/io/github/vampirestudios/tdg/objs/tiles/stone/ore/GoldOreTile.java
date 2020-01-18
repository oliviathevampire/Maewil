package io.github.vampirestudios.tdg.objs.tiles.stone.ore;

import io.github.vampirestudios.tdg.objs.items.Items;
import io.github.vampirestudios.tdg.objs.tiles.Tile;
import io.github.vampirestudios.tdg.start.MaewilEngine;

/**
 * @author CoffeeCatRailway
 * Created: 22/12/2018
 */
public class GoldOreTile extends OreTile {

    public GoldOreTile(MaewilEngine maewilEngine) {
        super(maewilEngine, "gold_ore", Items.GOLD_INGOT);
    }

    @Override
    public Tile newCopy() {
        return super.newCopy(new GoldOreTile(maewilEngine));
    }
}
