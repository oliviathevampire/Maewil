package io.github.vampirestudios.tdg.objs;

import org.mini2Dx.core.serialization.annotation.Field;

public class PlayerData {
    @Field
    private int levelsComplete;

    public int getLevelsComplete() {
        return levelsComplete;
    }

    public void setLevelsComplete(int levelsComplete) {
        this.levelsComplete = levelsComplete;
    }
}