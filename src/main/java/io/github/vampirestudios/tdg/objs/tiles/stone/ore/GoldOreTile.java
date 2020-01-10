package io.github.vampirestudios.tdg.objs.tiles.stone.ore;

import io.github.vampirestudios.tdg.objs.items.Items;
import io.github.vampirestudios.tdg.objs.tiles.Tile;
import io.github.vampirestudios.tdg.start.TutEngine;

/**
 * @author CoffeeCatRailway
 * Created: 22/12/2018
 */
public class GoldOreTile extends OreTile {

    public GoldOreTile(TutEngine tutEngine) {
        super(tutEngine, "gold_ore", Items.GOLD_INGOT);
    }

    @Override
    public Tile newCopy() {
        return super.newCopy(new GoldOreTile(tutEngine));
    }
}
