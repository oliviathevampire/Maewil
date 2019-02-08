package coffeecatteam.theultimatetile.tile.tiles;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.gfx.assets.Assets;

/**
 * @author CoffeeCatRailway
 * Created: 3/02/2019
 */
public class TileAndesite extends TileStone {

    public TileAndesite(TutEngine tutEngine, String id) {
        super(tutEngine, Assets.ANDESITE, id, true);
    }

    @Override
    public TileAndesite newTile() {
        return (TileAndesite) super.newTile(new TileAndesite(tutEngine, id));
    }
}
