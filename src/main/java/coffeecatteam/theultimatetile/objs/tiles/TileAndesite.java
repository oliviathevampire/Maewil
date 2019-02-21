package coffeecatteam.theultimatetile.objs.tiles;

import coffeecatteam.theultimatetile.TutEngine;

/**
 * @author CoffeeCatRailway
 * Created: 3/02/2019
 */
public class TileAndesite extends TileStone {

    public TileAndesite(TutEngine tutEngine) {
        super(tutEngine, "andesite", true);
    }

    @Override
    public TileAndesite newTile() {
        return super.newTile(new TileAndesite(tutEngine));
    }
}
