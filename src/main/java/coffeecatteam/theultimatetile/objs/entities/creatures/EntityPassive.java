package coffeecatteam.theultimatetile.objs.entities.creatures;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.objs.entities.ai.AIWander;
import org.newdawn.slick.GameContainer;

public abstract class EntityPassive extends EntityCreature {

    // AI
    protected AIWander aiWander;

    public EntityPassive(TutEngine tutEngine, String id) {
        super(tutEngine, id, Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT);
        aiWander = new AIWander(tutEngine, this, 1.5f);
        bounds.x = 0;
        bounds.y = height - ((height / 4f) * 3);
        bounds.width = width;
        bounds.height = (height / 4) * 3;
    }

    @Override
    public void update(GameContainer container, int delta) {
        xMove = 0;
        yMove = 0;

        // Movement
        if (tutEngine.getEntityManager().getPlayer().isActive()) {
            aiWander.update(container, delta);
        }
        move();
    }
}
