package coffeecatteam.theultimatetile.game.tile.tiles;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.tile.Tile;
import org.newdawn.slick.Image;

/**
 * @author CoffeeCatRailway
 * Created: 24/12/2018
 */
public class TileWood extends Tile {

    public TileWood(Engine engine, Image texture, String id) {
        super(engine, texture, id, true, TileType.WOOD);
    }

    @Override
    public TileWood newTile() {
        return (TileWood) super.newTile(new TileWood(engine, texture, id));
    }
}
