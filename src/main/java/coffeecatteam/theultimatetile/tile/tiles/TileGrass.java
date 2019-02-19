package coffeecatteam.theultimatetile.tile.tiles;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.tile.Tile;
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
    public TileGrass newTile() {
        return super.newTile(new TileGrass(tutEngine));
    }
}
