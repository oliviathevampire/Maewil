package coffeecatteam.theultimatetile.entities.statics;

import coffeecatteam.theultimatetile.entities.Entity;

public abstract class EntityStatic extends Entity {

    public EntityStatic(String id, int width, int height) {
        super(id, width, height);
        currentHealth = 10;
    }
}
