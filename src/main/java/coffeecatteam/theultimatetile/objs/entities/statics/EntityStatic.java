package coffeecatteam.theultimatetile.objs.entities.statics;

import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.objs.entities.Entity;

public abstract class EntityStatic extends Entity {

    public EntityStatic(TutEngine tutEngine, String id, int width, int height, HitType hitType) {
        super(tutEngine, id, width, height, hitType);
        currentHealth = 10;
    }
}
