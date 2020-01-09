package coffeecatteam.theultimatetile.objs.tiles;

import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.world.colormap.WorldColors;

/**
 * @author CoffeeCatRailway
 * Created: 23/12/2018
 */
public class AirTile extends Tile {

    public AirTile(TutEngine tutEngine) {
        super(tutEngine, TileSettings.Builder.create().air(true).id("air").solid(false).mapColor(WorldColors.AIR).tileType(TileType.AIR).unbreakable(true).build());
    }

    @Override
    public AirTile newCopy() {
        return super.newCopy(new AirTile(tutEngine));
    }

}
