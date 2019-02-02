package coffeecatteam.theultimatetile.game.tile.tiles;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.tile.Tile;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.manager.ItemManager;

/**
 * @author CoffeeCatRailway
 * Created: 22/12/2018
 */
public class TileBrokenStone extends Tile {

    public TileBrokenStone(Engine engine, String id) {
        super(engine, null, id, true, TileType.STONE);
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
        return (TileBrokenStone) super.newTile(new TileBrokenStone(engine, id));
    }
}
