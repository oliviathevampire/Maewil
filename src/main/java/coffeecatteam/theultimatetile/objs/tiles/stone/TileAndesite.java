package coffeecatteam.theultimatetile.objs.tiles.stone;

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
    public TileAndesite newCopy() {
        return super.newCopy(new TileAndesite(tutEngine));
    }
}
