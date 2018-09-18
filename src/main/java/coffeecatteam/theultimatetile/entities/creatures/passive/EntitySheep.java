package coffeecatteam.theultimatetile.entities.creatures.passive;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.entities.ai.AIFollowFlee;
import coffeecatteam.theultimatetile.entities.creatures.EntityPassive;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.inventory.items.Items;

public class EntitySheep extends EntityPassive {

    private AIFollowFlee aiFollowFlee;

    public EntitySheep(TheUltimateTile theUltimateTile, String id) {
        super(theUltimateTile, id);
        this.drop = Items.WOOL_BUNDLE;
        aiFollowFlee = new AIFollowFlee(this, theUltimateTile.getEntityManager().getPlayer(), 100f, 3.5f).setFlee();
    }

    @Override
    public void tick() {
        xMove = 0;
        yMove = 0;

        // Movement
        if (theUltimateTile.getEntityManager().getPlayer().isActive()) {
            if (hasDataTag("FLEE_PLAYER")) {
                if (!aiFollowFlee.tick())
                    aiWander.tick();
            } else
                aiWander.tick();
        }
        move();
    }

    @Override
    protected void init() {
        bounds.x = 0;
        bounds.y = 28;
        bounds.width = 64;
        bounds.height = 35;

        animIdle = new Animation(animSpeed, Assets.SHEEP_IDLE);
        animUp = new Animation(animUpDownSpeed, Assets.SHEEP_UP);
        animDown = new Animation(animUpDownSpeed, Assets.SHEEP_DOWN);
        animLeft = new Animation(animSpeed, Assets.SHEEP_LEFT);
        animRight = new Animation(animSpeed, Assets.SHEEP_RIGHT);
    }
}
