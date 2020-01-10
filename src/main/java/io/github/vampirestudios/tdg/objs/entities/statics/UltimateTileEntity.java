package io.github.vampirestudios.tdg.objs.entities.statics;

import io.github.vampirestudios.tdg.objs.entities.Entity;
import io.github.vampirestudios.tdg.start.TutEngine;

public class UltimateTileEntity extends StaticEntity {

    public UltimateTileEntity(TutEngine tutEngine) {
        super(tutEngine, "ultimate", Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT, HitType.NONE);
        isCollidable = false;
        setCurrentTexture("main");
    }

    @Override
    public Entity newCopy() {
        return super.newCopy(new UltimateTileEntity(tutEngine));
    }
}
