package coffeecatteam.theultimatetile.game.entities.statics.nature;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.entities.Entity;
import coffeecatteam.theultimatetile.game.entities.statics.EntityNature;
import coffeecatteam.theultimatetile.game.inventory.items.Item;
import coffeecatteam.theultimatetile.gfx.assets.Assets;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class EntityCrop extends EntityNature {

    public EntityCrop(Engine engine, String id, Image texture, Item drop) {
        super(engine, id, texture, Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT, EntityHitType.BUSH);
        isCollidable = false;

        drops.add(drop);
    }

    @Override
    public void render(Graphics g) {
        Assets.CROP_GROUND.draw(this.renderX, this.renderY, width, height);
        super.render(g);
    }
}
