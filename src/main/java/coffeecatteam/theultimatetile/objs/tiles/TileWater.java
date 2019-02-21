package coffeecatteam.theultimatetile.objs.tiles;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.world.colormap.WorldColors;

/**
 * @author CoffeeCatRailway
 * Created: 26/12/2018
 */
public class TileWater extends TileOverlap {

    public TileWater(TutEngine tutEngine) {
        super(tutEngine, Assets.SAND, "water", false, TileType.FLUID, Assets.SAND_ALTS);
        this.setMapColor(WorldColors.WATER);
        this.setConnect("sand", "grass");
        this.setIgnore("water");
    }

    @Override
    public TileWater newCopy() {
        return super.newCopy(new TileWater(tutEngine));
    }
}
