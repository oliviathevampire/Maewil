package io.github.vampirestudios.tdg.objs.items.tool;

import io.github.vampirestudios.tdg.objs.entities.creatures.PlayerEntity;
import io.github.vampirestudios.tdg.start.MaewilEngine;

public class ShovelItem extends ToolItem {

    public ShovelItem(MaewilEngine maewilEngine, String id, int damage) {
        super(maewilEngine, id, damage, ToolType.SHOVEL);
    }

    @Override
    public boolean onInteracted(PlayerEntity player) {
        return false;
    }

    @Override
    public ToolItem newCopy() {
        return super.newCopy(new ShovelItem(maewilEngine, id, damage));
    }
}
