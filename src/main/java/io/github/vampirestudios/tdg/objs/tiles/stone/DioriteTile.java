package io.github.vampirestudios.tdg.objs.tiles.stone;

import io.github.vampirestudios.tdg.start.TutEngine;

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
