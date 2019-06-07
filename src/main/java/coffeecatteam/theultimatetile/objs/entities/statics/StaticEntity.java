package coffeecatteam.theultimatetile.objs.entities.statics;

import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.start.TutEngine;

public abstract class StaticEntity extends Entity {

    public StaticEntity(TutEngine tutEngine, String id, int width, int height, HitType hitType) {
        super(tutEngine, id, width, height, hitType);
        currentHealth = 10;
    }
}
