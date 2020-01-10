package io.github.vampirestudios.tdg.objs.entities.statics;

import io.github.vampirestudios.tdg.objs.entities.Entity;
import io.github.vampirestudios.tdg.start.TutEngine;

public abstract class StaticEntity extends Entity {

    public StaticEntity(TutEngine tutEngine, String id, int width, int height, HitType hitType) {
        super(tutEngine, id, width, height, hitType);
        currentHealth = 10;
    }
}
