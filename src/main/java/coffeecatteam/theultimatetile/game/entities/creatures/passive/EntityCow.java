package coffeecatteam.theultimatetile.game.entities.creatures.passive;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.entities.creatures.EntityPassive;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.manager.ItemManager;

public class EntityCow extends EntityPassive {

    public EntityCow(Engine engine, String id) {
        super(engine, id);
        this.drop = ItemManager.RAW_STEAK;
    }

    @Override
    protected void init() {
        bounds.x = 0;
        bounds.y = 28;
        bounds.width = 64;
        bounds.height = 35;

        animIdle = new Animation(animSpeed, Assets.COW_IDLE);
        animUp = new Animation(animUpDownSpeed, Assets.COW_UP);
        animDown = new Animation(animUpDownSpeed, Assets.COW_DOWN);
        animLeft = new Animation(animSpeed, Assets.COW_LEFT);
        animRight = new Animation(animSpeed, Assets.COW_RIGHT);
    }
}
