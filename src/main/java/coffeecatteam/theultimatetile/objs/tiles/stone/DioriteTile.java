package coffeecatteam.theultimatetile.objs.tiles.stone;

import coffeecatteam.theultimatetile.start.TutEngine;

/**
 * @author CoffeeCatRailway
 * Created: 3/02/2019
 */
public class DioriteTile extends StoneTile {

    public DioriteTile(TutEngine tutEngine) {
        super(tutEngine, "diorite", true);
    }

    @Override
    public DioriteTile newCopy() {
        return super.newCopy(new DioriteTile(tutEngine));
    }
}
