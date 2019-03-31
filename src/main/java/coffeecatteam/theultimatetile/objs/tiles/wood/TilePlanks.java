package coffeecatteam.theultimatetile.objs.tiles.wood;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.objs.items.Items;
import coffeecatteam.theultimatetile.objs.tiles.Tile;

/**
 * @author CoffeeCatRailway
 * Created: 29/03/2019
 */
public class TilePlanks extends TileWood {

    public TilePlanks(TutEngine tutEngine) {
        super(tutEngine, "planks");
        this.setDrop(Items.WOOD);
    }

    @Override
    public Tile newCopy() {
        return super.newCopy(new TilePlanks(tutEngine));
    }
}
