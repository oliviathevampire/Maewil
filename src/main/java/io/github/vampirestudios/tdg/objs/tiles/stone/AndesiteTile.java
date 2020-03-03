package io.github.vampirestudios.tdg.objs.tiles.stone;

import io.github.vampirestudios.tdg.start.MaewilEngine;

public class AndesiteTile extends StoneTile {

    public AndesiteTile(MaewilEngine maewilEngine) {
        super(maewilEngine, "andesite", true);
    }

    @Override
    public AndesiteTile newCopy() {
        return super.newCopy(new AndesiteTile(maewilEngine));
    }
}
