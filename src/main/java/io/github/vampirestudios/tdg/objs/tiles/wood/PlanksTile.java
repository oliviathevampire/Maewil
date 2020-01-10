package io.github.vampirestudios.tdg.objs.tiles.wood;

import io.github.vampirestudios.tdg.objs.items.Items;
import io.github.vampirestudios.tdg.objs.tiles.Tile;
import io.github.vampirestudios.tdg.start.TutEngine;

/**
 * @author CoffeeCatRailway
 * Created: 29/03/2019
 */
public class PlanksTile extends WoodTile {

    public PlanksTile(TutEngine tutEngine) {
        super(tutEngine, "planks");
        this.setDrop(Items.WOOD);
    }

    @Override
    public Tile newCopy() {
        return super.newCopy(new PlanksTile(tutEngine));
    }
}
