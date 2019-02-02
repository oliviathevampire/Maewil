package coffeecatteam.theultimatetile.game.tile.tiles;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.gfx.assets.Assets;

/**
 * @author CoffeeCatRailway
 * Created: 3/02/2019
 */
public class TileObsidian extends TileStone {

    public TileObsidian(Engine engine, String id) {
        super(engine, Assets.OBSIDIAN, id, true);
        this.setDrop(null);
    }

    @Override
    public TileObsidian newTile() {
        return (TileObsidian) super.newTile(new TileObsidian(engine, id));
    }
}
