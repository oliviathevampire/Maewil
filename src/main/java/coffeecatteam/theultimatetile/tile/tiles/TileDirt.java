package coffeecatteam.theultimatetile.tile.tiles;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.tile.Tile;
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
    public TileDirt newTile() {
        return super.newTile(new TileDirt(tutEngine));
    }
}
