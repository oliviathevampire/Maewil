package coffeecatteam.theultimatetile.game.tile.tiles;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.tile.Tile;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;

public class TileLava extends Tile {

    private Animation animation;

    public TileLava(Engine engine, String id) {
        super(engine, Assets.LAVA[0], id, false, Tile.TileType.FLUID);
        this.animation = new Animation(50, Assets.LAVA);
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
    public TileLava newTile() {
        return (TileLava) super.newTile(new TileLava(engine, id));
    }
}
