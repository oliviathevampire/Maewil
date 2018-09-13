package coffeecatteam.theultimatetile.entities.ai;

import coffeecatteam.theultimatetile.entities.creatures.EntityCreature;

public abstract class AI {

    protected EntityCreature entity;

    public AI(EntityCreature entity) {
        this.entity = entity;
    }

    public abstract boolean tick();

    public EntityCreature getEntity() {
        return entity;
    }

    public void setEntity(EntityCreature entity) {
        this.entity = entity;
    }
}
