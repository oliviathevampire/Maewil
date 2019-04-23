package coffeecatteam.theultimatetile.objs.tiles.stone.ore;

import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.objs.items.Items;
import coffeecatteam.theultimatetile.objs.tiles.Tile;

/**
 * @author CoffeeCatRailway
 * Created: 22/12/2018
 */
public class TileCoalOre extends TileOre {

    public TileCoalOre(TutEngine tutEngine) {
        super(tutEngine, "coal_ore", Items.COAL);
    }

    @Override
    public Tile newCopy() {
        return super.newCopy(new TileCoalOre(tutEngine));
    }
}
