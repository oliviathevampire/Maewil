package io.github.vampirestudios.tdg.objs.tiles.stone.ore;

import io.github.vampirestudios.tdg.objs.items.Items;
import io.github.vampirestudios.tdg.objs.tiles.Tile;
import io.github.vampirestudios.tdg.start.TutEngine;

/**
 * @author CoffeeCatRailway
 * Created: 22/12/2018
 */
public class IronOreTile extends OreTile {

    public IronOreTile(TutEngine tutEngine) {
        super(tutEngine, "iron_ore", Items.IRON_INGOT);
    }

    @Override
    public Tile newCopy() {
        return super.newCopy(new IronOreTile(tutEngine));
    }
}
