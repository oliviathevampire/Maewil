package coffeecatteam.theultimatetile.objs.tiles;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.objs.items.Item;
import coffeecatteam.theultimatetile.world.colormap.WorldColors;

/**
 * @author CoffeeCatRailway
 * Created: 22/12/2018
 */
public class TileOre extends Tile {

    public TileOre(TutEngine tutEngine, String id, Item drop) {
        super(tutEngine, id, true, TileType.STONE);
        this.setMapColor(WorldColors.STONE);
        this.setDrop(drop);
    }

    @Override
    public TileOre newCopy() {
        return super.newCopy(new TileOre(tutEngine, id, drop));
    }
}
