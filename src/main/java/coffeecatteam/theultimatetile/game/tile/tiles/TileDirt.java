package coffeecatteam.theultimatetile.game.tile.tiles;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.tile.Tile;
import coffeecatteam.theultimatetile.gfx.assets.Assets;

/**
 * @author CoffeeCatRailway
 * Created: 22/12/2018
 */
public class TileDirt extends TileOverlap {

    public TileDirt(Engine engine, String id) {
        super(engine, Assets.DIRT, Assets.GRASS, id, false, Tile.TileType.GROUND, Assets.GRASS_ALTS);
        this.setConnect(TileGrass.class);
        this.setIgnore(TileDirt.class);
    }

    @Override
    public TileDirt newTile() {
        return (TileDirt) super.newTile(new TileDirt(engine, id));
    }
}
