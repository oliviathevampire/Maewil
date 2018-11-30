package coffeecatteam.theultimatetile.gfx.audio;

import coffeecatteam.coffeecatutils.Logger;
import org.lwjgl.openal.*;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class AudioMaster {

    private static List<Integer> buffers = new ArrayList<>();
    private static long device;
    private static long context;

    public static void init() {
        device = ALC10.alcOpenDevice((ByteBuffer) null);
        if (device == 0L) {
            Logger.print("\n\nUnable to open default audio device");
            return;
        }

        ALCCapabilities deviceCaps = ALC.createCapabilities(device);

        if (!deviceCaps.OpenALC10) {
            Logger.print("\n\nOpenALC10 Unsupported");
            return;
        }


        context = ALC10.alcCreateContext(device, (IntBuffer) null);
        if (context == 0L) {
            Logger.print("\n\nUnable to create ALC Context");
            return;
        }

        ALC10.alcMakeContextCurrent(context);
        AL.createCapabilities(deviceCaps);
    }

    public static void setListenerData(float x, float y, float z) {
        AL10.alListener3f(AL10.AL_POSITION, x, y, z);
        AL10.alListener3f(AL10.AL_VELOCITY, 0f, 0f, 0f);
    }

    public static int loadSound(String file) {
        int buffer = AL10.alGenBuffers();
        buffers.add(buffer);
        WaveData waveFile = WaveData.create(AudioMaster.class.getResourceAsStream("/assets/sounds/" + file + ".wav"));
        AL10.alBufferData(buffer, waveFile.format, waveFile.data, waveFile.samplerate);
        waveFile.dispose();
        return buffer;
    }

    public static void cleanUp() {
        buffers.forEach(AL10::alDeleteBuffers);
        ALC10.alcDestroyContext(context);
        ALC10.alcCloseDevice(device);
    }
}
