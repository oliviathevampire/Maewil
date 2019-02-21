package coffeecatteam.theultimatetile.objs.tiles;

import coffeecatteam.theultimatetile.TutEngine;

/**
 * @author CoffeeCatRailway
 * Created: 3/02/2019
 */
public class TileDiorite extends TileStone {

    public TileDiorite(TutEngine tutEngine) {
        super(tutEngine, "diorite", true);
    }

    @Override
    public TileDiorite newCopy() {
        return super.newCopy(new TileDiorite(tutEngine));
    }
}
