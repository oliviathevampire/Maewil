package coffeecatteam.theultimatetile.game.tile.tiles;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.tile.Tile;

import java.awt.image.BufferedImage;

/**
 * @author CoffeeCatRailway
 * Created: 24/12/2018
 */
public class TileWood extends Tile {

    public TileWood(Engine engine, BufferedImage texture, String id) {
        super(engine, texture, id, true, TileType.WOOD);
    }

    @Override
    public TileWood copy() {
        return new TileWood(engine, texture, id);
    }
}
