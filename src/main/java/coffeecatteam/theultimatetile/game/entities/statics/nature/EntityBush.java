package coffeecatteam.theultimatetile.game.entities.statics.nature;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.entities.Entity;
import coffeecatteam.theultimatetile.game.entities.statics.EntityStatic;
import coffeecatteam.theultimatetile.game.inventory.items.ItemStack;
import coffeecatteam.theultimatetile.manager.ItemManager;
import coffeecatteam.theultimatetile.utils.Utils;

import java.awt.image.BufferedImage;
import java.util.List;

public class EntityBush extends EntityStatic {

    public EntityBush(Engine engine, String id, BufferedImage texture, int width) {
        super(engine, id, width, Entity.DEFAULT_HEIGHT, EntityHitType.BUSH);
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
            ((GameEngine) engine).getItemManager().addItem(new ItemStack(ItemManager.LEAF), x + Utils.getRandomInt(width), y + Utils.getRandomInt(height));
            ((GameEngine) engine).getItemManager().addItem(new ItemStack(ItemManager.STICK), x + Utils.getRandomInt(width), y + Utils.getRandomInt(height));
        }
    }
}
