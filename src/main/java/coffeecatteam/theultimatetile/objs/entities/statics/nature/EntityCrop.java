package coffeecatteam.theultimatetile.objs.entities.statics.nature;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.objs.entities.statics.EntityNature;
import coffeecatteam.theultimatetile.objs.items.Item;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class EntityCrop extends EntityNature {

    public EntityCrop(TutEngine tutEngine, String id, Image texture, Item drop) {
        super(tutEngine, id, texture, Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT, EntityHitType.BUSH);
        isCollidable = false;

        drops.add(drop);
    }

    @Override
    public void render(Graphics g) {
        Assets.CROP_GROUND.draw(this.renderX, this.renderY, width, height);
        super.render(g);
    }
}