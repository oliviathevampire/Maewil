package coffeecatteam.theultimatetile.objs.tiles;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.objs.items.Items;
import coffeecatteam.theultimatetile.world.colormap.WorldColors;

/**
 * @author CoffeeCatRailway
 * Created: 24/12/2018
 */
public class TileStone extends TileOverlap {

    public TileStone(TutEngine tutEngine) {
        this(tutEngine, "stone", true);
    }

    public TileStone(TutEngine tutEngine, String id, boolean overlap) {
        super(tutEngine, Assets.BROKEN_STONE, id, true, TileType.STONE, Assets.BROKEN_STONE_ALTS);
        this.setMapColor(WorldColors.STONE);

        if (overlap) {
            this.setConnect("broken_stone");
            this.setIgnore("stone");
        }

        this.setDrop(Items.BOOK);
    }

    @Override
    public TileStone newTile() {
        return super.newTile(new TileStone(tutEngine));
    }
}