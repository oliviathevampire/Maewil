package coffeecatteam.theultimatetile.objs.tiles.stone.ore;

import coffeecatteam.theultimatetile.objs.items.Items;
import coffeecatteam.theultimatetile.objs.tiles.Tile;
import coffeecatteam.theultimatetile.start.TutEngine;

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
