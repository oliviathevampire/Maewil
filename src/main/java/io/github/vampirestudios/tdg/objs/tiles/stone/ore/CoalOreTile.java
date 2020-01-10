package io.github.vampirestudios.tdg.objs.tiles.stone.ore;

import io.github.vampirestudios.tdg.objs.items.Items;
import io.github.vampirestudios.tdg.objs.tiles.Tile;
import io.github.vampirestudios.tdg.start.TutEngine;

/**
 * @author CoffeeCatRailway
 * Created: 22/12/2018
 */
public class CoalOreTile extends OreTile {

    public CoalOreTile(TutEngine tutEngine) {
        super(tutEngine, "coal_ore", Items.COAL);
    }

    @Override
    public Tile newCopy() {
        return super.newCopy(new CoalOreTile(tutEngine));
    }
}
