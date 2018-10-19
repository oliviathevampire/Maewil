package coffeecatteam.theultimatetile.entities.statics.nature;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.entities.Entity;
import coffeecatteam.theultimatetile.entities.statics.EntityStatic;
import coffeecatteam.theultimatetile.inventory.items.ItemStack;
import coffeecatteam.theultimatetile.manager.ItemManager;
import coffeecatteam.theultimatetile.utils.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

public class EntityBush extends EntityStatic {

    public EntityBush(TheUltimateTile theUltimateTile, String id, BufferedImage texture, int width) {
        super(theUltimateTile, id, width, Entity.DEFAULT_HEIGHT, EntityHitType.BUSH);
        this.texture = texture;

        bounds.x = this.width / 4 - (this.width / 4) / 2;
        bounds.y = height / 2 + height / 3;
        bounds.width = this.width / 4 + this.width / 2;
        bounds.height = height / 3;
    }

    @Override
    public void die(List<Entity> entities, int index) {
        super.die(entities, index);
        int amt = Utils.getRandomInt(3);
        for (int i = 0; i < amt; i++) {
            theUltimateTile.getItemManager().addItem(new ItemStack(ItemManager.LEAF), x + Utils.getRandomInt(width), y + Utils.getRandomInt(height));
            theUltimateTile.getItemManager().addItem(new ItemStack(ItemManager.STICK), x + Utils.getRandomInt(width), y + Utils.getRandomInt(height));
        }
    }
}
