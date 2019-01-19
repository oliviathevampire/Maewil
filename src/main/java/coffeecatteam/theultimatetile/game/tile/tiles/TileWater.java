package coffeecatteam.theultimatetile.game.tile.tiles;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.assets.Assets;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

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
    public void forcedUpdate(GameContainer container, int delta) {
        animation.update(container, delta);
    }

    @Override
    public void render(Graphics g, int x, int y, int width, int height) {
        animation.getCurrentFrame().draw(x, y, width, height);
        super.render(g, x, y, width, height);
    }

    @Override
    public Image getTexture() {
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
