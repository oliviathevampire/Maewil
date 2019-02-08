package coffeecatteam.theultimatetile.game.entities.ai;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.game.entities.Entity;
import coffeecatteam.theultimatetile.game.entities.creatures.EntityCreature;
import org.newdawn.slick.GameContainer;

public abstract class AI {

    protected EntityCreature entity;
    protected TutEngine tutEngine;

    public AI(TutEngine tutEngine, EntityCreature entity) {
        this.tutEngine = tutEngine;
        this.entity = entity;
    }

    public abstract boolean update(GameContainer container, int delta);

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
