package coffeecatteam.theultimatetile.game.tile.tiles;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.tile.Tile;

import java.awt.image.BufferedImage;

/**
 * @author CoffeeCatRailway
 * Created: 23/12/2018
 */
public class TileAir extends Tile {

    public TileAir(Engine engine, BufferedImage texture, String id, boolean isSolid, TileType tileType) {
        super(engine, texture, id, isSolid, tileType);
    }
}
