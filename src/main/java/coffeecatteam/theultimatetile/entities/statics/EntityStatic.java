package coffeecatteam.theultimatetile.entities.statics;

import coffeecatteam.theultimatetile.GameEngine;
import coffeecatteam.theultimatetile.entities.Entity;

public abstract class EntityStatic extends Entity {

    public EntityStatic(GameEngine gameEngine, String id, int width, int height, EntityHitType entityHitType) {
        super(gameEngine, id, width, height, entityHitType);
        currentHealth = 10;
    }
}
