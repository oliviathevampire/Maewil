package coffeecatteam.theultimatetile.game.entities.creatures;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.entities.Entity;
import coffeecatteam.theultimatetile.game.entities.ai.AIWander;

public abstract class EntityPassive extends EntityCreature {

    // AI
    protected AIWander aiWander;

    public EntityPassive(Engine engine, String id) {
        super(engine, id, Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT);
        aiWander = new AIWander(this, 1.5f);
    }

    @Override
    public void tick() {
        xMove = 0;
        yMove = 0;

        // Movement
        if (((GameEngine) engine).getEntityManager().getPlayer().isActive()) {
            aiWander.tick();
        }
        move();
    }
}
