package coffeecatteam.theultimatetile.game.tile.tiles;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import org.newdawn.slick.Image;

/**
 * @author CoffeeCatRailway
 * Created: 3/02/2019
 */
public class TileDiorite extends TileStone {

    public TileDiorite(Engine engine, String id) {
        super(engine, Assets.DIORITE, id, true);
    }

    @Override
    public TileDiorite newTile() {
        return (TileDiorite) super.newTile(new TileDiorite(engine, id));
    }
}
