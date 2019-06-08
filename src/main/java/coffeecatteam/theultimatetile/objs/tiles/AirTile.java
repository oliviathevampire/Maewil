package coffeecatteam.theultimatetile.objs.tiles;

import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.world.colormap.WorldColors;

/**
 * @author CoffeeCatRailway
 * Created: 23/12/2018
 */
public class AirTile extends Tile {

    public AirTile(TutEngine tutEngine) {
        super(tutEngine, "air", false, Tile.TileType.AIR);
        this.setMapColor(WorldColors.AIR);
    }

    @Override
    public AirTile newCopy() {
        return super.newCopy(new AirTile(tutEngine));
    }
}
