package coffeecatteam.theultimatetile.objs.entities.statics.nature;

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

        bounds.x = this.width / 4f - (this.width / 4f) / 2;
        bounds.y = height / 2f + height / 3f;
        bounds.width = this.width / 4 + this.width / 2;
        bounds.height = height / 3;

        drops.add(Items.LEAF);
        drops.add(Items.STICK);
    }

    @Override
    public Entity newCopy() {
        return super.newCopy(new EntityRock(tutEngine, type));
    }
}
