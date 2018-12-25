package coffeecatteam.theultimatetile.game.tile.tiles;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.inventory.items.Item;

import java.awt.image.BufferedImage;

/**
 * @author CoffeeCatRailway
 * Created: 24/12/2018
 */
public class TileStone extends TileBreakable {

    public TileStone(Engine engine, BufferedImage texture, String id, Item drop, TileType tileType) {
        super(engine, texture, id, drop, tileType);
    }
}
