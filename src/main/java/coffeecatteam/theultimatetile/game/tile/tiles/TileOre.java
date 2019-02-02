package coffeecatteam.theultimatetile.game.tile.tiles;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.inventory.items.Item;
import coffeecatteam.theultimatetile.game.tile.Tile;
import org.newdawn.slick.Image;

/**
 * @author CoffeeCatRailway
 * Created: 22/12/2018
 */
public class TileOre extends Tile {

    public TileOre(Engine engine, Image texture, String id, Item drop) {
        super(engine, texture, id, true, TileType.STONE);
        this.setDrop(drop);
    }

    @Override
    public TileOre newTile() {
        return (TileOre) super.newTile(new TileOre(engine, texture, id, drop));
    }
}
