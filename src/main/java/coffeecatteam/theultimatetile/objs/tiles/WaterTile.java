package coffeecatteam.theultimatetile.objs.tiles;

import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.world.colormap.WorldColors;

/**
 * @author CoffeeCatRailway
 * Created: 26/12/2018
 */
public class WaterTile extends OverlapTile {

    public WaterTile(TutEngine tutEngine) {
        super(tutEngine, Assets.SAND, "water", false, TileType.FLUID, Assets.SAND_ALTS);
        this.setMapColor(WorldColors.WATER);
        this.setConnect("sand", "grass");
        this.setIgnore("water");
    }

    @Override
    public WaterTile newCopy() {
        return super.newCopy(new WaterTile(tutEngine));
    }
}
