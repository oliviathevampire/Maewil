package coffeecatteam.theultimatetile.objs.entities.statics;

import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.start.TutEngine;

public class EntityUltimateTile extends EntityStatic {

    public EntityUltimateTile(TutEngine tutEngine) {
        super(tutEngine, "ultimate", Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT, HitType.NONE);
        isCollidable = false;
        setCurrentTexture("main");
    }

    @Override
    public Entity newCopy() {
        return super.newCopy(new EntityUltimateTile(tutEngine));
    }
}
