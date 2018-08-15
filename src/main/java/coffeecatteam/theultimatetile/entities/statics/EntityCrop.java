package coffeecatteam.theultimatetile.entities.statics;

import coffeecatteam.theultimatetile.Handler;
import coffeecatteam.theultimatetile.entities.Entity;
import coffeecatteam.theultimatetile.items.Item;
import coffeecatteam.theultimatetile.items.ItemStack;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.Random;

public class EntityCrop extends EntityStatic {

    private BufferedImage texture;
    private Item drop;

    public EntityCrop(Handler handler, String id, BufferedImage texture, Item drop) {
        super(handler, id,  Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT);
        isCollidable = false;

        this.texture = texture;
        this.drop = drop;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(texture, (int) (this.x - handler.getCamera().getxOffset()), (int) (this.y - handler.getCamera().getyOffset()), width, height, null);
    }

    @Override
    public void die(Iterator<Entity> it) {
        super.die(it);
        int amt = new Random().nextInt(2) + 1;
        for (int i = 0; i < amt; i++)
            handler.getWorld().getItemManager().addItem(new ItemStack(drop), (int) (x + new Random().nextInt(3)), (int) (y + new Random().nextInt(3)));
    }
}
