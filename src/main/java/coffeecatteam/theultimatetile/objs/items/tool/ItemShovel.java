package coffeecatteam.theultimatetile.objs.items.tool;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.objs.entities.creatures.EntityPlayer;

/**
 * @author CoffeeCatRailway
 * Created: 28/03/2019
 */
public class ItemShovel extends ItemTool {

    public ItemShovel(TutEngine tutEngine, String id, int damage) {
        super(tutEngine, id, damage, ToolType.SHOVEL);
    }

    @Override
    public boolean onInteracted(EntityPlayer player) {
        return false;
    }

    @Override
    public ItemTool newCopy() {
        return super.newCopy(new ItemShovel(tutEngine, id, damage));
    }
}
