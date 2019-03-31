package coffeecatteam.theultimatetile.objs.tiles.stone.ore;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.objs.items.Items;
import coffeecatteam.theultimatetile.objs.tiles.Tile;

/**
 * @author CoffeeCatRailway
 * Created: 22/12/2018
 */
public class TileGoldOre extends TileOre {

    public TileGoldOre(TutEngine tutEngine) {
        super(tutEngine, "gold_ore", Items.GOLD_INGOT);
    }

    @Override
    public Tile newCopy() {
        return super.newCopy(new TileGoldOre(tutEngine));
    }
}
