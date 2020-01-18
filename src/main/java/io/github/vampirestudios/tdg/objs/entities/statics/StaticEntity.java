package io.github.vampirestudios.tdg.objs.entities.statics;

import io.github.vampirestudios.tdg.objs.entities.Entity;
import io.github.vampirestudios.tdg.start.MaewilEngine;

public abstract class StaticEntity extends Entity {

    public StaticEntity(MaewilEngine maewilEngine, String id, int width, int height, HitType hitType) {
        super(maewilEngine, id, width, height, hitType);
        currentHealth = 10;
    }
}
