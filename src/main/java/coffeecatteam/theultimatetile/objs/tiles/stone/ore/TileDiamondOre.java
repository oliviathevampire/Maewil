package coffeecatteam.theultimatetile.objs.tiles.stone.ore;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.objs.items.Items;
import coffeecatteam.theultimatetile.objs.tiles.Tile;

/**
 * @author CoffeeCatRailway
 * Created: 22/12/2018
 */
public class TileDiamondOre extends TileOre {

    public TileDiamondOre(TutEngine tutEngine) {
        super(tutEngine, "diamond_ore", Items.DIAMOND);
    }

    @Override
    public Tile newCopy() {
        return super.newCopy(new TileDiamondOre(tutEngine));
    }
}
