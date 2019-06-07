package coffeecatteam.theultimatetile.objs.tiles;

import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.world.colormap.WorldColors;

/**
 * @author CoffeeCatRailway
 * Created: 22/12/2018
 */
public class DirtTile extends OverlapTile {

    public DirtTile(TutEngine tutEngine) {
        super(tutEngine, Assets.GRASS, "dirt", false, Tile.TileType.GROUND, Assets.GRASS_ALTS);
        this.setMapColor(WorldColors.DIRT);
        this.setConnect("grass");
        this.setIgnore("dirt");
    }

    @Override
    public DirtTile newCopy() {
        return super.newCopy(new DirtTile(tutEngine));
    }
}
