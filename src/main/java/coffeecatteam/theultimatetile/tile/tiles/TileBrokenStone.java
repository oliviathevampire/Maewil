package coffeecatteam.theultimatetile.tile.tiles;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.manager.ItemManager;
import coffeecatteam.theultimatetile.tile.Tile;
import coffeecatteam.theultimatetile.world.colormap.WorldColors;

/**
 * @author CoffeeCatRailway
 * Created: 22/12/2018
 */
public class TileBrokenStone extends Tile {

    public TileBrokenStone(TutEngine tutEngine) {
        super(tutEngine, "broken_stone", true, TileType.STONE);
        this.setMapColor(WorldColors.STONE);
        this.setDrop(ItemManager.ROCK);
    }

    @Override
    public TileBrokenStone newTile() {
        return super.newTile(new TileBrokenStone(tutEngine));
    }
}
