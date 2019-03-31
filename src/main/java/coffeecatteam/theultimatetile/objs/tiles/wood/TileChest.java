package coffeecatteam.theultimatetile.objs.tiles.wood;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.objs.tiles.Tile;

/**
 * @author CoffeeCatRailway
 * Created: 29/03/2019
 */
public class TileChest extends TileWood {

    public TileChest(TutEngine tutEngine) {
        super(tutEngine, "chest");
    }

    @Override
    public Tile newCopy() {
        return super.newCopy(new TileChest(tutEngine));
    }
}
