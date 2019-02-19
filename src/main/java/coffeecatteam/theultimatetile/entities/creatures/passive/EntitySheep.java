package coffeecatteam.theultimatetile.entities.creatures.passive;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.entities.ai.AIFollowFlee;
import coffeecatteam.theultimatetile.entities.creatures.EntityPassive;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.manager.ItemManager;
import org.newdawn.slick.GameContainer;

public class EntitySheep extends EntityPassive {

    private AIFollowFlee aiFollowFlee;

    public EntitySheep(TutEngine tutEngine, String id) {
        super(tutEngine, id);
        this.drop = ItemManager.WOOL_BUNDLE;
        aiFollowFlee = new AIFollowFlee(tutEngine, this, tutEngine.getEntityManager().getPlayer(), 100f, 3.5f).setFlee();
    }

    @Override
    public void update(GameContainer container, int delta) {
        xMove = 0;
        yMove = 0;

        // Movement
        if (tutEngine.getEntityManager().getPlayer().isActive()) {
            if (TAGS.hasKey("fleePlayer") && TAGS.getBoolean("fleePlayer")) {
                if (!aiFollowFlee.update(container, delta))
                    aiWander.update(container, delta);
            } else
                aiWander.update(container, delta);
        }
        move();
    }

    @Override
    protected void init() {
        animIdle = new Animation(animSpeed, Assets.SHEEP_IDLE);
        animUp = new Animation(animUpDownSpeed, Assets.SHEEP_UP);
        animDown = new Animation(animUpDownSpeed, Assets.SHEEP_DOWN);
        animLeft = new Animation(animSpeed, Assets.SHEEP_LEFT);
        animRight = new Animation(animSpeed, Assets.SHEEP_RIGHT);
    }
}
