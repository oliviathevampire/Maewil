package coffeecatteam.theultimatetile.objs.entities.creatures.undead;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.objs.entities.creatures.EntityUndead;
import coffeecatteam.theultimatetile.objs.items.Items;

public class EntityZombie extends EntityUndead {

    public EntityZombie(TutEngine tutEngine) {
        super(tutEngine, "zombie");
        this.drop = Items.ROTTEN_FLESH;
    }

    @Override
    public Entity newCopy() {
        return super.newCopy(new EntityZombie(tutEngine));
    }
}
