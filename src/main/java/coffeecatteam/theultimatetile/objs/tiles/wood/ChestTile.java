package coffeecatteam.theultimatetile.objs.tiles.wood;

import coffeecatteam.theultimatetile.objs.tiles.Tile;
import coffeecatteam.theultimatetile.start.TutEngine;

/**
 * @author CoffeeCatRailway
 * Created: 29/03/2019
 */
public class ChestTile extends WoodTile {

    public ChestTile(TutEngine tutEngine) {
        super(tutEngine, "chest");
    }

    @Override
    public Tile newCopy() {
        return super.newCopy(new ChestTile(tutEngine));
    }
}
