package coffeecatteam.theultimatetile.objs.tiles.stone.ore;

import coffeecatteam.theultimatetile.objs.items.Items;
import coffeecatteam.theultimatetile.objs.tiles.Tile;
import coffeecatteam.theultimatetile.start.TutEngine;

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
