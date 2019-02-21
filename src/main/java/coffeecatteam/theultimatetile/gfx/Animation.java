package coffeecatteam.theultimatetile.gfx;

import coffeecatteam.theultimatetile.TutEngine;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;

public class Animation {

    public static final int DEFAULT_SPEED = 50;

    private int speed, index;
    private long lastTime, timer;
    private Image[] frames;

    public Animation() {
        this(new Image[]{});
    }

    public Animation(Image image) {
        this(new Image[]{image, image});
    }

    public Animation(int speed, Image image) {
        this(speed, new Image[]{image});
    }

    public Animation(Image[] frames) {
        this(DEFAULT_SPEED, frames);
    }

    public Animation(int speed, Image[] frames) {
        this.speed = speed;
        this.frames = frames;
        index = 0;
        timer = 0;
        lastTime = System.currentTimeMillis();
    }

    public void setFrames(Image[] frames) {
        this.frames = frames;
    }

    public Image[] getFrames() {
        return frames;
    }

    public void update() {
        timer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();

        if (timer > speed) {
            index++;
            timer = 0;
            if (index >= frames.length)
                index = 0;
        }
    }

    public Image getCurrentFrame() {
        try {
            return frames[index];
        } catch (ArrayIndexOutOfBoundsException e) {
            TutEngine.getTutEngine().getLogger().print(e);
            return frames[0];
        }
    }
}
