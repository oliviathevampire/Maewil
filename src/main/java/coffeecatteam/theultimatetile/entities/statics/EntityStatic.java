package coffeecatteam.theultimatetile.entities.statics;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.entities.Entity;

public abstract class EntityStatic extends Entity {

    public EntityStatic(TutEngine tutEngine, String id, int width, int height, EntityHitType entityHitType) {
        super(tutEngine, id, width, height, entityHitType);
        currentHealth = 10;
    }
}
