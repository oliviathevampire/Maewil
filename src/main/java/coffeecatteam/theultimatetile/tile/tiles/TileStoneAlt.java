package coffeecatteam.theultimatetile.tile.tiles;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.gfx.assets.Assets;

/**
 * @author CoffeeCatRailway
 * Created: 24/12/2018
 */
public class TileStoneAlt extends TileStone {

    public TileStoneAlt(TutEngine tutEngine, String id) {
        super(tutEngine, null, id, true);
    }

    @Override
    public void init() {
        this.setHasAlts(true);
        this.addTextureAlts(Assets.STONE);

        super.init();
    }

    @Override
    public TileStoneAlt newTile() {
        return (TileStoneAlt) super.newTile(new TileStoneAlt(tutEngine, id));
    }
}
