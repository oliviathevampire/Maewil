package io.github.vampirestudios.tdg.objs.entities.creatures.undead;

import io.github.vampirestudios.tdg.objs.entities.Entity;
import io.github.vampirestudios.tdg.objs.entities.creatures.EntityUndead;
import io.github.vampirestudios.tdg.objs.items.Items;
import io.github.vampirestudios.tdg.start.TutEngine;

public class SkeletonEntity extends EntityUndead {

    public SkeletonEntity(TutEngine tutEngine) {
        super(tutEngine, "skeleton");
        this.drop = Items.BONE;
        this.setMaxHealth(Entity.DEFAULT_HEALTH / 2);
        setMaxFollowDistance(250f);
        this.dmgModifier = 2;
    }

    @Override
    public Entity newCopy() {
        return super.newCopy(new SkeletonEntity(tutEngine));
    }
}
