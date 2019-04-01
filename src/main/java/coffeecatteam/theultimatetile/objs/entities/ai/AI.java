package coffeecatteam.theultimatetile.objs.entities.ai;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.objs.entities.creatures.EntityCreature;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public abstract class AI {

    protected EntityCreature entity;
    protected TutEngine tutEngine;

    public AI(TutEngine tutEngine, EntityCreature entity) {
        this.tutEngine = tutEngine;
        this.entity = entity;
    }

    public abstract boolean update(GameContainer container, StateBasedGame game, int delta);

    public EntityCreature getEntity() {
        return entity;
    }

    public void setEntity(EntityCreature entity) {
        this.entity = entity;
    }

    protected float getDistance(Entity from, Entity to) {
        float x = to.getX() - from.getX();
        float y = to.getY() - from.getY();

        return (float) Math.sqrt(x * x + y * y);
    }
}
