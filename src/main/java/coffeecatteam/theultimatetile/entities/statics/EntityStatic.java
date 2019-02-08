package coffeecatteam.theultimatetile.game.entities.statics;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.game.entities.Entity;

public abstract class EntityStatic extends Entity {

    public EntityStatic(TutEngine tutEngine, String id, int width, int height, EntityHitType entityHitType) {
        super(tutEngine, id, width, height, entityHitType);
        currentHealth = 10;
    }
}
