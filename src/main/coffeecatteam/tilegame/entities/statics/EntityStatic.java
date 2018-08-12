package coffeecatteam.tilegame.entities.statics;

import coffeecatteam.tilegame.Handler;
import coffeecatteam.tilegame.entities.Entity;

public abstract class EntityStatic extends Entity {

    public EntityStatic(Handler handler, String id, int width, int height) {
        super(handler, id, width, height);
        health = 10;
    }
}
