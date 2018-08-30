package coffeecatteam.theultimatetile.entities.statics;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.entities.Entity;

public abstract class EntityStatic extends Entity {

    public EntityStatic(TheUltimateTile theUltimateTile, String id, int width, int height) {
        super(theUltimateTile, id, width, height);
        currentHealth = 10;
    }
}
