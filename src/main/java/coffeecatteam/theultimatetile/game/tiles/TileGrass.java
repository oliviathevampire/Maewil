package coffeecatteam.theultimatetile.game.tiles;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.gfx.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author CoffeeCatRailway
 * Created: 22/12/2018
 */
public class TileGrass extends Tile {

    public TileGrass(Engine engine, String id) {
        super(engine, getGTexture(), id, false, Tile.TileType.GROUND);
    }

    private static BufferedImage getGTexture() {
        BufferedImage texture;
        int ti = NumberUtils.getRandomInt(100);
        if (ti <= 75) {
            texture = Assets.GRASS[0];
        } else if (ti <= 88) {
            texture = Assets.GRASS[1];
        } else {
            texture = Assets.GRASS[2];
        }
        return texture;
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);
    }
}
