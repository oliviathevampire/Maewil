package coffeecatteam.tilegame.entities.creatures;

import coffeecatteam.tilegame.Handler;
import coffeecatteam.tilegame.entities.Entity;
import coffeecatteam.tilegame.gfx.Animation;
import coffeecatteam.tilegame.gfx.Assets;
import coffeecatteam.tilegame.inventory.Inventory;
import coffeecatteam.tilegame.tiles.Tile;
import coffeecatteam.tilegame.tiles.Tiles;
import coffeecatteam.tilegame.utils.Utils;

import java.awt.*;
import java.util.Iterator;

public class EntityPlayer extends EntityCreature {

    private Animation animIdle, animUp, animDown, animLeft, animRight, animDead;
    private Animation currentAnim;

    private Animation sprintEffect;
    private Animation splashEffect;

    private long lastAttackTimer, attackCooldown = 400, attackTimer = attackCooldown;

    private Inventory inventory;

    public EntityPlayer(Handler handler, float x, float y) {
        super(handler, x, y, Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT);

        bounds.x = 13;
        bounds.y = 28;
        bounds.width = 34;
        bounds.height = 35;

        // Animations - 500 = 0.5 second
        int speed = 135;
        int upDownSpeed = speed + 115;
        animIdle = new Animation(speed, Assets.PLAYER_IDLE);
        animUp = new Animation(upDownSpeed, Assets.PLAYER_UP);
        animDown = new Animation(upDownSpeed, Assets.PLAYER_DOWN);
        animLeft = new Animation(speed, Assets.PLAYER_LEFT);
        animRight = new Animation(speed, Assets.PLAYER_RIGHT);
        animDead = new Animation(speed, Assets.PLAYER_DEAD);

        currentAnim = animIdle;

        int effectSpeed = 50;
        sprintEffect = new Animation(effectSpeed, Assets.SPRINT_EFFECT);
        splashEffect = new Animation(effectSpeed, Assets.SPLASH_EFFECT);

        inventory = new Inventory(handler);
    }

    @Override
    public void tick() {
        if (isActive()) {
            if (!inventory.isActive()) {
                // Movement
                getInput();
                move();

                // Attack
                checkAttacks();
            }

            handler.getCamera().centerOnEntity(this);
            inventory.tick();
        }

        // Animation
        currentAnim.tick();
        sprintEffect.tick();
        splashEffect.tick();
    }

    private void checkAttacks() {
        attackTimer += System.currentTimeMillis() - lastAttackTimer;
        lastAttackTimer = System.currentTimeMillis();
        if (attackTimer < attackCooldown)
            return;

        Rectangle cb = getCollisionBounds(0, 0);
        Rectangle ar = new Rectangle();
        int arSize = 20;
        ar.width = arSize;
        ar.height = arSize;

        if (handler.getKeyManager().up && handler.getKeyManager().interact) {
            ar.x = cb.x + cb.width / 2 - arSize / 2;
            ar.y = cb.y - arSize;
        } else if (handler.getKeyManager().down && handler.getKeyManager().interact) {
            ar.x = cb.x + cb.width / 2 - arSize / 2;
            ar.y = cb.y + cb.height;
        } else if (handler.getKeyManager().left && handler.getKeyManager().interact) {
            ar.x = cb.x - arSize;
            ar.y = cb.y + cb.height / 2 - arSize / 2;
        } else if (handler.getKeyManager().right && handler.getKeyManager().interact) {
            ar.x = cb.x + cb.width;
            ar.y = cb.y + cb.height / 2 - arSize / 2;
        } else {
            return;
        }

        attackTimer = 0;

        for (Entity e : handler.getWorld().getEntityManager().getEntities()) {
            if (e.equals(this))
                continue;
            if (e.getCollisionBounds(0, 0).intersects(ar)) {
                e.hurt(Utils.getRandomInt(5, 10));
                return;
            }
        }
    }

    @Override
    public void die(Iterator<Entity> it) {
        currentAnim = animDead;
    }

    private void getInput() {
        xMove = 0;
        yMove = 0;

        if (handler.getKeyManager().sprint && !inWater())
            speed = EntityCreature.DEFAULT_SPEED * 1.7f;
        if (!handler.getKeyManager().sprint)
            speed = EntityCreature.DEFAULT_SPEED;
        if (inWater())
            speed = EntityCreature.DEFAULT_SPEED * 0.65f;

        if (handler.getKeyManager().up) {
            yMove = -speed;
            currentAnim = animUp;
        }
        if (handler.getKeyManager().down) {
            yMove = speed;
            currentAnim = animDown;
        }
        if (handler.getKeyManager().left) {
            xMove = -speed;
            currentAnim = animLeft;
        }
        if (handler.getKeyManager().right) {
            xMove = speed;
            currentAnim = animRight;
        }
        if (xMove == 0 && yMove == 0)
            currentAnim = animIdle;
    }

    private boolean inWater() {
        for (int y = 0; y < handler.getWorld().getWidth(); y++) {
            for (int x = 0; x < handler.getWorld().getHeight(); x++) {
                int tx = (int) (this.x / Tile.TILE_WIDTH);
                int ty = (int) (this.y / Tile.TILE_HEIGHT);
                Tile tile = handler.getWorld().getTile(tx, ty);
                if (tile.getId() == Tiles.WATER.getId()) {
                    if (tile.getBounds().intersects(bounds))
                        return true;
                }
            }
        }
        return false;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(currentAnim.getCurrentFrame(), (int) (x - handler.getCamera().getxOffset()), (int) (y - handler.getCamera().getyOffset()), width, height, null);
        if (handler.getKeyManager().sprint && !inWater() && currentAnim != animIdle)
            g.drawImage(sprintEffect.getCurrentFrame(), (int) (x - handler.getCamera().getxOffset()), (int) (y - handler.getCamera().getyOffset()), width, height, null);

        if (inWater()) {
            g.drawImage(splashEffect.getCurrentFrame(), (int) (x - handler.getCamera().getxOffset()), (int) (y - handler.getCamera().getyOffset()), width, height, null);
        }
    }

    public void postRender(Graphics g) {
        inventory.render(g);
    }

    public Inventory getInventory() {
        return inventory;
    }
}
