package io.github.vampirestudios.tdg.objs.entities.creatures.undead;

import io.github.vampirestudios.tdg.objs.entities.Entity;
import io.github.vampirestudios.tdg.objs.entities.creatures.EntityUndead;
import io.github.vampirestudios.tdg.start.TutEngine;

public class ThingEntity extends EntityUndead {

    public ThingEntity(TutEngine tutEngine) {
        super(tutEngine, "thing");
        this.setMaxHealth(Entity.DEFAULT_HEALTH + Entity.DEFAULT_HEALTH / 2);
        setMaxFollowDistance(250f);
    }

    @Override
    public Entity newCopy() {
        return super.newCopy(new ThingEntity(tutEngine));
    }
}
