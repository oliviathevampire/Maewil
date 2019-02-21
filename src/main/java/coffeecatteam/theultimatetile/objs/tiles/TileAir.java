package coffeecatteam.theultimatetile.objs.tiles;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.world.colormap.WorldColors;

/**
 * @author CoffeeCatRailway
 * Created: 23/12/2018
 */
public class TileAir extends Tile {

    public TileAir(TutEngine tutEngine) {
        super(tutEngine, "air", false, Tile.TileType.AIR);
        this.setMapColor(WorldColors.AIR);
    }

    @Override
    public TileAir newTile() {
        return super.newTile(new TileAir(tutEngine));
    }
}
