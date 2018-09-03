package coffeecatteam.theultimatetile.entities.statics;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.entities.Entity;
import coffeecatteam.theultimatetile.inventory.items.ItemStack;
import coffeecatteam.theultimatetile.inventory.items.Items;
import coffeecatteam.theultimatetile.utils.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class EntityRock extends EntityStatic {

    private BufferedImage texture;

    public EntityRock(TheUltimateTile theUltimateTile, String id, BufferedImage texture) {
        super(theUltimateTile, id, Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT);
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
        g.drawImage(texture, (int) (this.x - theUltimateTile.getCamera().getxOffset()), (int) (this.y - theUltimateTile.getCamera().getyOffset()), width, height, null);
    }

    @Override
    public void die(List<Entity> entities, int index) {
        super.die(entities, index);
        int amt = new Random().nextInt(2) + 1;
        for (int i = 0; i < amt; i++)
            theUltimateTile.getItemManager().addItem(new ItemStack(Items.ROCK), x + Utils.getRandomInt(0, width), y + Utils.getRandomInt(0, height));
    }
}
