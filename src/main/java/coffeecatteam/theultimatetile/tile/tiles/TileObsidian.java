package coffeecatteam.theultimatetile.tile.tiles;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.world.colormap.WorldColors;

/**
 * @author CoffeeCatRailway
 * Created: 3/02/2019
 */
public class TileObsidian extends TileStone {

    public TileObsidian(TutEngine tutEngine) {
        super(tutEngine, "obsidian", true);
        this.setMapColor(WorldColors.STONE);
        this.setDrop(null);
    }

    @Override
    public TileObsidian newTile() {
        return super.newTile(new TileObsidian(tutEngine));
    }
}
