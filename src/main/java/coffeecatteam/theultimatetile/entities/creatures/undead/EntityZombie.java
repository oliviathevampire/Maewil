package coffeecatteam.theultimatetile.entities.creatures.undead;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.entities.creatures.EntityUndead;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.manager.ItemManager;

public class EntityZombie extends EntityUndead {

    public EntityZombie(TutEngine tutEngine, String id) {
        super(tutEngine, id);
        this.drop = ItemManager.ROTTEN_FLESH;
    }

    @Override
    protected void init() {
        animIdle = new Animation(animSpeed, Assets.ZOMBIE_IDLE);
        animUp = new Animation(animUpDownSpeed, Assets.ZOMBIE_UP);
        animDown = new Animation(animUpDownSpeed, Assets.ZOMBIE_DOWN);
        animLeft = new Animation(animSpeed, Assets.ZOMBIE_LEFT);
        animRight = new Animation(animSpeed, Assets.ZOMBIE_RIGHT);
    }
}
