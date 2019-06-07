package coffeecatteam.theultimatetile.objs.entities.creatures.passive;

import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.objs.entities.creatures.EntityPassive;
import coffeecatteam.theultimatetile.start.TutEngine;

public class FoxEntity extends EntityPassive {

    public FoxEntity(TutEngine tutEngine) {
        super(tutEngine, "fox");
    }

    @Override
    public Entity newCopy() {
        return super.newCopy(new FoxEntity(tutEngine));
    }
}
