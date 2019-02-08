package coffeecatteam.theultimatetile.tile.tiles;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.inventory.items.Item;
import coffeecatteam.theultimatetile.tile.Tile;
import org.newdawn.slick.Image;

/**
 * @author CoffeeCatRailway
 * Created: 22/12/2018
 */
public class TileOre extends Tile {

    public TileOre(TutEngine tutEngine, Image texture, String id, Item drop) {
        super(tutEngine, texture, id, true, TileType.STONE);
        this.setDrop(drop);
    }

    @Override
    public TileOre newTile() {
        return (TileOre) super.newTile(new TileOre(tutEngine, texture, id, drop));
    }
}
