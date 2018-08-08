package coffeecatteam.tilegame.entities.creatures;

import coffeecatteam.tilegame.Handler;
import coffeecatteam.tilegame.entities.Entity;
import coffeecatteam.tilegame.gfx.Animation;
import coffeecatteam.tilegame.gfx.Assets;
import coffeecatteam.tilegame.gfx.ImageLoader;
import coffeecatteam.tilegame.gfx.SpriteSheet;
import coffeecatteam.tilegame.items.Item;
import coffeecatteam.tilegame.items.Items;
import coffeecatteam.tilegame.utils.Utils;

import java.awt.*;
import java.util.Iterator;
import java.util.Random;

public class EntityZombie extends EntityCreature {

    private Animation animIdle, animUp, animDown, animLeft, animRight;

    private Animation currentAnim;

    private long lastAttackTimer, attackCooldown = 800, attackTimer = attackCooldown;

    public EntityZombie(Handler handler, float x, float y) {
        super(handler, x, y, Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT);

        bounds.x = 13;
        bounds.y = 28;
        bounds.width = 34;
        bounds.height = 35;

        // Animations - 500 = 0.5 second
        int speed = 135;
        int upDownSpeed = speed + 115;
        animIdle = new Animation(speed, Assets.ZOMBIE_IDLE);
        animUp = new Animation(upDownSpeed, Assets.ZOMBIE_UP);
        animDown = new Animation(upDownSpeed, Assets.ZOMBIE_DOWN);
        animLeft = new Animation(speed, Assets.ZOMBIE_LEFT);
        animRight = new Animation(speed, Assets.ZOMBIE_RIGHT);

        currentAnim = animIdle;
    }

    @Override
    public void tick() {
        xMove = 0;
        yMove = 0;

        // Movement
        if (handler.getWorld().getEntityManager().getPlayer().isActive()) {
            float x = handler.getWorld().getEntityManager().getPlayer().getX() - this.x;
            float y = handler.getWorld().getEntityManager().getPlayer().getY() - this.y;

            float distance = (float) Math.sqrt(x * x + y * y);
            float multiplier = 2.0f / distance;

            if (distance < 350) {
                xMove = x * multiplier;
                yMove = y * multiplier;
            }
        }
        updateAnim();
        move();

        // Animation
        currentAnim.tick();

        // Attack arSizeRectangle
        checkAttacks();
    }

    private void checkAttacks() {
        attackTimer += System.currentTimeMillis() - lastAttackTimer;
        lastAttackTimer = System.currentTimeMillis();
        if (attackTimer < attackCooldown)
            return;

        Rectangle cb = getCollisionBounds(0, 0);
        Rectangle ar = new Rectangle();
        int arSize = width * 2;
        ar.x = cb.x - arSize / 2;
        ar.y = cb.y - arSize / 2;
        ar.width = arSize;
        ar.height = arSize;

        attackTimer = 0;

        for (Entity e : handler.getWorld().getEntityManager().getEntities())
            if (e.equals(handler.getWorld().getEntityManager().getPlayer()))
                if (e.getCollisionBounds(0, 0).intersects(ar))
                    e.hurt(Utils.getRandomInt(1, 3));
    }

    private void updateAnim() {
        if (yMove < 0)
            currentAnim = animUp;
        if (yMove > 0)
            currentAnim = animDown;

        if (xMove < 0)
            currentAnim = animLeft;
        if (xMove > 0)
            currentAnim = animRight;

        if (xMove == 0 && yMove == 0)
            currentAnim = animIdle;
    }

    private SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/assets/textures/gui/overlay/health_bar.png"));

    @Override
    public void render(Graphics g) {
        g.drawImage(currentAnim.getCurrentFrame(), (int) (this.x - handler.getCamera().getxOffset()), (int) (this.y - handler.getCamera().getyOffset()), width, height, null);

        g.drawImage(sheet.crop(0, 9, 16, 2), (int) (this.x - handler.getCamera().getxOffset()), (int) (this.y - handler.getCamera().getyOffset()) - 8, width, 4, null);

        float ht = (health * 100.0f) / 15;
        g.drawImage(sheet.crop(0, 5, 16, 2), (int) (this.x - handler.getCamera().getxOffset()), (int) (this.y - handler.getCamera().getyOffset()) - 8, (int) (ht / 10), 4, null);
    }

    @Override
    public void die(Iterator<Entity> it) {
        super.die(it);
        int amt = new Random().nextInt(3) + 1;
        for (int i = 0; i < amt; i++)
            handler.getWorld().getItemManager().addItem(Items.ROTTEN_FLESH.createNew((int) (x + Utils.getRandomInt(0, 2) * Item.WIDTH), (int) (y + Utils.getRandomInt(0, 2) * Item.HEIGHT)));
    }
}
