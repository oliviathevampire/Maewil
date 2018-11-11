package coffeecatteam.theultimatetile.tiles;

import coffeecatteam.theultimatetile.GameEngine;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.Assets;

import java.awt.*;

public class TileAnimated extends Tile {

    private Animation animation;

    public TileAnimated(GameEngine gameEngine, Animation animation, String id, boolean isSolid, TileType tileType) {
        super(gameEngine, Assets.WATER[0], id, isSolid, tileType);
        this.animation = animation;
    }

    @Override
    public void tick() {
        animation.tick();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(animation.getCurrentFrame(), (int) (x * Tile.TILE_WIDTH - gameEngine.getCamera().getxOffset()), (int) (y * Tile.TILE_HEIGHT - gameEngine.getCamera().getyOffset()), TILE_WIDTH, TILE_HEIGHT, null);
    }
}
