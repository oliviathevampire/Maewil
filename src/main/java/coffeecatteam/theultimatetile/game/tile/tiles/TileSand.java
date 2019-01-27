package coffeecatteam.theultimatetile.game.tile.tiles;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.gfx.assets.Assets;

/**
 * @author CoffeeCatRailway
 * Created: 24/12/2018
 */
public class TileSand extends TileOverlap {

    public TileSand(Engine engine, String id) {
        super(engine, Assets.SAND[0], Assets.GRASS, id, false, TileType.GROUND, Assets.GRASS_ALTS);
        this.setConnect(TileGrass.class);
        this.setIgnore(TileSand.class);
    }

    @Override
    public TileSand newTile() {
        return (TileSand) new TileSand(engine, id).setMapColor(mapColor);
    }
}
