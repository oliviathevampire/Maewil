package coffeecatteam.theultimatetile.entities.statics.nature;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.entities.Entity;
import coffeecatteam.theultimatetile.entities.statics.EntityStatic;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.inventory.items.Item;
import coffeecatteam.theultimatetile.inventory.items.ItemStack;
import coffeecatteam.theultimatetile.utils.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

public class EntityCrop extends EntityStatic {

    private Item drop;

    public EntityCrop(TheUltimateTile theUltimateTile, String id, BufferedImage texture, Item drop) {
        super(theUltimateTile, id, Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT, EntityHitType.BUSH);
        isCollidable = false;

        this.texture = texture;
        this.drop = drop;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.CROP_GROUND, this.renderX, this.renderY, width, height, null);
        super.render(g);
    }

    @Override
    public void die(List<Entity> entities, int index) {
        super.die(entities, index);
        int amt = Utils.getRandomInt(3);
        for (int i = 0; i < amt; i++)
            theUltimateTile.getItemManager().addItem(new ItemStack(drop), x + Utils.getRandomInt(width), y + Utils.getRandomInt(height));
    }
}
