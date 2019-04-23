package coffeecatteam.theultimatetile.objs.items.tool;

import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.objs.entities.creatures.EntityPlayer;

/**
 * @author CoffeeCatRailway
 * Created: 28/03/2019
 */
public class ItemSword extends ItemTool {

    public ItemSword(TutEngine tutEngine, String id, int damage) {
        super(tutEngine, id, damage, ToolType.SOWRD);
    }

    @Override
    public boolean onInteracted(EntityPlayer player) {
        return false;
    }

    @Override
    public ItemTool newCopy() {
        return super.newCopy(new ItemSword(tutEngine, id, damage));
    }
}
