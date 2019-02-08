package coffeecatteam.theultimatetile.tile.tiles;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.tile.Tile;
import coffeecatteam.theultimatetile.gfx.assets.Assets;

/**
 * @author CoffeeCatRailway
 * Created: 23/12/2018
 */
public class TileAir extends Tile {

    public TileAir(TutEngine tutEngine) {
        super(tutEngine, Assets.AIR, "air", false, Tile.TileType.AIR);
    }

    @Override
    public TileAir newTile() {
        return (TileAir) super.newTile(new TileAir(tutEngine));
    }
}
