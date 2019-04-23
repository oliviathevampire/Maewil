package coffeecatteam.theultimatetile.objs.tiles.wood;

import coffeecatteam.theultimatetile.objs.tiles.Tile;
import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.world.colormap.WorldColors;

/**
 * @author CoffeeCatRailway
 * Created: 24/12/2018
 */
public abstract class TileWood extends Tile {

    public TileWood(TutEngine tutEngine, String id) {
        super(tutEngine, id, true, TileType.WOOD);
        this.setMapColor(WorldColors.WOOD);
    }
}
