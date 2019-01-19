package coffeecatteam.theultimatetile.gfx;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;

public class Animation {

    private int speed, index;
    private long lastTime, timer;
    private Image[] frames;

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

    public void update(GameContainer container, int delta) {
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
        return frames[index];
    }
}
