package coffeecatteam.tilegame.entities.creatures;

import coffeecatteam.tilegame.Handler;
import coffeecatteam.tilegame.gfx.ImageLoader;
import coffeecatteam.tilegame.gfx.SpriteSheet;

import java.awt.*;

public class Player extends Creature {

    private SpriteSheet sheet;

    private AnimState animState;
    private int animStage = 0;
    private float timer = 0;

    public Player(Handler handler, float x, float y) {
        super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        sheet =  new SpriteSheet(ImageLoader.loadImage("/assets/textures/character_sheet.png"));
        animState = AnimState.IDLE;

        bounds.x = 13;
        bounds.y = 2;
        bounds.width = 34;
        bounds.height = 61;
    }

    @Override
    public void tick() {
        getInput();
        move();
        handler.getCamera().centerOnEntity(this);
    }

    private void getInput() {
        xMove = 0;
        yMove = 0;

        if (handler.getKeyManager().up) {
            yMove = -speed;
            animState = AnimState.FORWARD;
        }
        if (handler.getKeyManager().down) {
            yMove = speed;
            animState = AnimState.BACKWARD;
        }
        if (handler.getKeyManager().left) {
            xMove = -speed;
            animState = AnimState.LEFT;
        }
        if (handler.getKeyManager().right) {
            xMove = speed;
            animState = AnimState.RIGHT;
        }
        if (xMove == 0 && yMove == 0)
            animState = AnimState.IDLE;
    }

    @Override
    public void render(Graphics g) {
        int anim = animState.getAnim();
        int length = animState.getLength();

        boolean fliped = animState.isFliped();
        int width = this.width;
        float x = this.x;

        timer += 0.25f;
        if (timer % 2 == 0)
            animStage++;
        if (animStage >= length)
            animStage = 0;
        if (fliped) {
            width = this.width - this.width * 2;
            x = this.x - width;
        }
        g.drawImage(sheet.crop(animStage * 16, anim * 16, 16, 16), (int) (x - handler.getCamera().getxOffset()), (int) (y - handler.getCamera().getyOffset()), width, height, null);

//        g.setColor(Color.red);
//        g.fillRect((int) (x + bounds.x - handler.getCamera().getxOffset()), (int) (y + bounds.y - handler.getCamera().getyOffset()), bounds.width, bounds.height);
    }

    enum AnimState {
        IDLE(0, false, 4),
        FORWARD(3, false, 3),
        BACKWARD(1, false, 3),
        LEFT(2, true, 4),
        RIGHT(2, false, 4);

        private int anim;
        private boolean fliped;
        private int length;

        AnimState(int anim, boolean fliped, int length) {
            this.anim = anim;
            this.fliped = fliped;
            this.length = length;
        }

        public int getAnim() {
            return anim;
        }

        public boolean isFliped() {
            return fliped;
        }

        public int getLength() {
            return length;
        }
    }
}
