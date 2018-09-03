package coffeecatteam.theultimatetile.entities.creatures.undead;

import coffeecatteam.theultimatetile.entities.Entity;
import coffeecatteam.theultimatetile.entities.creatures.EntityCreature;
import coffeecatteam.theultimatetile.gfx.*;
import coffeecatteam.theultimatetile.inventory.items.Item;
import coffeecatteam.theultimatetile.inventory.items.ItemStack;
import coffeecatteam.theultimatetile.utils.Utils;

import java.awt.*;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public abstract class EntityUndead extends EntityCreature {

    private long lastAttackTimer, attackCooldown = 800, attackTimer = attackCooldown;

    protected Animation animIdle, animUp, animDown, animLeft, animRight;
    protected Animation currentAnim;

    protected Item drop = null;
    protected int dmgModifier = 0;

    /* Animations - 500 = 0.5 second */
    protected int speed = 135;
    protected int upDownSpeed = speed + 115;

    protected float maxDistance = 350f;

    public EntityUndead(String id) {
        super(id, Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT);
        init();
        currentAnim = animIdle;
    }

    protected abstract void init();

    @Override
    public void tick() {
        xMove = 0;
        yMove = 0;

        // Movement
        if (theUltimateTile.getEntityManager().getPlayer().isActive()) {
            float x = theUltimateTile.getEntityManager().getPlayer().getX() - this.x;
            float y = theUltimateTile.getEntityManager().getPlayer().getY() - this.y;

            float distance = (float) Math.sqrt(x * x + y * y);
            float multiplier = 2.0f / distance;

            if (distance < maxDistance) {
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

        for (Entity e : theUltimateTile.getEntityManager().getEntities())
            if (e.equals(theUltimateTile.getEntityManager().getPlayer()))
                if (e.getCollisionBounds(0, 0).intersects(ar))
                    e.hurt(Utils.getRandomInt(1, 3) + dmgModifier);
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
        int x = (int) (this.x - theUltimateTile.getCamera().getxOffset());
        int y = (int) (this.y - theUltimateTile.getCamera().getyOffset());
        g.drawImage(currentAnim.getCurrentFrame(), x, y, width, height, null);

        int barWidth = 16;
        g.drawImage(sheet.crop(0, 9, barWidth, 2), x, y - 8, width, 4, null);

        int ht = (int) Utils.map(currentHealth, 0, maxHealth, 0, width); // (currentHealth * 100.0f) / 15
        g.drawImage(sheet.crop(0, 5, barWidth, 2), x, y - 8, ht, 4, null);

        Font font = Assets.FONT_20;
        String textHealth = "HP: " + currentHealth;
        int xOff = Text.getWidth(g, textHealth, font) / 2 - width / 2;
        Text.drawString(g, textHealth, x - xOff, y - Text.getHeight(g, font) / 2, false, false, new Color(0, 255, 0), font);
    }

    @Override
    public void die(List<Entity> entities, int index) {
        super.die(entities, index);
        if (drop != null) {
            int amt = new Random().nextInt(3) + 1;
            for (int i = 0; i < amt; i++)
                theUltimateTile.getItemManager().addItem(new ItemStack(drop), x + Utils.getRandomInt(0, width), y + Utils.getRandomInt(0, height));
        }
    }
}
