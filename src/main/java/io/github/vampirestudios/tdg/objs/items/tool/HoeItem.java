package io.github.vampirestudios.tdg.objs.items.tool;

import io.github.vampirestudios.tdg.objs.entities.creatures.PlayerEntity;
import io.github.vampirestudios.tdg.start.MaewilEngine;

/**
 * @author CoffeeCatRailway
 * Created: 28/03/2019
 */
public class HoeItem extends ToolItem {

    public HoeItem(MaewilEngine maewilEngine, String id, int damage) {
        super(maewilEngine, id, damage, ToolType.HOE);
    }

    @Override
    public boolean onInteracted(PlayerEntity player) {
        return false;
    }

    @Override
    public ToolItem newCopy() {
        return super.newCopy(new HoeItem(maewilEngine, id, damage));
    }
}
