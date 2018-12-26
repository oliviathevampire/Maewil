package coffeecatteam.theultimatetile.game.tile.tiles;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.inventory.items.Item;
import coffeecatteam.theultimatetile.game.tile.Tile;

import java.awt.image.BufferedImage;

/**
 * @author CoffeeCatRailway
 * Created: 24/12/2018
 */
public class TileStone extends Tile {

    public TileStone(Engine engine, BufferedImage texture, String id) {
        this(engine, texture, id, null);
    }

    public TileStone(Engine engine, BufferedImage texture, String id, Item drop) {
        super(engine, texture, id, true, TileType.STONE);
        this.setDrop(drop);
    }

    @Override
    public TileStone copy() {
        return new TileStone(engine, texture, id, drop);
    }
}
