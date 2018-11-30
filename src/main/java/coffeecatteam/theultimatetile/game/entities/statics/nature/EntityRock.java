package coffeecatteam.theultimatetile.game.entities.statics.nature;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.entities.Entity;
import coffeecatteam.theultimatetile.game.entities.statics.EntityStatic;
import coffeecatteam.theultimatetile.game.inventory.items.ItemStack;
import coffeecatteam.theultimatetile.manager.ItemManager;

import java.awt.image.BufferedImage;
import java.util.List;

public class EntityRock extends EntityStatic {

    public EntityRock(Engine engine, String id, BufferedImage texture) {
        super(engine, id, Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT, EntityHitType.STONE);
        this.texture = texture;

        bounds.x = 0;
        bounds.y = height / 2 + height / 3;
        bounds.width = width;
        bounds.height = height / 3;
    }

    @Override
    public void die(List<Entity> entities, int index) {
        super.die(entities, index);
        int amt = NumberUtils.getRandomInt(3);
        for (int i = 0; i < amt; i++)
            ((GameEngine) engine).getItemManager().addItem(new ItemStack(ItemManager.ROCK), (float) position.x + NumberUtils.getRandomInt(width), (float) position.y + NumberUtils.getRandomInt(height));
    }
}
