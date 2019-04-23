package coffeecatteam.theultimatetile.objs.tiles.stone;

import coffeecatteam.theultimatetile.start.TutEngine;
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
    public TileObsidian newCopy() {
        return super.newCopy(new TileObsidian(tutEngine));
    }
}
