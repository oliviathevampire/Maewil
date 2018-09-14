package coffeecatteam.theultimatetile.entities.creatures.passive;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.entities.creatures.EntityPassive;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.inventory.items.Items;

public class EntityPig extends EntityPassive {

    public EntityPig(TheUltimateTile theUltimateTile, String id) {
        super(theUltimateTile, id);
        this.drop = Items.RAW_PORK;
    }

    @Override
    protected void init() {
        bounds.x = 0;
        bounds.y = 28;
        bounds.width = 64;
        bounds.height = 35;

        animIdle = new Animation(animSpeed, Assets.PIG_IDLE);
        animUp = new Animation(animUpDownSpeed, Assets.PIG_UP);
        animDown = new Animation(animUpDownSpeed, Assets.PIG_DOWN);
        animLeft = new Animation(animSpeed, Assets.PIG_LEFT);
        animRight = new Animation(animSpeed, Assets.PIG_RIGHT);
    }
}