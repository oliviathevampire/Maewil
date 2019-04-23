package coffeecatteam.theultimatetile.objs.tiles;

import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.world.colormap.WorldColors;

/**
 * @author CoffeeCatRailway
 * Created: 22/12/2018
 */
public class TileDirt extends TileOverlap {

    public TileDirt(TutEngine tutEngine) {
        super(tutEngine, Assets.GRASS, "dirt", false, Tile.TileType.GROUND, Assets.GRASS_ALTS);
        this.setMapColor(WorldColors.DIRT);
        this.setConnect("grass");
        this.setIgnore("dirt");
    }

    @Override
    public TileDirt newCopy() {
        return super.newCopy(new TileDirt(tutEngine));
    }
}
