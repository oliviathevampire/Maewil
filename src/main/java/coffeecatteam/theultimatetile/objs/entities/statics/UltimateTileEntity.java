package coffeecatteam.theultimatetile.objs.entities.statics;

import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.start.TutEngine;

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
