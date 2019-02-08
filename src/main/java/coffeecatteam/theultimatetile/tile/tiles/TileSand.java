package coffeecatteam.theultimatetile.tile.tiles;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.gfx.assets.Assets;

/**
 * @author CoffeeCatRailway
 * Created: 24/12/2018
 */
public class TileSand extends TileOverlap {

    public TileSand(TutEngine tutEngine, String id) {
        super(tutEngine, null, Assets.GRASS, id, false, TileType.GROUND, Assets.GRASS_ALTS);
        this.setConnect(TileGrass.class);
        this.setIgnore(TileSand.class);
    }

    @Override
    public void init() {
        this.setHasAlts(true);
        this.addTextureAlts(Assets.SAND, Assets.SAND_ALTS);

        super.init();
    }

    @Override
    public TileSand newTile() {
        return (TileSand) super.newTile(new TileSand(tutEngine, id));
    }
}
