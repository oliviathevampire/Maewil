package coffeecatteam.theultimatetile.objs.tiles.stone.ore;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.objs.items.Items;
import coffeecatteam.theultimatetile.objs.tiles.Tile;

/**
 * @author CoffeeCatRailway
 * Created: 22/12/2018
 */
public class TileIronOre extends TileOre {

    public TileIronOre(TutEngine tutEngine) {
        super(tutEngine, "iron_ore", Items.IRON_INGOT);
    }

    @Override
    public Tile newCopy() {
        return super.newCopy(new TileIronOre(tutEngine));
    }
}
