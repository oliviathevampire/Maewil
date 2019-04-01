package coffeecatteam.theultimatetile.objs.entities.statics;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.objs.items.Item;
import coffeecatteam.theultimatetile.objs.items.ItemStack;
import coffeecatteam.theultimatetile.objs.tiles.Tile;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CoffeeCatRailway
 * Created: 11/12/2018
 */
public abstract class EntityNature extends EntityStatic {

    protected List<Item> drops = new ArrayList<>();

    public EntityNature(TutEngine tutEngine, String id, int width, int height, HitType hitType) {
        super(tutEngine, id, width, height, hitType);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        super.render(container, game, g);
        if (this.currentHealth != this.maxHealth)
            this.renderHealth(container, game, g);
    }

    @Override
    public void die() {
        for (Item item : drops) {
            int amt = NumberUtils.getRandomInt(3);
            for (int i = 0; i < amt; i++)
                tutEngine.getEntityManager().addItem(new ItemStack(item.newCopy()), (float) (position.x + NumberUtils.getRandomInt(width)) / Tile.TILE_SIZE, (float) (position.y + NumberUtils.getRandomInt(height)) / Tile.TILE_SIZE);
        }
    }

    public List<Item> getDrops() {
        return drops;
    }
}
