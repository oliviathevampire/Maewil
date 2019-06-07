package coffeecatteam.theultimatetile.objs.entities.creatures.undead;

import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.objs.entities.creatures.EntityUndead;
import coffeecatteam.theultimatetile.start.TutEngine;

public class ThingEntity extends EntityUndead {

    public ThingEntity(TutEngine tutEngine) {
        super(tutEngine, "thing");
        this.setMaxHealth(Entity.DEFAULT_HEALTH + Entity.DEFAULT_HEALTH / 2);
        setMaxFollowDistance(250f);
    }

    @Override
    public Entity newCopy() {
        return super.newCopy(new ThingEntity(tutEngine));
    }
}
