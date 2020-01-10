package io.github.vampirestudios.tdg.objs.entities.statics.nature;

import coffeecatteam.coffeecatutils.position.AABB;
import io.github.vampirestudios.tdg.objs.entities.Entity;
import io.github.vampirestudios.tdg.objs.entities.statics.NatureEntity;
import io.github.vampirestudios.tdg.objs.items.Items;
import io.github.vampirestudios.tdg.start.TutEngine;

public class BushEntity extends NatureEntity {

    private RockEntity.RockType type;

    public BushEntity(TutEngine tutEngine, RockEntity.RockType type) {
        super(tutEngine, "bush", type.getWidth(), type.getHeight(), HitType.BUSH);
        setCurrentTexture(type.getId());
        this.type = type;
        this.isCollidable = false;

        drops.add(Items.LEAF);
        drops.add(Items.STICK);
    }

    @Override
    public String getUnlocalizedName() {
        return getId() + "_" + this.type.getId();
    }

    @Override
    public AABB getTileBounds() {
        return new AABB(width / 4f - (width / 4f) / 2, height / 2f + height / 3f, width / 4 + width / 2, height / 3);
    }

    @Override
    public Entity newCopy() {
        return super.newCopy(new BushEntity(tutEngine, type));
    }
}
