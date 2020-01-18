package io.github.vampirestudios.tdg.objs.entities.creatures.undead;

import io.github.vampirestudios.tdg.objs.entities.Entity;
import io.github.vampirestudios.tdg.objs.entities.creatures.EntityUndead;
import io.github.vampirestudios.tdg.objs.items.Items;
import io.github.vampirestudios.tdg.start.MaewilEngine;

public class ZombieEntity extends EntityUndead {

    public ZombieEntity(MaewilEngine maewilEngine) {
        super(maewilEngine, "zombie");
        this.drop = Items.ROTTEN_FLESH;
    }

    @Override
    public Entity newCopy() {
        return super.newCopy(new ZombieEntity(maewilEngine));
    }
}
