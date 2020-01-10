package io.github.vampirestudios.tdg.objs.items.tool;

import io.github.vampirestudios.tdg.objs.entities.creatures.PlayerEntity;
import io.github.vampirestudios.tdg.start.TutEngine;

/**
 * @author CoffeeCatRailway
 * Created: 28/03/2019
 */
public class ShovelItem extends ToolItem {

    public ShovelItem(TutEngine tutEngine, String id, int damage) {
        super(tutEngine, id, damage, ToolType.SHOVEL);
    }

    @Override
    public boolean onInteracted(PlayerEntity player) {
        return false;
    }

    @Override
    public ToolItem newCopy() {
        return super.newCopy(new ShovelItem(tutEngine, id, damage));
    }
}
