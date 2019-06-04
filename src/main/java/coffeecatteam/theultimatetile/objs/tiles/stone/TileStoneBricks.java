package coffeecatteam.theultimatetile.objs.tiles.stone;

import coffeecatteam.theultimatetile.objs.items.Items;
import coffeecatteam.theultimatetile.objs.tiles.Tile;
import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.world.colormap.WorldColors;

/**
 * @author CoffeeCatRailway
 * Created: 24/12/2018
 */
public class TileStoneBricks extends Tile {

    public TileStoneBricks(TutEngine tutEngine) {
        this(tutEngine, "stone_bricks");
    }

    public TileStoneBricks(TutEngine tutEngine, String id) {
        super(tutEngine, id, true, TileType.STONE);
        this.setMapColor(WorldColors.STONE);

        this.setDrop(Items.ROCK);
    }

    @Override
    public TileStoneBricks newCopy() {
        return super.newCopy(new TileStoneBricks(tutEngine));
    }
}
