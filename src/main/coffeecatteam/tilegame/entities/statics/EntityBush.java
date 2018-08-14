package coffeecatteam.tilegame.entities.statics;

import coffeecatteam.tilegame.Handler;
import coffeecatteam.tilegame.entities.Entity;
import coffeecatteam.tilegame.items.ItemStack;
import coffeecatteam.tilegame.items.Items;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.Random;

public class EntityBush extends EntityStatic {

    private BufferedImage texture;

    public EntityBush(Handler handler, String id, BufferedImage texture, int width) {
        super(handler, id, width, Entity.DEFAULT_HEIGHT);

        this.texture = texture;

        bounds.x = this.width / 4 - (this.width / 4) / 2;
        bounds.y = height / 2 + height / 3;
        bounds.width = this.width / 4 + this.width / 2;
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
        for (int i = 0; i < amt; i++) {
            handler.getWorld().getItemManager().addItem(new ItemStack(Items.LEAF), (int) (x + new Random().nextInt(3)), (int) (y + new Random().nextInt(3)));
            handler.getWorld().getItemManager().addItem(new ItemStack(Items.STICK), (int) (x + new Random().nextInt(3)), (int) (y + new Random().nextInt(3)));
        }
    }
}
