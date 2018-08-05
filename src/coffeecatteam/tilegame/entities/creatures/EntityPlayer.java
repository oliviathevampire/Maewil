package coffeecatteam.tilegame.entities.creatures;

import coffeecatteam.tilegame.Handler;
import coffeecatteam.tilegame.entities.Entity;
import coffeecatteam.tilegame.gfx.Animation;
import coffeecatteam.tilegame.gfx.Assets;
import coffeecatteam.tilegame.gfx.overlays.Overlay;
import coffeecatteam.tilegame.gfx.overlays.OverlayPlayerHealth;

import java.awt.*;
import java.util.Iterator;
import java.util.Random;

public class EntityPlayer extends EntityCreature {

    private Animation animIdle, animUp, animDown, animLeft, animRight, animDead;
    private Animation currentAnim;

    //private Animation testAnim;

    private long lastAttackTimer, attackCooldown = 400, attackTimer = attackCooldown;

    public EntityPlayer(Handler handler, float x, float y) {
        super(handler, x, y, EntityCreature.DEFAULT_CREATURE_WIDTH, EntityCreature.DEFAULT_CREATURE_HEIGHT);

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

        //testAnim = new Animation(100, Assets.EXTRA_LIFE);
    }

    @Override
    public void tick() {
        if (isActive()) {
            // Movement
            getInput();
            move();
            handler.getCamera().centerOnEntity(this);

            // Attack
            checkAttacks();
        }

        // Animation
        currentAnim.tick();
        //testAnim.tick();
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
            ar.y = cb.y + cb.height / 2 -  arSize / 2;
        } else if (handler.getKeyManager().right && handler.getKeyManager().interact) {
            ar.x = cb.x + cb.width;
            ar.y = cb.y + cb.height / 2 -  arSize / 2;
        } else {
            return;
        }

        attackTimer = 0;

        for (Entity e : handler.getWorld().getEntityManager().getEntities()) {
            if (e.equals(this))
                continue;
            if (e.getCollisionBounds(0, 0).intersects(ar)) {
                e.hurt(new Random().nextInt(5) + 1);
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

    @Override
    public void render(Graphics g) {

        g.drawImage(currentAnim.getCurrentFrame(), (int) (this.x - handler.getCamera().getxOffset()), (int) (this.y - handler.getCamera().getyOffset()), width, height, null);
        //g.drawImage(testAnim.getCurrentFrame(), (int) (this.x - handler.getCamera().getxOffset()), (int) (this.y - handler.getCamera().getyOffset()) - 100, width, height, null);
    }
}
