package coffeecatteam.theultimatetile.game.tile.tiles;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.gfx.assets.Assets;

/**
 * @author CoffeeCatRailway
 * Created: 22/12/2018
 */
public class TileDirt extends TileOverlap {

    public TileDirt(Engine engine, String id, boolean isSolid, TileType tileType) {
        super(engine, Assets.DIRT, Assets.GRASS, id, isSolid, tileType, Assets.GRASS_ALTS);
        this.setConnect(TileGrass.class);
        this.setIgnore(TileDirt.class);
    }

    @Override
    public TileDirt copy() {
        return new TileDirt(engine, id, isSolid, tileType);
    }
}
