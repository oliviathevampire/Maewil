package coffeecatteam.theultimatetile.game.entities.statics;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.entities.Entity;
import coffeecatteam.theultimatetile.game.inventory.items.Item;
import coffeecatteam.theultimatetile.game.inventory.items.ItemStack;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import java.util.ArrayList;
import java.util.List;

/**
 * @author CoffeeCatRailway
 * Created: 11/12/2018
 */
public class EntityNature extends EntityStatic {

    protected List<Item> drops = new ArrayList<>();

    public EntityNature(Engine engine, String id, Image texture, int width, int height, EntityHitType entityHitType) {
        super(engine, id, width, height, entityHitType);
        this.texture = texture;
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        if (this.currentHealth != this.maxHealth)
            this.renderHealth(g);
    }

    @Override
    public void die(List<Entity> entities, int index) {
        super.die(entities, index);
        for (Item item : drops) {
            int amt = NumberUtils.getRandomInt(3);
            for (int i = 0; i < amt; i++) {
                ((GameEngine) engine).getItemManager().addItem(new ItemStack(item), (float) position.x + NumberUtils.getRandomInt(width), (float) position.y + NumberUtils.getRandomInt(height));
            }
        }
    }

    public List<Item> getDrops() {
        return drops;
    }
}
