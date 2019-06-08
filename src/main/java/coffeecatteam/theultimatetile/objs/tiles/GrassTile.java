package coffeecatteam.theultimatetile.objs.tiles;

import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.world.colormap.WorldColors;

/**
 * @author CoffeeCatRailway
 * Created: 22/12/2018
 */
public class GrassTile extends Tile {

    public GrassTile(TutEngine tutEngine) {
        super(tutEngine, "grass", false, Tile.TileType.GROUND);
        this.setMapColor(WorldColors.GRASS);
    }

    @Override
    public GrassTile newCopy() {
        return super.newCopy(new GrassTile(tutEngine));
    }
}
