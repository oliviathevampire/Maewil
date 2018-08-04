package coffeecatteam.tilegame.entities.creatures;

import coffeecatteam.tilegame.Handler;
import coffeecatteam.tilegame.gfx.Animation;
import coffeecatteam.tilegame.gfx.Assets;

import java.awt.*;

public class EntityZombie extends EntityCreature {

    private Animation animIdle;
    private Animation animUp;
    private Animation animDown;
    private Animation animLeft;
    private Animation animRight;

    private Animation currentAnim;

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

        float x = handler.getWorld().getEntityManager().getPlayer().getX() - this.x;
        float y = handler.getWorld().getEntityManager().getPlayer().getY() - this.y;

        float distance = (float) Math.sqrt(x * x + y * y);
        float multiplier = 2.0f / distance;

        if (distance > 20 && distance < 300) {
            xMove = x * multiplier;
            yMove = y * multiplier;
        }
        updateAnim();

        // Movement
        move();

        // Animation
        currentAnim.tick();
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

    @Override
    public void render(Graphics g) {
        g.drawImage(currentAnim.getCurrentFrame(), (int) (this.x - handler.getCamera().getxOffset()), (int) (this.y - handler.getCamera().getyOffset()), width, height, null);
    }

    @Override
    public void die() {

    }
}
