package coffeecatteam.tilegame.entities.creatures;

import coffeecatteam.tilegame.Game;
import coffeecatteam.tilegame.gfx.Assets;
import coffeecatteam.tilegame.gfx.ImageLoader;
import coffeecatteam.tilegame.gfx.SpriteSheet;

import java.awt.Graphics;

public class Player extends Creature {

    private SpriteSheet sheet;

    private AnimState animState;
    private int animStage = 0;
    private float timer = 0;

    public Player(Game game, float x, float y) {
        super(game, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        sheet =  new SpriteSheet(ImageLoader.loadImage("/assets/textures/character_sheet.png"));

        animState = AnimState.IDLE;
    }

    @Override
    public void tick() {
        getInput();
        move();
        game.getCamera().centerOnEntity(this);
    }

    private void getInput() {
        xMove = 0;
        yMove = 0;

        if (game.getKeyManager().up) {
            yMove = -speed;
            animState = AnimState.FORWARD;
        }
        if (game.getKeyManager().down) {
            yMove = speed;
            animState = AnimState.BACKWARD;
        }
        if (game.getKeyManager().left) {
            xMove = -speed;
            animState = AnimState.LEFT;
        }
        if (game.getKeyManager().right) {
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
        g.drawImage(sheet.crop(animStage * 16, anim * 16, 16, 16), (int) (x - game.getCamera().getxOffset()), (int) (y - game.getCamera().getyOffset()), width, height, null);
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
