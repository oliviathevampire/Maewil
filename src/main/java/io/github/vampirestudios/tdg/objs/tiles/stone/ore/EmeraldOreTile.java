package io.github.vampirestudios.tdg.objs.tiles.stone.ore;

import io.github.vampirestudios.tdg.objs.items.Items;
import io.github.vampirestudios.tdg.objs.tiles.Tile;
import io.github.vampirestudios.tdg.start.MaewilEngine;

/**
 * @author CoffeeCatRailway
 * Created: 22/12/2018
 */
public class EmeraldOreTile extends OreTile {

    public EmeraldOreTile(MaewilEngine maewilEngine) {
        super(maewilEngine, "diamond_ore", Items.DIAMOND);
    }

    @Override
    public Tile newCopy() {
        return super.newCopy(new EmeraldOreTile(maewilEngine));
    }
}
