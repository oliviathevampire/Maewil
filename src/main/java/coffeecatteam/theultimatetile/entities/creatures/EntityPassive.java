package coffeecatteam.theultimatetile.entities.creatures;

import coffeecatteam.theultimatetile.GameEngine;
import coffeecatteam.theultimatetile.entities.Entity;
import coffeecatteam.theultimatetile.entities.ai.AIWander;

public abstract class EntityPassive extends EntityCreature {

    // AI
    protected AIWander aiWander;

    public EntityPassive(GameEngine gameEngine, String id) {
        super(gameEngine, id, Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT);
        aiWander = new AIWander(this, 1.5f);
    }

    @Override
    public void tick() {
        xMove = 0;
        yMove = 0;

        // Movement
        if (gameEngine.getEntityManager().getPlayer().isActive()) {
            aiWander.tick();
        }
        move();
    }
}
