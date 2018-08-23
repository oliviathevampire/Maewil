package coffeecatteam.theultimatetile.entities.creatures;

import coffeecatteam.theultimatetile.Handler;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.items.Items;

public class EntityBouncer extends EntityUndead {

    public EntityBouncer(Handler handler, String id) {
        super(handler, id);
        this.drop = Items.BOUNCY_BALL;
    }

    @Override
    protected void init() {
        bounds.x = 13;
        bounds.y = 28;
        bounds.width = 34;
        bounds.height = 35;

        animIdle = new Animation(speed, Assets.BOUNCER_IDLE);
        animUp = new Animation(upDownSpeed, Assets.BOUNCER_UP);
        animDown = new Animation(upDownSpeed, Assets.BOUNCER_DOWN);
        animLeft = new Animation(speed, Assets.BOUNCER_LEFT);
        animRight = new Animation(speed, Assets.BOUNCER_RIGHT);
    }
}
