package io.github.vampirestudios.tdg.objs.entities.ai;

import io.github.vampirestudios.tdg.objs.entities.Entity;
import io.github.vampirestudios.tdg.objs.entities.creatures.LivingEntity;
import io.github.vampirestudios.tdg.start.TutEngine;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public abstract class Goal {

    protected LivingEntity entity;
    protected TutEngine tutEngine;

    public Goal(TutEngine tutEngine, LivingEntity entity) {
        this.tutEngine = tutEngine;
        this.entity = entity;
    }

    public abstract boolean update(GameContainer container, StateBasedGame game, int delta);

    public LivingEntity getEntity() {
        return entity;
    }

    public void setEntity(LivingEntity entity) {
        this.entity = entity;
    }

    protected float getDistance(Entity from, Entity to) {
        float x = to.getX() - from.getX();
        float y = to.getY() - from.getY();

        return (float) Math.sqrt(x * x + y * y);
    }
}
