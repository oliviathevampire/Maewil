package coffeecatteam.theultimatetile.tile.tiles;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.inventory.items.Item;
import coffeecatteam.theultimatetile.tile.Tile;
import coffeecatteam.theultimatetile.world.colormap.WorldColors;

/**
 * @author CoffeeCatRailway
 * Created: 22/12/2018
 */
public class TileOre extends Tile {

    public TileOre(TutEngine tutEngine, String id, Item drop) {
        super(tutEngine, id, true, TileType.STONE);
        this.setMapColor(WorldColors.STONE);
        this.setDrop(drop);
    }

    @Override
    public TileOre newTile() {
        return super.newTile(new TileOre(tutEngine, id, drop));
    }
}
