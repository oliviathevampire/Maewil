package coffeecatteam.theultimatetile.game.tile.tiles;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.tile.Tile;
import coffeecatteam.theultimatetile.gfx.assets.Assets;

/**
 * @author CoffeeCatRailway
 * Created: 22/12/2018
 */
public class TileGrass extends Tile {

    public TileGrass(Engine engine, String id) {
        super(engine, null, id, false, Tile.TileType.GROUND);
    }

    @Override
    public void init() {
        this.setHasAlts(true);
        this.addTextureAlts(Assets.GRASS, Assets.GRASS_ALTS);

        super.init();
    }

    @Override
    public TileGrass newTile() {
        return (TileGrass) super.newTile(new TileGrass(engine, id));
    }
}
