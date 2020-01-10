package io.github.vampirestudios.tdg.objs.entities.creatures.undead;

import io.github.vampirestudios.tdg.objs.entities.Entity;
import io.github.vampirestudios.tdg.objs.entities.creatures.EntityUndead;
import io.github.vampirestudios.tdg.objs.items.Items;
import io.github.vampirestudios.tdg.start.TutEngine;

public class ZombieEntity extends EntityUndead {

    public ZombieEntity(TutEngine tutEngine) {
        super(tutEngine, "zombie");
        this.drop = Items.ROTTEN_FLESH;
    }

    @Override
    public Entity newCopy() {
        return super.newCopy(new ZombieEntity(tutEngine));
    }
}
