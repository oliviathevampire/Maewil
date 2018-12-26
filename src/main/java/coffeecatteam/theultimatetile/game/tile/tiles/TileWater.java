package coffeecatteam.theultimatetile.game.tile.tiles;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.tile.Tile;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.assets.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author CoffeeCatRailway
 * Created: 26/12/2018
 */
public class TileWater extends TileOverlap {

    private Animation animation;

    public TileWater(Engine engine, String id, boolean isSolid, TileType tileType) {
        super(engine, null, Assets.SAND, id, isSolid, tileType, Assets.SAND_ALTS);
        this.setConnect(TileSand.class, TileGrass.class);
        this.setIgnore(TileWater.class);

        this.animation = new Animation(50, Assets.WATER);
    }

    @Override
    public void forcedTick() {
        animation.tick();
    }

    @Override
    public void render(Graphics2D g, int x, int y, int width, int height) {
        g.drawImage(animation.getCurrentFrame(), x, y, width, height, null);
        super.render(g, x, y, width, height);
    }

    @Override
    public BufferedImage getTexture() {
        return animation.getFrames()[0];
    }

    public Animation getAnimation() {
        return animation;
    }

    @Override
    public TileWater copy() {
        return new TileWater(engine, id, isSolid, tileType);
    }
}
