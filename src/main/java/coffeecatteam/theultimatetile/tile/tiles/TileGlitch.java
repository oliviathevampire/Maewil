package coffeecatteam.theultimatetile.tile.tiles;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.tile.Tile;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;

public class TileGlitch extends Tile {

    private Animation animation;

    public TileGlitch(TutEngine tutEngine, String id) {
        super(tutEngine, Assets.BINARY[0], id, false, TileType.GROUND);
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
        return (TileGlitch) super.newTile(new TileGlitch(tutEngine, id));
    }
}
