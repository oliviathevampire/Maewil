package coffeecatteam.theultimatetile.game.entities.statics.nature;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.entities.Entity;
import coffeecatteam.theultimatetile.game.entities.statics.EntityStatic;
import coffeecatteam.theultimatetile.game.inventory.items.Item;
import coffeecatteam.theultimatetile.game.inventory.items.ItemStack;
import coffeecatteam.theultimatetile.gfx.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class EntityCrop extends EntityStatic {

    private Item drop;

    public EntityCrop(Engine engine, String id, BufferedImage texture, Item drop) {
        super(engine, id, Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT, EntityHitType.BUSH);
        isCollidable = false;

        this.texture = texture;
        this.drop = drop;
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(Assets.CROP_GROUND, this.renderX, this.renderY, width, height, null);
        super.render(g);
    }

    @Override
    public void die(List<Entity> entities, int index) {
        super.die(entities, index);
        int amt = NumberUtils.getRandomInt(3);
        for (int i = 0; i < amt; i++)
            ((GameEngine) engine).getItemManager().addItem(new ItemStack(drop), (float) position.x + NumberUtils.getRandomInt(width), (float) position.y + NumberUtils.getRandomInt(height));
    }
}
