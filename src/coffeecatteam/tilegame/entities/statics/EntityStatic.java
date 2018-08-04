package coffeecatteam.tilegame.entities.statics;

import coffeecatteam.tilegame.Handler;
import coffeecatteam.tilegame.entities.Entity;

import java.awt.*;

public abstract class EntityStatic extends Entity {

    public EntityStatic(Handler handler, float x, float y, int width, int height) {
        super(handler, x, y, width, height);
        health = 10;
    }
}
