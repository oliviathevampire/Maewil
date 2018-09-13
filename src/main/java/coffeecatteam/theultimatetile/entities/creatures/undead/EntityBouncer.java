package coffeecatteam.theultimatetile.entities.creatures.undead;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.entities.creatures.EntityUndead;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.inventory.items.Items;

public class EntityBouncer extends EntityUndead {

    public EntityBouncer(TheUltimateTile theUltimateTile, String id) {
        super(theUltimateTile, id);
        this.drop = Items.BOUNCY_BALL;
    }

    @Override
    protected void init() {
        bounds.x = 13;
        bounds.y = 28;
        bounds.width = 34;
        bounds.height = 35;

        animIdle = new Animation(animSpeed, Assets.BOUNCER_IDLE);
        animUp = new Animation(animUpDownSpeed, Assets.BOUNCER_UP);
        animDown = new Animation(animUpDownSpeed, Assets.BOUNCER_DOWN);
        animLeft = new Animation(animSpeed, Assets.BOUNCER_LEFT);
        animRight = new Animation(animSpeed, Assets.BOUNCER_RIGHT);
    }
}
