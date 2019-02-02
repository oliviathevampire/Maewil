package coffeecatteam.theultimatetile.game.tile.tiles;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.tile.Tile;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;

public class TileGlitch extends Tile {

    private Animation animation;

    public TileGlitch(Engine engine, String id) {
        super(engine, Assets.BINARY[0], id, false, TileType.GROUND);
        this.animation = new Animation(75, Assets.BINARY);
    }

    @Override
    public void forcedUpdate(GameContainer container, int delta) {
        animation.update(container, delta);
    }

    @Override
    public Image getTexture() {
        return animation.getCurrentFrame();
    }

    public Animation getAnimation() {
        return animation;
    }

    @Override
    public TileGlitch newTile() {
        return (TileGlitch) super.newTile(new TileGlitch(engine, id));
    }
}
