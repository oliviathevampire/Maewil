package coffeecatteam.theultimatetile.objs.tiles;

import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.world.colormap.WorldColors;

/**
 * @author CoffeeCatRailway
 * Created: 24/12/2018
 */
public class SandTile extends OverlapTile {

    public SandTile(TutEngine tutEngine) {
        super(tutEngine, Assets.GRASS, "sand", false, TileType.GROUND, Assets.GRASS_ALTS);
        this.setMapColor(WorldColors.SAND);
        this.setConnect("grass");
        this.setIgnore("sand");
    }

    @Override
    public SandTile newCopy() {
        return super.newCopy(new SandTile(tutEngine));
    }
}
