package io.github.vampirestudios.tdg.objs.tiles.stone.ore;

import io.github.vampirestudios.tdg.objs.items.Items;
import io.github.vampirestudios.tdg.objs.tiles.Tile;
import io.github.vampirestudios.tdg.start.MaewilEngine;

/**
 * @author CoffeeCatRailway
 * Created: 22/12/2018
 */
public class AmethystOreTile extends OreTile {

    public AmethystOreTile(MaewilEngine maewilEngine) {
        super(maewilEngine, "amethyst_ore", Items.AMETHYST);
    }

    @Override
    public Tile newCopy() {
        return super.newCopy(new AmethystOreTile(maewilEngine));
    }
}
