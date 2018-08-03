package coffeecatteam.tilegame.entities.creatures;

import coffeecatteam.tilegame.Handler;
import coffeecatteam.tilegame.gfx.Animation;
import coffeecatteam.tilegame.gfx.Assets;

import java.awt.*;

public class EntityPlayer extends EntityCreature {

    private Animation animIdle;
    private Animation animUp;
    private Animation animDown;
    private Animation animLeft;
    private Animation animRight;

    private Animation currentAnim;

    //private Animation testAnim;

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

        currentAnim = animIdle;

        //testAnim = new Animation(100, Assets.EXTRA_LIFE);
    }

    @Override
    public void tick() {
        // Movement
        getInput();
        move();
        handler.getCamera().centerOnEntity(this);

        // Animation
        currentAnim.tick();
        //testAnim.tick();
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
        for (int i = 0; i < 3; i++) {
            int x = (int) (this.x - handler.getCamera().getxOffset()) - 10 * i * 2 + 45;
            int y = (int) (this.y - handler.getCamera().getyOffset()) - 40;
            g.drawImage(Assets.HEARTS[0], x, y, width / 4, height / 4, null);
            g.drawImage(Assets.SPRINT[0], x, y + 20, width / 4, height / 4, null);
        }

        g.drawImage(currentAnim.getCurrentFrame(), (int) (this.x - handler.getCamera().getxOffset()), (int) (this.y - handler.getCamera().getyOffset()), width, height, null);
        //g.drawImage(testAnim.getCurrentFrame(), (int) (this.x - handler.getCamera().getxOffset()), (int) (this.y - handler.getCamera().getyOffset()) - 100, width, height, null);

//        g.setColor(Color.red);
//        g.fillRect((int) (x + bounds.x - handler.getCamera().getxOffset()), (int) (y + bounds.y - handler.getCamera().getyOffset()), bounds.width, bounds.height);
    }
}
