package coffeecatteam.theultimatetile.objs.tiles.stone;

import coffeecatteam.theultimatetile.objs.items.Items;
import coffeecatteam.theultimatetile.objs.tiles.Tile;
import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.world.colormap.WorldColors;

/**
 * @author CoffeeCatRailway
 * Created: 24/12/2018
 */
public class StoneTilesTile extends Tile {

    public StoneTilesTile(TutEngine tutEngine) {
        this(tutEngine, "stone_tile");
    }

    public StoneTilesTile(TutEngine tutEngine, String id) {
        super(tutEngine,id, true, TileType.STONE);
        this.setMapColor(WorldColors.STONE_TILES);

        this.setDrop(Items.ROCK);
    }

    @Override
    public StoneTilesTile newCopy() {
        return super.newCopy(new StoneTilesTile(tutEngine));
    }
}
