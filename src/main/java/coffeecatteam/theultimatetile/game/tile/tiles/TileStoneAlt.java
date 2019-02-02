package coffeecatteam.theultimatetile.game.tile.tiles;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.gfx.assets.Assets;

/**
 * @author CoffeeCatRailway
 * Created: 24/12/2018
 */
public class TileStoneAlt extends TileStone {

    public TileStoneAlt(Engine engine, String id) {
        super(engine, null, id, true);
    }

    @Override
    public void init() {
        this.setHasAlts(true);
        this.addTextureAlts(Assets.STONE);

        super.init();
    }

    @Override
    public TileStoneAlt newTile() {
        return (TileStoneAlt) super.newTile(new TileStoneAlt(engine, id));
    }
}
