package coffeecatteam.theultimatetile.entities.creatures;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.entities.Entity;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.Assets;

public class EntityThing extends EntityUndead {

    public EntityThing(TheUltimateTile theUltimateTile, String id) {
        super(theUltimateTile, id);
        this.setMaxHealth(Entity.DEFAULT_HEALTH + Entity.DEFAULT_HEALTH / 2);
        maxDistance = 400f;
    }

    @Override
    protected void init() {
        animIdle = new Animation(speed, Assets.THING_IDLE);
        animUp = new Animation(upDownSpeed, Assets.THING_UP);
        animDown = new Animation(upDownSpeed, Assets.THING_DOWN);
        animLeft = new Animation(speed, Assets.THING_LEFT);
        animRight = new Animation(speed, Assets.THING_RIGHT);
    }
}
