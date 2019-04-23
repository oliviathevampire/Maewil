package coffeecatteam.theultimatetile.gfx;

import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.start.TutLauncher;
import org.newdawn.slick.Image;

import java.util.ArrayList;
import java.util.List;

public class Animation {

    public static List<Animation> ANIMATIONS = new ArrayList<>();
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

        ANIMATIONS.add(this);
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
            if (frames[index] == null)
                return Assets.MISSING_TEXTURE;
        } catch (ArrayIndexOutOfBoundsException e) {
            TutLauncher.LOGGER.error(e);
            return Assets.MISSING_TEXTURE;
        }

        return frames[index];
    }
}
