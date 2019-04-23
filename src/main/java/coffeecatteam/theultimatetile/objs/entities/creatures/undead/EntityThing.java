package coffeecatteam.theultimatetile.objs.entities.creatures.undead;

import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.objs.entities.creatures.EntityUndead;

public class EntityThing extends EntityUndead {

    public EntityThing(TutEngine tutEngine) {
        super(tutEngine, "thing");
        this.setMaxHealth(Entity.DEFAULT_HEALTH + Entity.DEFAULT_HEALTH / 2);
        setMaxFollowDistance(250f);
    }

    @Override
    public Entity newCopy() {
        return super.newCopy(new EntityThing(tutEngine));
    }
}
