package coffeecatteam.theultimatetile.objs.entities.statics;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.objs.items.Item;
import coffeecatteam.theultimatetile.objs.items.ItemStack;
import coffeecatteam.theultimatetile.objs.tiles.Tile;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CoffeeCatRailway
 * Created: 11/12/2018
 */
public abstract class EntityNature extends EntityStatic {

    protected List<Item> drops = new ArrayList<>();

    public EntityNature(TutEngine tutEngine, String id, int width, int height, EntityHitType entityHitType) {
        super(tutEngine, id, width, height, entityHitType);
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
            for (int i = 0; i < amt; i++)
                tutEngine.getEntityManager().addItem(new ItemStack(item.newCopy()), (float) (position.x + NumberUtils.getRandomInt(width)) / Tile.TILE_WIDTH, (float) (position.y + NumberUtils.getRandomInt(height)) / Tile.TILE_HEIGHT);
        }
    }

    public List<Item> getDrops() {
        return drops;
    }
}
