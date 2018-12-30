package coffeecatteam.theultimatetile.gfx.assets;

import coffeecatteam.theultimatetile.Engine;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import java.net.URL;

public class Sounds {

    public static Sound BG_MUSIC;
    public static Sound BOUNCE;

    public static Sound PUNCH;

    public static Sound STEP_GROUND;
    public static Sound STEP_STONE;
    public static Sound STEP_WOOD;

    public static Sound BUSH;

    public static Sound SPLASH;

    public static void init() throws SlickException {
        BG_MUSIC = loadSound("bg_music");
        BOUNCE = loadSound("bounce");

        PUNCH = loadSound("punch");

        STEP_GROUND = loadSound("step_ground");
        STEP_STONE = loadSound("step_stone");
        STEP_WOOD = loadSound("step_wood");

        BUSH = loadSound("bush");

        SPLASH = loadSound("splash");
        Engine.getEngine().getLogger().print("Sounds loaded!");
    }

    private static Sound loadSound(String name) throws SlickException {
        String path = "/assets/sounds/" + name + ".ogg";
        URL file = Sounds.class.getResource(path);
        if (file == null)
            throw new SlickException("Missing sound: " + path);
        return new Sound(file);
    }

    /*
     * Play sound methods
     */
    public static void play(Sound sound, float x, float y) {
        play(sound, x, y, 1f, 1f);
    }
    
    public static void play(Sound sound, float x, float y, float pitch, float volume) {
        play(sound, x, y, 1f, 1f, false);
    }

    public static void play(Sound sound, float x, float y, float pitch, float volume, boolean loop) {
        if (sound != null && !sound.playing()) {
            if (loop)
                sound.loop();
            sound.playAt(pitch, volume, x, y, 0.1f);
        }
    }
}
