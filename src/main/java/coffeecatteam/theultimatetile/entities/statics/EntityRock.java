package coffeecatteam.theultimatetile.entities.statics;

import coffeecatteam.theultimatetile.Handler;
import coffeecatteam.theultimatetile.entities.Entity;
import coffeecatteam.theultimatetile.items.ItemStack;
import coffeecatteam.theultimatetile.items.Items;
import coffeecatteam.theultimatetile.utils.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.Random;

public class EntityRock extends EntityStatic {

    private BufferedImage texture;

    public EntityRock(Handler handler, String id, BufferedImage texture) {
        super(handler, id, Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT);
        this.texture = texture;

        bounds.x = 0;
        bounds.y = height / 2 + height / 3;
        bounds.width = width;
        bounds.height = height / 3;
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
            handler.getGame().getItemManager().addItem(new ItemStack(Items.ROCK), x + Utils.getRandomInt(0, width), y + Utils.getRandomInt(0, height));
    }
}
