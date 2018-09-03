package coffeecatteam.theultimatetile.entities.creatures.undead;

import coffeecatteam.theultimatetile.entities.Entity;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.inventory.items.Items;

public class EntitySkeleton extends EntityUndead {

    public EntitySkeleton(String id) {
        super(id);
        this.drop = Items.BONE;
        this.setMaxHealth(Entity.DEFAULT_HEALTH / 2);
        maxDistance = 400f;
        this.dmgModifier = 2;
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
