package coffeecatteam.theultimatetile.objs.tiles.wood;

import coffeecatteam.theultimatetile.objs.items.Items;
import coffeecatteam.theultimatetile.objs.tiles.Tile;
import coffeecatteam.theultimatetile.start.TutEngine;

/**
 * @author CoffeeCatRailway
 * Created: 29/03/2019
 */
public class PlanksTile extends WoodTile {

    public PlanksTile(TutEngine tutEngine) {
        super(tutEngine, "planks");
        this.setDrop(Items.WOOD);
    }

    @Override
    public Tile newCopy() {
        return super.newCopy(new PlanksTile(tutEngine));
    }
}
