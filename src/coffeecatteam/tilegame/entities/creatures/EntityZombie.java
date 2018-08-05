package coffeecatteam.tilegame.entities.creatures;

import coffeecatteam.tilegame.Handler;
import coffeecatteam.tilegame.entities.Entity;
import coffeecatteam.tilegame.gfx.Animation;
import coffeecatteam.tilegame.gfx.Assets;
import coffeecatteam.tilegame.gfx.ImageLoader;
import coffeecatteam.tilegame.gfx.SpriteSheet;
import coffeecatteam.tilegame.items.Item;
import coffeecatteam.tilegame.tiles.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class EntityZombie extends EntityCreature {

    private Animation animIdle, animUp, animDown, animLeft, animRight;

    private Animation currentAnim;

    private long lastAttackTimer, attackCooldown = 800, attackTimer = attackCooldown;

    public EntityZombie(Handler handler, float x, float y) {
        super(handler, x, y, EntityCreature.DEFAULT_CREATURE_WIDTH, EntityCreature.DEFAULT_CREATURE_HEIGHT);

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
            } // else {
//                wander();
//            }
        }
        updateAnim();
        move();

        // Animation
        currentAnim.tick();

        // Attack arSizeRectangle
        checkAttacks();
    }

    private long lastWanderTimer, wanderCooldown = 10000, wanderTimer = wanderCooldown;
    private float xWander, yWander;

    private void wander() {
        wanderTimer += System.currentTimeMillis() - lastWanderTimer;
        lastWanderTimer = System.currentTimeMillis();
        if (wanderTimer > wanderCooldown) {
            List<String> poss = new ArrayList<>();
            for (int y = 0; y < handler.getWorld().getHeight(); y++) {
                for (int x = 0; x < handler.getWorld().getWidth(); x++) {
                    Tile t = handler.getWorld().getTile(x, y);
                    boolean isSolid = t.isSolid();
                    if (!isSolid)
                        poss.add(x + "," + y);
                }
            }
            String t = poss.get(new Random().nextInt(poss.size()));
            xWander = Float.parseFloat(t.split(",")[0]);
            yWander = Float.parseFloat(t.split(",")[1]);

            wanderTimer = 0;
            return;
        }

        float x = xWander - this.x;
        float y = yWander - this.y;

        float distance = (float) Math.sqrt(x * x + y * y);
        float multiplier = 2.0f / distance;

        xMove = x * multiplier;
        yMove = y * multiplier;
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
                    e.hurt(new Random().nextInt(3) + 1);
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
            handler.getWorld().getItemManager().addItem(Item.ROTTEN_FLESH.createNew((int) (x + new Random().nextInt(2) * Item.WIDTH), (int) (y + new Random().nextInt(2) * Item.HEIGHT)));
    }
}
