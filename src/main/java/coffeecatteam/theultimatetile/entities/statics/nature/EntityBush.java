package coffeecatteam.theultimatetile.entities.statics.nature;

import coffeecatteam.theultimatetile.GameEngine;
import coffeecatteam.theultimatetile.entities.Entity;
import coffeecatteam.theultimatetile.entities.statics.EntityStatic;
import coffeecatteam.theultimatetile.inventory.items.ItemStack;
import coffeecatteam.theultimatetile.manager.ItemManager;
import coffeecatteam.utils.Utils;

import java.awt.image.BufferedImage;
import java.util.List;

public class EntityBush extends EntityStatic {

    public EntityBush(GameEngine gameEngine, String id, BufferedImage texture, int width) {
        super(gameEngine, id, width, Entity.DEFAULT_HEIGHT, EntityHitType.BUSH);
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
            gameEngine.getItemManager().addItem(new ItemStack(ItemManager.LEAF), x + Utils.getRandomInt(width), y + Utils.getRandomInt(height));
            gameEngine.getItemManager().addItem(new ItemStack(ItemManager.STICK), x + Utils.getRandomInt(width), y + Utils.getRandomInt(height));
        }
    }
}
