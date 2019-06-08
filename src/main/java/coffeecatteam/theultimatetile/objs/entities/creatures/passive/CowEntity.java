package coffeecatteam.theultimatetile.objs.entities.creatures.passive;

import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.objs.entities.creatures.EntityPassive;
import coffeecatteam.theultimatetile.objs.items.Items;
import coffeecatteam.theultimatetile.start.TutEngine;

public class CowEntity extends EntityPassive {

    public CowEntity(TutEngine tutEngine) {
        super(tutEngine, "cow");
        this.drop = Items.RAW_STEAK;
    }

    @Override
    public Entity newCopy() {
        return super.newCopy(new CowEntity(tutEngine));
    }
}
