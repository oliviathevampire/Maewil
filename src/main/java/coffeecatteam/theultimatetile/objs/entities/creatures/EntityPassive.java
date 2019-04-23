package coffeecatteam.theultimatetile.objs.entities.creatures;

import coffeecatteam.coffeecatutils.position.AABB;
import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.objs.entities.ai.AIWander;
import coffeecatteam.theultimatetile.start.TutEngine;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public abstract class EntityPassive extends EntityCreature {

    // AI
    protected AIWander aiWander;

    public EntityPassive(TutEngine tutEngine, String id) {
        super(tutEngine, id, Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT);
        aiWander = new AIWander(tutEngine, this, 1.5f);
    }

    @Override
    public AABB getTileBounds() {
        return new AABB(0, height - ((height / 4f) * 3), width, (height / 4) * 3);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {
        xMove = 0;
        yMove = 0;

        // Movement
        if (tutEngine.getPlayer().isActive())
            aiWander.update(container, game, delta);
        move();
    }
}
