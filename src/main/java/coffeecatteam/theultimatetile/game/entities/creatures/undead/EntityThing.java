package coffeecatteam.theultimatetile.game.entities.creatures.undead;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.entities.Entity;
import coffeecatteam.theultimatetile.game.entities.creatures.EntityUndead;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.assets.Assets;

public class EntityThing extends EntityUndead {

    public EntityThing(Engine engine, String id) {
        super(engine, id);
        this.setMaxHealth(Entity.DEFAULT_HEALTH + Entity.DEFAULT_HEALTH / 2);
        setMaxFollowDistance(250f);
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
