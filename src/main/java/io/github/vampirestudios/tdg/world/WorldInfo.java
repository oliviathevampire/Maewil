package io.github.vampirestudios.tdg.world;

import io.github.vampirestudios.tdg.utils.set.DataSet;
import io.netty.buffer.ByteBuf;

import java.io.File;
import java.util.UUID;

public class WorldInfo {

    private static final String NAME = "world_info.dat";
    private final File dataFile;

    public long seed;
    public boolean storyMode = true;
    public UUID lastPlayerId;

    public WorldInfo(File worldDirectory) {
        this.dataFile = new File(worldDirectory, NAME);
    }

    public static boolean exists(File directory) {
        return new File(directory, NAME).exists();
    }

    public static long lastModified(File directory) {
        return new File(directory, NAME).lastModified();
    }

    public void load() {
        DataSet dataSet = new DataSet();
        dataSet.read(this.dataFile);

        this.seed = dataSet.getLong("seed");
        this.storyMode = dataSet.getBoolean("story_mode");
        this.lastPlayerId = dataSet.getUniqueId("last_player");
    }

    public void save() {
        DataSet dataSet = new DataSet();

        dataSet.addLong("seed", this.seed);
        dataSet.addBoolean("story_mode", this.storyMode);
        if (this.lastPlayerId != null) {
            dataSet.addUniqueId("last_player", this.lastPlayerId);
        }

        dataSet.write(this.dataFile);
    }

    public void toBuffer(ByteBuf buf) {
        buf.writeLong(this.seed);
        buf.writeBoolean(this.storyMode);
    }

    public void fromBuffer(ByteBuf buf) {
        this.seed = buf.readLong();
        this.storyMode = buf.readBoolean();
    }
}