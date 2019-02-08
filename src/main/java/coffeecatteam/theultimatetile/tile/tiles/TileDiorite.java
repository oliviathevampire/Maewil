package coffeecatteam.theultimatetile.tile.tiles;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.gfx.assets.Assets;

/**
 * @author CoffeeCatRailway
 * Created: 3/02/2019
 */
public class TileDiorite extends TileStone {

    public TileDiorite(TutEngine tutEngine, String id) {
        super(tutEngine, Assets.DIORITE, id, true);
    }

    @Override
    public TileDiorite newTile() {
        return (TileDiorite) super.newTile(new TileDiorite(tutEngine, id));
    }
}
