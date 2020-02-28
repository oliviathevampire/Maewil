package io.github.vampirestudios.tdg.objs.items.tool;

import io.github.vampirestudios.tdg.objs.entities.creatures.PlayerEntity;
import io.github.vampirestudios.tdg.start.MaewilEngine;

public class SwordItem extends ToolItem {

    public SwordItem(MaewilEngine maewilEngine, String id, int damage) {
        super(maewilEngine, id, damage, ToolType.SOWRD);
    }

    @Override
    public boolean onInteracted(PlayerEntity player) {
        return false;
    }

    @Override
    public ToolItem newCopy() {
        return super.newCopy(new SwordItem(maewilEngine, id, damage));
    }
}
