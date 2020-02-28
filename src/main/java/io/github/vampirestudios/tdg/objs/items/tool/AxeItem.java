package io.github.vampirestudios.tdg.objs.items.tool;

import io.github.vampirestudios.tdg.objs.entities.creatures.PlayerEntity;
import io.github.vampirestudios.tdg.start.MaewilEngine;

public class AxeItem extends ToolItem {

    public AxeItem(MaewilEngine maewilEngine, String id, int damage) {
        super(maewilEngine, id, damage, ToolType.AXE);
    }

    @Override
    public boolean onInteracted(PlayerEntity player) {
        return false;
    }

    @Override
    public ToolItem newCopy() {
        return super.newCopy(new AxeItem(maewilEngine, id, damage));
    }
}
