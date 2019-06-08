package coffeecatteam.theultimatetile.objs.items;

import coffeecatteam.theultimatetile.start.TutEngine;

/**
 * @author CoffeeCatRailway
 * Created: 21/02/2019
 */
public class BasicItem extends Item {

    public BasicItem(TutEngine tutEngine, String id) {
        super(tutEngine, id);
    }

    @Override
    public BasicItem newCopy() {
        return super.newCopy(new BasicItem(tutEngine, id));
    }
}
