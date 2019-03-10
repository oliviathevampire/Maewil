package coffeecatteam.theultimatetile.objs.entities.statics.nature;

import coffeecatteam.coffeecatutils.position.AABB;
import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.objs.entities.statics.EntityNature;
import coffeecatteam.theultimatetile.objs.items.Items;

public class EntityBush extends EntityNature {

    private EntityRock.RockType type;

    public EntityBush(TutEngine tutEngine, EntityRock.RockType type) {
        super(tutEngine, "bush", type.getWidth(), type.getHeight(), EntityHitType.BUSH);
        setCurrentTexture(type.getId());
        this.type = type;

        drops.add(Items.LEAF);
        drops.add(Items.STICK);
    }

    @Override
    public AABB getTileBounds() {
        return new AABB(width / 4f - (width / 4f) / 2, height / 2f + height / 3f, width / 4 + width / 2, height / 3);
    }

    @Override
    public Entity newCopy() {
        return super.newCopy(new EntityBush(tutEngine, type));
    }
}
