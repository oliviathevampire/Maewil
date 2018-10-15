package coffeecatteam.theultimatetile.entities.creatures.undead;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.entities.creatures.EntityUndead;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.manager.ItemManager;

public class EntityZombie extends EntityUndead {

    public EntityZombie(TheUltimateTile theUltimateTile, String id) {
        super(theUltimateTile, id);
        this.drop = ItemManager.ROTTEN_FLESH;
    }

    @Override
    protected void init() {
        bounds.x = 13;
        bounds.y = 28;
        bounds.width = 34;
        bounds.height = 35;

        animIdle = new Animation(animSpeed, Assets.ZOMBIE_IDLE);
        animUp = new Animation(animUpDownSpeed, Assets.ZOMBIE_UP);
        animDown = new Animation(animUpDownSpeed, Assets.ZOMBIE_DOWN);
        animLeft = new Animation(animSpeed, Assets.ZOMBIE_LEFT);
        animRight = new Animation(animSpeed, Assets.ZOMBIE_RIGHT);
    }
}
