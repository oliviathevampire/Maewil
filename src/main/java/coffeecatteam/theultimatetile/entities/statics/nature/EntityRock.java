package coffeecatteam.theultimatetile.entities.statics.nature;

import coffeecatteam.theultimatetile.GameEngine;
import coffeecatteam.theultimatetile.entities.Entity;
import coffeecatteam.theultimatetile.entities.statics.EntityStatic;
import coffeecatteam.theultimatetile.inventory.items.ItemStack;
import coffeecatteam.theultimatetile.manager.ItemManager;
import coffeecatteam.utils.Utils;

import java.awt.image.BufferedImage;
import java.util.List;

public class EntityRock extends EntityStatic {

    public EntityRock(GameEngine gameEngine, String id, BufferedImage texture) {
        super(gameEngine, id, Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT, EntityHitType.STONE);
        this.texture = texture;

        bounds.x = 0;
        bounds.y = height / 2 + height / 3;
        bounds.width = width;
        bounds.height = height / 3;
    }

    @Override
    public void die(List<Entity> entities, int index) {
        super.die(entities, index);
        int amt = Utils.getRandomInt(3);
        for (int i = 0; i < amt; i++)
            gameEngine.getItemManager().addItem(new ItemStack(ItemManager.ROCK), x + Utils.getRandomInt(width), y + Utils.getRandomInt(height));
    }
}
