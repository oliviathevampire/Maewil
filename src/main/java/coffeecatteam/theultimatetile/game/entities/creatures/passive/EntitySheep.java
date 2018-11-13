package coffeecatteam.theultimatetile.game.entities.creatures.passive;

import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.entities.ai.AIFollowFlee;
import coffeecatteam.theultimatetile.game.entities.creatures.EntityPassive;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.manager.ItemManager;

public class EntitySheep extends EntityPassive {

    private AIFollowFlee aiFollowFlee;

    public EntitySheep(GameEngine gameEngine, String id) {
        super(gameEngine, id);
        this.drop = ItemManager.WOOL_BUNDLE;
        aiFollowFlee = new AIFollowFlee(this, gameEngine.getEntityManager().getPlayer(), 100f, 3.5f).setFlee();
    }

    @Override
    public void tick() {
        xMove = 0;
        yMove = 0;

        // Movement
        if (gameEngine.getEntityManager().getPlayer().isActive()) {
            if (Boolean.valueOf(TAGS.get("fleePlayer"))) {
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
