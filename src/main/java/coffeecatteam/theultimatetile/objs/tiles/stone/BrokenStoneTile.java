package coffeecatteam.theultimatetile.objs.tiles.stone;

import coffeecatteam.theultimatetile.objs.items.Items;
import coffeecatteam.theultimatetile.objs.tiles.Tile;
import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.world.colormap.WorldColors;

/**
 * @author CoffeeCatRailway
 * Created: 22/12/2018
 */
public class BrokenStoneTile extends Tile {

    public BrokenStoneTile(TutEngine tutEngine) {
        super(tutEngine, "broken_stone", true, TileType.STONE);
        this.setMapColor(WorldColors.STONE);
        this.setDrop(Items.ROCK);
    }

    @Override
    public BrokenStoneTile newCopy() {
        return super.newCopy(new BrokenStoneTile(tutEngine));
    }
}
