package coffeecatteam.theultimatetile.objs.tiles.wood;

import coffeecatteam.theultimatetile.objs.items.Items;
import coffeecatteam.theultimatetile.objs.tiles.Tile;
import coffeecatteam.theultimatetile.start.TutEngine;

/**
 * @author CoffeeCatRailway
 * Created: 29/03/2019
 */
public class TileLog extends TileWood {

    public TileLog(TutEngine tutEngine) {
        super(tutEngine, "log");
        this.setDrop(Items.BARK);
    }

    @Override
    public Tile newCopy() {
        return super.newCopy(new TileLog(tutEngine));
    }
}
