package coffeecatteam.theultimatetile.tile.tiles;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.tile.Tile;
import org.newdawn.slick.Image;

/**
 * @author CoffeeCatRailway
 * Created: 24/12/2018
 */
public class TileWood extends Tile {

    public TileWood(TutEngine tutEngine, Image texture, String id) {
        super(tutEngine, texture, id, true, TileType.WOOD);
    }

    @Override
    public TileWood newTile() {
        return (TileWood) super.newTile(new TileWood(tutEngine, texture, id));
    }
}
