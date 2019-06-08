package coffeecatteam.theultimatetile.objs.tiles.stone.ore;

import coffeecatteam.theultimatetile.objs.items.Items;
import coffeecatteam.theultimatetile.objs.tiles.Tile;
import coffeecatteam.theultimatetile.start.TutEngine;

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
