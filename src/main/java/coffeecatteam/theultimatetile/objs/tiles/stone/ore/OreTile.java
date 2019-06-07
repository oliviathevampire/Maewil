package coffeecatteam.theultimatetile.objs.tiles.stone.ore;

import coffeecatteam.theultimatetile.objs.items.Item;
import coffeecatteam.theultimatetile.objs.tiles.Tile;
import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.world.colormap.WorldColors;

/**
 * @author CoffeeCatRailway
 * Created: 22/12/2018
 */
public abstract class OreTile extends Tile {

    public OreTile(TutEngine tutEngine, String id, Item drop) {
        super(tutEngine, id, true, TileType.STONE);
        this.setMapColor(WorldColors.STONE);
        this.setDrop(drop.newCopy());
    }
}
