package coffeecatteam.theultimatetile.tiles;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.Assets;

import java.awt.*;

public class TileAnimated extends Tile {

    private Animation animation;

    public TileAnimated(TheUltimateTile theUltimateTile, Animation animation, int id, boolean isSolid, TileType tileType) {
        super(theUltimateTile, Assets.WATER[0], id, isSolid, tileType);
        this.animation = animation;
    }

    @Override
    public void tick() {
        animation.tick();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(animation.getCurrentFrame(), (int) (x * Tile.TILE_WIDTH - theUltimateTile.getCamera().getxOffset()), (int) (y * Tile.TILE_HEIGHT - theUltimateTile.getCamera().getyOffset()), TILE_WIDTH, TILE_HEIGHT, null);
    }
}
