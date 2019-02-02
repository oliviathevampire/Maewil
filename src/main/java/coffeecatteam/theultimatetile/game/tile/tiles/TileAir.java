package coffeecatteam.theultimatetile.game.tile.tiles;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.tile.Tile;
import coffeecatteam.theultimatetile.gfx.assets.Assets;

/**
 * @author CoffeeCatRailway
 * Created: 23/12/2018
 */
public class TileAir extends Tile {

    public TileAir(Engine engine) {
        super(engine, Assets.AIR, "air", false, Tile.TileType.AIR);
    }

    @Override
    public TileAir newTile() {
        return (TileAir) super.newTile(new TileAir(engine));
    }
}
