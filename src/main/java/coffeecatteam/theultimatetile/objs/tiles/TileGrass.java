package coffeecatteam.theultimatetile.objs.tiles;

import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.world.colormap.WorldColors;

/**
 * @author CoffeeCatRailway
 * Created: 22/12/2018
 */
public class TileGrass extends Tile {

    public TileGrass(TutEngine tutEngine) {
        super(tutEngine, "grass", false, Tile.TileType.GROUND);
        this.setMapColor(WorldColors.GRASS);
    }

    @Override
    public TileGrass newCopy() {
        return super.newCopy(new TileGrass(tutEngine));
    }
}
