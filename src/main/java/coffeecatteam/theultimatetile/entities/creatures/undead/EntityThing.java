package coffeecatteam.theultimatetile.entities.creatures.undead;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.entities.Entity;
import coffeecatteam.theultimatetile.entities.creatures.EntityUndead;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.Assets;

public class EntityThing extends EntityUndead {

    public EntityThing(TheUltimateTile theUltimateTile, String id) {
        super(theUltimateTile, id);
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
