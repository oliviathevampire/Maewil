package coffeecatteam.theultimatetile.objs.entities.creatures.undead;

import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.objs.entities.creatures.EntityUndead;
import coffeecatteam.theultimatetile.objs.items.Items;
import coffeecatteam.theultimatetile.start.TutEngine;

public class ZombieEntity extends EntityUndead {

    public ZombieEntity(TutEngine tutEngine) {
        super(tutEngine, "zombie");
        this.drop = Items.ROTTEN_FLESH;
    }

    @Override
    public Entity newCopy() {
        return super.newCopy(new ZombieEntity(tutEngine));
    }
}
