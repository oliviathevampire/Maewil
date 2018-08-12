package coffeecatteam.tilegame.entities.statics;

import coffeecatteam.tilegame.Handler;
import coffeecatteam.tilegame.entities.Entity;
import coffeecatteam.tilegame.gfx.Assets;
import coffeecatteam.tilegame.items.Item;
import coffeecatteam.tilegame.items.ItemStack;
import coffeecatteam.tilegame.items.Items;
import coffeecatteam.tilegame.utils.Utils;

import java.awt.*;
import java.util.Iterator;
import java.util.Random;

public class EntityRock extends EntityStatic {

    public EntityRock(Handler handler, float x, float y) {
        super(handler, x, y, Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT);

        bounds.x = 0;
        bounds.y = height / 2;
        bounds.width = width;
        bounds.height = height / 2;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.ROCK, (int) (this.x - handler.getCamera().getxOffset()), (int) (this.y - handler.getCamera().getyOffset()), width, height, null);
    }

    @Override
    public void die(Iterator<Entity> it) {
        super.die(it);
        int amt = new Random().nextInt(2) + 1;
        for (int i = 0; i < amt; i++)
            handler.getWorld().getItemManager().addItem(new ItemStack(Items.ROCK), (int) (x + new Random().nextInt(3)), (int) (y + new Random().nextInt(3)));
    }
}
