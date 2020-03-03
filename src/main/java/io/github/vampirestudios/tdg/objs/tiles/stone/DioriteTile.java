package io.github.vampirestudios.tdg.objs.tiles.stone;

import io.github.vampirestudios.tdg.start.MaewilEngine;

public class DioriteTile extends StoneTile {

    public DioriteTile(MaewilEngine maewilEngine) {
        super(maewilEngine, "diorite", true);
    }

    @Override
    public DioriteTile newCopy() {
        return super.newCopy(new DioriteTile(maewilEngine));
    }
}
