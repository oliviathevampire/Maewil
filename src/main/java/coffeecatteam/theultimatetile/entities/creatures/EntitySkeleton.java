package coffeecatteam.theultimatetile.entities.creatures;

import coffeecatteam.theultimatetile.Handler;
import coffeecatteam.theultimatetile.entities.Entity;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.items.Items;

public class EntitySkeleton extends EntityUndead {

    public EntitySkeleton(Handler handler, String id) {
        super(handler, id);
        this.drop = Items.BONE;
        this.setMaxHealth(Entity.DEFAULT_HEALTH / 2);
    }

    @Override
    protected void init() {
        bounds.x = 13;
        bounds.y = 28;
        bounds.width = 34;
        bounds.height = 35;

        animIdle = new Animation(speed, Assets.SKELETON_IDLE);
        animUp = new Animation(upDownSpeed, Assets.SKELETON_UP);
        animDown = new Animation(upDownSpeed, Assets.SKELETON_DOWN);
        animLeft = new Animation(speed, Assets.SKELETON_LEFT);
        animRight = new Animation(speed, Assets.SKELETON_RIGHT);
    }
}