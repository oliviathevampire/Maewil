package io.github.vampirestudios.tdg.objs.entities.creatures.passive;

import io.github.vampirestudios.tdg.objs.entities.Entity;
import io.github.vampirestudios.tdg.objs.entities.creatures.EntityPassive;
import io.github.vampirestudios.tdg.start.MaewilEngine;

public class FoxEntity extends EntityPassive {

    public FoxEntity(MaewilEngine maewilEngine) {
        super(maewilEngine, "fox");
    }

    @Override
    public Entity newCopy() {
        return super.newCopy(new FoxEntity(maewilEngine));
    }
}
