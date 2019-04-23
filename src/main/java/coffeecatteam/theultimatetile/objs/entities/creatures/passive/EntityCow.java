package coffeecatteam.theultimatetile.objs.entities.creatures.passive;

import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.objs.entities.creatures.EntityPassive;
import coffeecatteam.theultimatetile.objs.items.Items;

public class EntityCow extends EntityPassive {

    public EntityCow(TutEngine tutEngine) {
        super(tutEngine, "cow");
        this.drop = Items.RAW_STEAK;
    }

    @Override
    public Entity newCopy() {
        return super.newCopy(new EntityCow(tutEngine));
    }
}
