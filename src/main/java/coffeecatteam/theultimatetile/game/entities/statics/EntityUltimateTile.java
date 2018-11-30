package coffeecatteam.theultimatetile.game.entities.statics;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.entities.Entity;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.Assets;

import java.awt.*;

public class EntityUltimateTile extends EntityStatic {

    private Animation animation;

    public EntityUltimateTile(Engine engine, String id) {
        super(engine, id, Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT, EntityHitType.NONE);

        bounds.x = 0;
        bounds.y = height / 2;
        bounds.width = width;
        bounds.height = height / 2;

        animation = new Animation(135, Assets.ULTIMATE_TILE);
        isCollidable = false;
    }

    @Override
    public void tick() {
        animation.tick();
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(animation.getCurrentFrame(), this.renderX, this.renderY, width, height, null);
    }
}
