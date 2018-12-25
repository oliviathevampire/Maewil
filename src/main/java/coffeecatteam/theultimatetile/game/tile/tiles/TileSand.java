package coffeecatteam.theultimatetile.game.tile.tiles;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.tile.Tile;

import java.awt.image.BufferedImage;

/**
 * @author CoffeeCatRailway
 * Created: 24/12/2018
 */
public class TileSand extends Tile {

    public TileSand(Engine engine, BufferedImage texture, String id, boolean isSolid, TileType tileType) {
        super(engine, texture, id, isSolid, tileType);
    }
}
