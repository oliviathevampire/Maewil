package coffeecatteam.theultimatetile.game.tiles;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TileAnimated extends Tile {

    private Animation animation;

    public TileAnimated(Engine engine, Animation animation, String id, boolean isSolid, TileType tileType) {
        super(engine, Assets.WATER[0], id, isSolid, tileType);
        this.animation = animation;
    }

    @Override
    public void forcedTick() {
        animation.tick();
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(animation.getCurrentFrame(), (int) (position.x * Tile.TILE_WIDTH - ((GameEngine) engine).getCamera().getxOffset()), (int) (position.y * Tile.TILE_HEIGHT - ((GameEngine) engine).getCamera().getyOffset()), TILE_WIDTH, TILE_HEIGHT, null);
    }

    @Override
    public BufferedImage getTexture() {
        return animation.getFrames()[0];
    }

    public Animation getAnimation() {
        return animation;
    }
}
