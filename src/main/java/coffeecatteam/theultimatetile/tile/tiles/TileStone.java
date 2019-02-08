package coffeecatteam.theultimatetile.tile.tiles;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.manager.ItemManager;
import org.newdawn.slick.Image;

/**
 * @author CoffeeCatRailway
 * Created: 24/12/2018
 */
public abstract class TileStone extends TileOverlap {

    public TileStone(TutEngine tutEngine, Image texture, String id, boolean overlap) {
        super(tutEngine, texture, Assets.BROKEN_STONE, id, true, TileType.STONE, Assets.BROKEN_STONE_ALTS);

        if (overlap) {
            this.setConnect(TileBrokenStone.class);
            this.setIgnore(TileStone.class);
        }

        this.setDrop(ItemManager.ROCK);
    }
}
