package coffeecatteam.theultimatetile.tile.tiles;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.tile.Tile;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.manager.ItemManager;

/**
 * @author CoffeeCatRailway
 * Created: 22/12/2018
 */
public class TileBrokenStone extends Tile {

    public TileBrokenStone(TutEngine tutEngine, String id) {
        super(tutEngine, null, id, true, TileType.STONE);
        this.setDrop(ItemManager.ROCK);
    }

    @Override
    public void init() {
        this.setHasAlts(true);
        this.addTextureAlts(Assets.BROKEN_STONE, Assets.BROKEN_STONE_ALTS);

        super.init();
    }

    @Override
    public TileBrokenStone newTile() {
        return (TileBrokenStone) super.newTile(new TileBrokenStone(tutEngine, id));
    }
}
