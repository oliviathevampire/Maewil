package coffeecatteam.theultimatetile.game.entities.statics;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.game.entities.Entity;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class EntityUltimateTile extends EntityStatic {

    private Animation animation;

    public EntityUltimateTile(TutEngine tutEngine, String id) {
        super(tutEngine, id, Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT, EntityHitType.NONE);

        animation = new Animation(135, Assets.ULTIMATE_TILE);
        isCollidable = false;
    }

    @Override
    public void update(GameContainer container, int delta) {
        animation.update(container, delta);
    }

    @Override
    public void render(Graphics g) {
        animation.getCurrentFrame().draw(this.renderX, this.renderY, width, height);
    }
}
