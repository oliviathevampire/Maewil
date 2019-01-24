package coffeecatteam.theultimatetile.game.tile.tiles;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.inventory.items.Item;
import coffeecatteam.theultimatetile.game.tile.Tile;

import org.newdawn.slick.Image;

/**
 * @author CoffeeCatRailway
 * Created: 24/12/2018
 */
public class TileStone extends Tile {

    public TileStone(Engine engine, Image texture, String id) {
        this(engine, texture, id, null);
    }

    public TileStone(Engine engine, Image texture, String id, Item drop) {
        super(engine, texture, id, true, TileType.STONE);
        this.setDrop(drop);
    }

    @Override
    public TileStone newTile() {
        return new TileStone(engine, texture, id, drop);
    }
}
