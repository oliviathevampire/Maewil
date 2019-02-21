package coffeecatteam.theultimatetile.objs.tiles;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.objs.items.Items;
import coffeecatteam.theultimatetile.world.colormap.WorldColors;

/**
 * @author CoffeeCatRailway
 * Created: 22/12/2018
 */
public class TileBrokenStone extends Tile {

    public TileBrokenStone(TutEngine tutEngine) {
        super(tutEngine, "broken_stone", true, TileType.STONE);
        this.setMapColor(WorldColors.STONE);
        this.setDrop(Items.ROCK);
    }

    @Override
    public TileBrokenStone newCopy() {
        return super.newCopy(new TileBrokenStone(tutEngine));
    }
}
