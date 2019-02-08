package coffeecatteam.theultimatetile.game.entities.statics;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.game.entities.Entity;
import coffeecatteam.theultimatetile.inventory.items.Item;
import coffeecatteam.theultimatetile.inventory.items.ItemStack;
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

    public EntityNature(TutEngine tutEngine, String id, Image texture, int width, int height, EntityHitType entityHitType) {
        super(tutEngine, id, width, height, entityHitType);
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
                ((TutEngine) TutEngine).getItemManager().addItem(new ItemStack(item), (float) position.x + NumberUtils.getRandomInt(width), (float) position.y + NumberUtils.getRandomInt(height));
            }
        }
    }

    public List<Item> getDrops() {
        return drops;
    }
}
