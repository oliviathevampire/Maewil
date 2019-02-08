package coffeecatteam.theultimatetile.tile.tiles;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.gfx.assets.Assets;

/**
 * @author CoffeeCatRailway
 * Created: 3/02/2019
 */
public class TileObsidian extends TileStone {

    public TileObsidian(TutEngine tutEngine, String id) {
        super(tutEngine, Assets.OBSIDIAN, id, true);
        this.setDrop(null);
    }

    @Override
    public TileObsidian newTile() {
        return (TileObsidian) super.newTile(new TileObsidian(tutEngine, id));
    }
}
