package coffeecatteam.theultimatetile.game.tile.tiles;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.tile.Tile;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.assets.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TileLava extends Tile {

    private Animation animation;

    public TileLava(Engine engine, Animation animation, String id) {
        super(engine, Assets.LAVA[0], id, false, Tile.TileType.FLUID);
        this.animation = animation;
    }

    @Override
    public void forcedTick() {
        animation.tick();
    }

    @Override
    public void render(Graphics2D g, int x, int y, int width, int height) {
        g.drawImage(animation.getCurrentFrame(), x, y, width, height, null);
    }

    @Override
    public BufferedImage getTexture() {
        return animation.getFrames()[0];
    }

    public Animation getAnimation() {
        return animation;
    }

    @Override
    public TileLava copy() {
        return new TileLava(engine, animation, id);
    }
}
