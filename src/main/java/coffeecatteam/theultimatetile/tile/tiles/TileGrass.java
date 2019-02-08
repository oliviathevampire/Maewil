package coffeecatteam.theultimatetile.tile.tiles;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.tile.Tile;
import coffeecatteam.theultimatetile.gfx.assets.Assets;

/**
 * @author CoffeeCatRailway
 * Created: 22/12/2018
 */
public class TileGrass extends Tile {

    public TileGrass(TutEngine tutEngine, String id) {
        super(tutEngine, null, id, false, Tile.TileType.GROUND);
    }

    @Override
    public void init() {
        this.setHasAlts(true);
        this.addTextureAlts(Assets.GRASS, Assets.GRASS_ALTS);

        super.init();
    }

    @Override
    public TileGrass newTile() {
        return (TileGrass) super.newTile(new TileGrass(tutEngine, id));
    }
}
