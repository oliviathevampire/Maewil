package coffeecatteam.theultimatetile.tile.tiles;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;

/**
 * @author CoffeeCatRailway
 * Created: 26/12/2018
 */
public class TileWater extends TileOverlap {

    private Animation animation;

    public TileWater(TutEngine tutEngine, String id) {
        super(tutEngine, null, Assets.SAND, id, false, TileType.FLUID, Assets.SAND_ALTS);
        this.setConnect(TileSand.class, TileGrass.class);
        this.setIgnore(TileWater.class);

        this.animation = new Animation(50, Assets.WATER);
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
    public TileWater newTile() {
        return (TileWater) super.newTile(new TileWater(tutEngine, id));
    }
}
