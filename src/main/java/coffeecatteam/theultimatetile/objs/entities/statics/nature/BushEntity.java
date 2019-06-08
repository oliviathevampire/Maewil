package coffeecatteam.theultimatetile.objs.entities.statics.nature;

import coffeecatteam.coffeecatutils.position.AABB;
import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.objs.entities.statics.NatureEntity;
import coffeecatteam.theultimatetile.objs.items.Items;
import coffeecatteam.theultimatetile.start.TutEngine;

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
