package coffeecatteam.theultimatetile.objs.tiles.stone.ore;

import coffeecatteam.theultimatetile.objs.items.Items;
import coffeecatteam.theultimatetile.objs.tiles.Tile;
import coffeecatteam.theultimatetile.start.TutEngine;

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
