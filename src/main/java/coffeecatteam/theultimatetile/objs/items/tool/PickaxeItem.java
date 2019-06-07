package coffeecatteam.theultimatetile.objs.items.tool;

import coffeecatteam.theultimatetile.objs.entities.creatures.PlayerEntity;
import coffeecatteam.theultimatetile.start.TutEngine;

/**
 * @author CoffeeCatRailway
 * Created: 28/03/2019
 */
public class PickaxeItem extends ToolItem {

    public PickaxeItem(TutEngine tutEngine, String id, int damage) {
        super(tutEngine, id, damage, ToolType.PICKAXE);
    }

    @Override
    public boolean onInteracted(PlayerEntity player) {
        return false;
    }

    @Override
    public ToolItem newCopy() {
        return super.newCopy(new PickaxeItem(tutEngine, id, damage));
    }
}
