package coffeecatteam.theultimatetile.game.tile.tiles;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.gfx.assets.Assets;

/**
 * @author CoffeeCatRailway
 * Created: 3/02/2019
 */
public class TileAndesite extends TileStone {

    public TileAndesite(Engine engine, String id) {
        super(engine, Assets.ANDESITE, id, true);
    }

    @Override
    public TileAndesite newTile() {
        return (TileAndesite) super.newTile(new TileAndesite(engine, id));
    }
}
