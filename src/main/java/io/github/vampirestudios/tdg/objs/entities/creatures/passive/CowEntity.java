package io.github.vampirestudios.tdg.objs.entities.creatures.passive;

import io.github.vampirestudios.tdg.objs.entities.Entity;
import io.github.vampirestudios.tdg.objs.entities.creatures.EntityPassive;
import io.github.vampirestudios.tdg.objs.items.Items;
import io.github.vampirestudios.tdg.start.MaewilEngine;

public class CowEntity extends EntityPassive {

    public CowEntity(MaewilEngine maewilEngine) {
        super(maewilEngine, "cow");
        this.drop = Items.RAW_STEAK;
    }

    @Override
    public Entity newCopy() {
        return super.newCopy(new CowEntity(maewilEngine));
    }
}
