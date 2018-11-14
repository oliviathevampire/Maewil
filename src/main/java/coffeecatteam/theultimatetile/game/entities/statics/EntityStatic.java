package coffeecatteam.theultimatetile.game.entities.statics;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.entities.Entity;

public abstract class EntityStatic extends Entity {

    public EntityStatic(Engine engine, String id, int width, int height, EntityHitType entityHitType) {
        super(engine, id, width, height, entityHitType);
        currentHealth = 10;
    }
}
