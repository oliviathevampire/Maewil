package coffeecatteam.theultimatetile.objs.tiles.stone;

import coffeecatteam.theultimatetile.objs.items.Items;
import coffeecatteam.theultimatetile.objs.tiles.Tile;
import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.world.colormap.WorldColors;

/**
 * @author CoffeeCatRailway
 * Created: 24/12/2018
 */
public class TileStoneTile extends Tile {

    public TileStoneTile(TutEngine tutEngine) {
        this(tutEngine, "stone_tile");
    }

    public TileStoneTile(TutEngine tutEngine, String id) {
        super(tutEngine,id, true, TileType.STONE);
        this.setMapColor(WorldColors.STONE_TILES);

        this.setDrop(Items.ROCK);
    }

    @Override
    public TileStoneTile newCopy() {
        return super.newCopy(new TileStoneTile(tutEngine));
    }
}
