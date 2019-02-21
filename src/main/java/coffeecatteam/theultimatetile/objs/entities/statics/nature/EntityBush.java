package coffeecatteam.theultimatetile.objs.entities.statics.nature;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.objs.entities.statics.EntityNature;
import coffeecatteam.theultimatetile.objs.items.Items;
import org.newdawn.slick.Image;

public class EntityBush extends EntityNature {

    public EntityBush(TutEngine tutEngine, String id, Image texture, int width) {
        super(tutEngine, id, texture, width, Entity.DEFAULT_HEIGHT, EntityHitType.BUSH);

        bounds.x = this.width / 4f - (this.width / 4f) / 2;
        bounds.y = height / 2f + height / 3f;
        bounds.width = this.width / 4 + this.width / 2;
        bounds.height = height / 3;

        drops.add(Items.LEAF);
        drops.add(Items.STICK);
    }
}
