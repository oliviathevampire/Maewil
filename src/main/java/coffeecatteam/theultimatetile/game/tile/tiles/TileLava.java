package coffeecatteam.theultimatetile.game.tile.tiles;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.tile.Tile;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.assets.Assets;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class TileLava extends Tile {

    private Animation animation;

    public TileLava(Engine engine, Animation animation, String id) {
        super(engine, Assets.LAVA[0], id, false, Tile.TileType.FLUID);
        this.animation = animation;
    }

    @Override
    public void forcedUpdate(GameContainer container, int delta) {
        animation.update(container, delta);
    }

    @Override
    public void render(Graphics g, int x, int y, int width, int height) {
        animation.getCurrentFrame().draw(x, y, width, height);
    }

    @Override
    public Image getTexture() {
        return animation.getFrames()[0];
    }

    public Animation getAnimation() {
        return animation;
    }

    @Override
    public TileLava newTile() {
        return (TileLava) new TileLava(engine, animation, id).setMapColor(mapColor);
    }
}
