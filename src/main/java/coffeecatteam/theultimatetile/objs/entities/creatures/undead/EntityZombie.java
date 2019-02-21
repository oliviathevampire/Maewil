package coffeecatteam.theultimatetile.objs.entities.creatures.undead;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.objs.entities.creatures.EntityUndead;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.objs.items.Items;

public class EntityZombie extends EntityUndead {

    public EntityZombie(TutEngine tutEngine, String id) {
        super(tutEngine, id);
        this.drop = Items.ROTTEN_FLESH;
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
