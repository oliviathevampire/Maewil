package coffeecatteam.theultimatetile.game.entities.statics;

import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.entities.Entity;

public abstract class EntityStatic extends Entity {

    public EntityStatic(GameEngine gameEngine, String id, int width, int height, EntityHitType entityHitType) {
        super(gameEngine, id, width, height, entityHitType);
        currentHealth = 10;
    }
}
