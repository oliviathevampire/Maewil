package coffeecatteam.theultimatetile.objs.entities.creatures.undead;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.objs.entities.creatures.EntityUndead;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.assets.Assets;

public class EntityThing extends EntityUndead {

    public EntityThing(TutEngine tutEngine, String id) {
        super(tutEngine, id);
        this.setMaxHealth(Entity.DEFAULT_HEALTH + Entity.DEFAULT_HEALTH / 2);
        setMaxFollowDistance(250f);

        bounds.x = 0;
        bounds.y = 0;
        bounds.width = width;
        bounds.height = height;
    }

    @Override
    protected void init() {
        animIdle = new Animation(animSpeed, Assets.THING_IDLE);
        animUp = new Animation(animUpDownSpeed, Assets.THING_UP);
        animDown = new Animation(animUpDownSpeed, Assets.THING_DOWN);
        animLeft = new Animation(animSpeed, Assets.THING_LEFT);
        animRight = new Animation(animSpeed, Assets.THING_RIGHT);
    }
}
