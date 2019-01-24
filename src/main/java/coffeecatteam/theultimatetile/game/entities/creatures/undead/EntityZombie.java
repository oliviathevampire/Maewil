package coffeecatteam.theultimatetile.game.entities.creatures.undead;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.entities.creatures.EntityUndead;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.manager.ItemManager;

public class EntityZombie extends EntityUndead {

    public EntityZombie(Engine engine, String id) {
        super(engine, id);
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
