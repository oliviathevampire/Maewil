package coffeecatteam.theultimatetile.game.tile.tiles;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.tile.Tile;
import coffeecatteam.theultimatetile.gfx.assets.Assets;

/**
 * @author CoffeeCatRailway
 * Created: 22/12/2018
 */
public class TileGrass extends Tile {

    public TileGrass(Engine engine, String id) {
        super(engine, null, id, false, Tile.TileType.GROUND);

        int max = 1000, chance = 850;
        int i = NumberUtils.getRandomInt(max);
        if (i < chance)
            this.texture = Assets.GRASS[0];
        else
            this.texture = Assets.GRASS[(int) NumberUtils.map(i, chance, max, 1, Assets.GRASS_ALTS - 1)];
    }

    @Override
    public TileGrass newTile() {
        return new TileGrass(engine, id);
    }
}
