package coffeecatteam.theultimatetile.game.tile.tiles;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.tile.Tile;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.manager.ItemManager;
import org.newdawn.slick.Image;

import java.util.Arrays;

/**
 * @author CoffeeCatRailway
 * Created: 24/12/2018
 */
public abstract class TileStone extends TileOverlap {

    public TileStone(Engine engine, Image texture, String id, boolean overlap) {
        super(engine, texture, Assets.BROKEN_STONE, id, true, TileType.STONE, Assets.BROKEN_STONE_ALTS);

        if (overlap) {
            this.setConnect(TileBrokenStone.class);
            this.setIgnore(TileStone.class);
        }

        this.setDrop(ItemManager.ROCK);
    }
}
