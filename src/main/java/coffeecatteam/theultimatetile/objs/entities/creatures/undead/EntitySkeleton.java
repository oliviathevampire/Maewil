package coffeecatteam.theultimatetile.objs.entities.creatures.undead;

import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.objs.entities.creatures.EntityUndead;
import coffeecatteam.theultimatetile.objs.items.Items;
import coffeecatteam.theultimatetile.start.TutEngine;

public class EntitySkeleton extends EntityUndead {

    public EntitySkeleton(TutEngine tutEngine) {
        super(tutEngine, "skeleton");
        this.drop = Items.BONE;
        this.setMaxHealth(Entity.DEFAULT_HEALTH / 2);
        setMaxFollowDistance(250f);
        this.dmgModifier = 2;
    }

    @Override
    public Entity newCopy() {
        return super.newCopy(new EntitySkeleton(tutEngine));
    }
}
