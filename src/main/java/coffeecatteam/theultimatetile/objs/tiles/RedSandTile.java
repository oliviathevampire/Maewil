package coffeecatteam.theultimatetile.objs.tiles;

import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.world.colormap.WorldColors;

/**
 * @author CoffeeCatRailway
 * Created: 24/12/2018
 */
public class RedSandTile extends OverlapTile {

    public RedSandTile(TutEngine tutEngine) {
        super(tutEngine, Assets.RED_SAND, "red_sand", false, TileType.GROUND, Assets.RED_SAND_ALTS);
        this.setMapColor(WorldColors.RED_SAND);
        this.setConnect("grass", "dirt");
        this.setIgnore("red_sand", "sand");
    }

    @Override
    public RedSandTile newCopy() {
        return super.newCopy(new RedSandTile(tutEngine));
    }
}
