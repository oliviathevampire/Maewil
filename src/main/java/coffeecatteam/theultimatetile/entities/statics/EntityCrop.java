package coffeecatteam.theultimatetile.entities.statics;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.entities.Entity;
import coffeecatteam.theultimatetile.inventory.items.Item;
import coffeecatteam.theultimatetile.inventory.items.ItemStack;
import coffeecatteam.theultimatetile.utils.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class EntityCrop extends EntityStatic {

    private BufferedImage texture;
    private Item drop;

    public EntityCrop(TheUltimateTile theUltimateTile, String id, BufferedImage texture, Item drop) {
        super(theUltimateTile, id, Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT);
        isCollidable = false;

        this.texture = texture;
        this.drop = drop;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(texture, (int) (this.x - theUltimateTile.getCamera().getxOffset()), (int) (this.y - theUltimateTile.getCamera().getyOffset()), width, height, null);
    }

    @Override
    public void die(List<Entity> entities, int index) {
        super.die(entities, index);
        int amt = new Random().nextInt(2) + 1;
        for (int i = 0; i < amt; i++)
            theUltimateTile.getItemManager().addItem(new ItemStack(drop), x + Utils.getRandomInt(0, width), y + Utils.getRandomInt(0, height));
    }
}
