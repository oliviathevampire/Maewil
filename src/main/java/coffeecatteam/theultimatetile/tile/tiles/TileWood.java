package coffeecatteam.theultimatetile.tile.tiles;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.tile.Tile;
import coffeecatteam.theultimatetile.world.colormap.WorldColors;

/**
 * @author CoffeeCatRailway
 * Created: 24/12/2018
 */
public class TileWood extends Tile {

    public TileWood(TutEngine tutEngine, String id) {
        super(tutEngine, id, true, TileType.WOOD);
        this.setMapColor(WorldColors.WOOD);
    }

    @Override
    public TileWood newTile() {
        return super.newTile(new TileWood(tutEngine, id));
    }
}
