package coffeecatteam.theultimatetile.entities.statics;

import coffeecatteam.theultimatetile.Handler;
import coffeecatteam.theultimatetile.entities.Entity;

public abstract class EntityStatic extends Entity {

    public EntityStatic(Handler handler, String id, int width, int height) {
        super(handler, id, width, height);
        health = 10;
    }
}
