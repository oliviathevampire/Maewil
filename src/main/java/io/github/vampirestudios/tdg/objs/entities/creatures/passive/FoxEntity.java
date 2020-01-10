package io.github.vampirestudios.tdg.objs.entities.creatures.passive;

import io.github.vampirestudios.tdg.objs.entities.Entity;
import io.github.vampirestudios.tdg.objs.entities.creatures.EntityPassive;
import io.github.vampirestudios.tdg.start.TutEngine;

public class FoxEntity extends EntityPassive {

    public FoxEntity(TutEngine tutEngine) {
        super(tutEngine, "fox");
    }

    @Override
    public Entity newCopy() {
        return super.newCopy(new FoxEntity(tutEngine));
    }
}
