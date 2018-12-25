package coffeecatteam.theultimatetile.game.entities.statics.nature;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.entities.Entity;
import coffeecatteam.theultimatetile.game.entities.statics.EntityNature;
import coffeecatteam.theultimatetile.game.inventory.items.Item;
import coffeecatteam.theultimatetile.gfx.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EntityCrop extends EntityNature {

    public EntityCrop(Engine engine, String id, BufferedImage texture, Item drop) {
        super(engine, id, texture, Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT, EntityHitType.BUSH);
        isCollidable = false;

        drops.add(drop);
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(Assets.CROP_GROUND, this.renderX, this.renderY, width, height, null);
        super.render(g);
    }
}
