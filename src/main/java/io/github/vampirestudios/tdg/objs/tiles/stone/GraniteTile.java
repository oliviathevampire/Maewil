package io.github.vampirestudios.tdg.objs.tiles.stone;

import io.github.vampirestudios.tdg.start.MaewilEngine;

public class GraniteTile extends StoneTile {

    public GraniteTile(MaewilEngine maewilEngine) {
        super(maewilEngine, "granite", true);
    }

    @Override
    public GraniteTile newCopy() {
        return super.newCopy(new GraniteTile(maewilEngine));
    }
}
