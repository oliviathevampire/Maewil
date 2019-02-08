package coffeecatteam.theultimatetile.tile.tiles;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.tile.Tile;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;

public class TileLava extends Tile {

    private Animation animation;

    public TileLava(TutEngine tutEngine, String id) {
        super(tutEngine, Assets.LAVA[0], id, false, Tile.TileType.FLUID);
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
        return (TileLava) super.newTile(new TileLava(tutEngine, id));
    }
}
