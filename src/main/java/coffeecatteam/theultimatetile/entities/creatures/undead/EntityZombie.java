package coffeecatteam.theultimatetile.entities.creatures.undead;

import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.inventory.items.Items;

public class EntityZombie extends EntityUndead {

    public EntityZombie(String id) {
        super(id);
        this.drop = Items.ROTTEN_FLESH;
    }

    @Override
    protected void init() {
        bounds.x = 13;
        bounds.y = 28;
        bounds.width = 34;
        bounds.height = 35;

        animIdle = new Animation(speed, Assets.ZOMBIE_IDLE);
        animUp = new Animation(upDownSpeed, Assets.ZOMBIE_UP);
        animDown = new Animation(upDownSpeed, Assets.ZOMBIE_DOWN);
        animLeft = new Animation(speed, Assets.ZOMBIE_LEFT);
        animRight = new Animation(speed, Assets.ZOMBIE_RIGHT);
    }
}
