package io.github.vampirestudios.tdg.objs.tiles.stone;

import io.github.vampirestudios.tdg.start.TutEngine;

/**
 * @author CoffeeCatRailway
 * Created: 3/02/2019
 */
public class AndesiteTile extends StoneTile {

    public AndesiteTile(TutEngine tutEngine) {
        super(tutEngine, "andesite", true);
    }

    @Override
    public AndesiteTile newCopy() {
        return super.newCopy(new AndesiteTile(tutEngine));
    }
}
