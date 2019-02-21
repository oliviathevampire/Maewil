package coffeecatteam.theultimatetile.objs.items;

import coffeecatteam.theultimatetile.TutEngine;

/**
 * @author CoffeeCatRailway
 * Created: 21/02/2019
 */
public class ItemBasic extends Item {

    public ItemBasic(TutEngine tutEngine, String id) {
        super(tutEngine, id);
    }

    @Override
    public ItemBasic newItem() {
        return super.newItem(new ItemBasic(tutEngine, id));
    }
}