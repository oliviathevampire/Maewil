package io.github.vampirestudios.tdg.gfx.assets;

import io.github.vampirestudios.tdg.start.MaewilLauncher;
import io.github.vampirestudios.tdg.utils.Identifier;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import java.net.URL;

public class Sounds {

    public static Music BG_MUSIC;
    public static Sound BOUNCE;

    public static Sound PUNCH;

    public static Sound STEP_GROUND, STEP_STONE, STEP_WOOD;

    public static Sound BUSH;

    public static Sound SPLASH;

    public static Sound CLICK_1, CLICK_2;

    public static void init() throws SlickException {
        BG_MUSIC = loadMusic("bg_music");

        BOUNCE = loadSound("bounce");

        PUNCH = loadSound("punch");

        STEP_GROUND = loadSound("step_ground");
        STEP_STONE = loadSound("step_stone");
        STEP_WOOD = loadSound("step_wood");

        BUSH = loadSound("bush");

        SPLASH = loadSound("splash");

        CLICK_1 = loadSound("click_1");
        CLICK_2 = loadSound("click_2");
        MaewilLauncher.LOGGER.info("Sounds loaded!");
    }

    public static Sound loadSound(String name) throws SlickException {
        Identifier path = new Identifier("sounds/" + name + ".ogg");
        URL file = Sounds.class.getResource(path.toAssetsString());
        if (file == null)
            throw new SlickException("Missing sound: " + path);
        return new Sound(file);
    }

    public static Music loadMusic(String name) throws SlickException {
        Identifier path = new Identifier("sounds/" + name + ".ogg");
        URL file = Sounds.class.getResource(path.toAssetsString());
        if (file == null)
            throw new SlickException("Missing sound: " + path.toAssetsString());
        return new Music(file);
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
