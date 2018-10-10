package coffeecatteam.theultimatetile.gfx.audio;

import java.util.HashMap;
import java.util.Map;

public class Sound {

    private static Map<Integer, Source> SOURCES = new HashMap<>();

    public static int BG_MUSIC;
    public static int BOUNCE;

    public static int PUNCH_LEFT;
    public static int PUNCH_RIGHT;

    public static int STEP_GROUND;
    public static int STEP_STONE;
    public static int STEP_WOOD;

    public static void init() {
        register(BG_MUSIC = AudioMaster.loadSound("bg_music"));
        register(BOUNCE = AudioMaster.loadSound("bounce"));

        register(PUNCH_LEFT = AudioMaster.loadSound("punch_left"));
        register(PUNCH_RIGHT = AudioMaster.loadSound("punch_right"));

        register(STEP_GROUND = AudioMaster.loadSound("step_ground"));
        register(STEP_STONE = AudioMaster.loadSound("step_stone"));
        register(STEP_WOOD = AudioMaster.loadSound("step_wood"));
    }

    private static void register(int sound) {
        SOURCES.put(sound, new Source(1f, 1f));
    }

    public static void play(int sound, float volume, float x, float y, float z) {
        play(sound, volume, x, y, z, false);
    }

    public static void play(int sound, float volume, float x, float y, float z, boolean loop) {
        SOURCES.get(sound).setLooping(loop);
        SOURCES.get(sound).setPosition(x, y, z);
        SOURCES.get(sound).setVolume(volume);
        SOURCES.get(sound).play(sound);
    }

    public static void setVolume(int sound, float volume) {
        SOURCES.get(sound).setVolume(volume);
    }

    public static void delete() {
        SOURCES.values().forEach(Source::delete);
    }
}
