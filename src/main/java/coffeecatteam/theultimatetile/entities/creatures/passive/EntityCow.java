package coffeecatteam.theultimatetile.entities.creatures.passive;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.entities.creatures.EntityPassive;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.inventory.items.Items;

public class EntityCow extends EntityPassive {

    public EntityCow(TheUltimateTile theUltimateTile, String id) {
        super(theUltimateTile, id);
        this.drop = Items.RAW_STEAK;
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
