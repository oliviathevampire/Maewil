package io.github.vampirestudios.tdg.objs.tiles.stone.ore;

import io.github.vampirestudios.tdg.objs.items.Items;
import io.github.vampirestudios.tdg.objs.tiles.Tile;
import io.github.vampirestudios.tdg.start.TutEngine;

/**
 * @author CoffeeCatRailway
 * Created: 22/12/2018
 */
public class DiamondOreTile extends OreTile {

    public DiamondOreTile(TutEngine tutEngine) {
        super(tutEngine, "diamond_ore", Items.DIAMOND);
    }

    @Override
    public Tile newCopy() {
        return super.newCopy(new DiamondOreTile(tutEngine));
    }
}
