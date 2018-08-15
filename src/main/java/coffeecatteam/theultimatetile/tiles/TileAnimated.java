package coffeecatteam.theultimatetile.tiles;

import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.Assets;

import java.awt.*;

public class TileAnimated extends Tile {

    private Animation animation;

    public TileAnimated(Animation animation, int id) {
        super(Assets.WATER[0], id);
        this.animation = animation;
    }

    @Override
    public void tick() {
        animation.tick();
    }

    @Override
    public void render(Graphics g, int x, int y) {
        g.drawImage(animation.getCurrentFrame(), x, y, TILE_WIDTH, TILE_HEIGHT, null);
    }
}
