package coffeecatteam.tilegame.entities.statics;

import coffeecatteam.tilegame.Handler;
import coffeecatteam.tilegame.entities.Entity;
import coffeecatteam.tilegame.gfx.Animation;
import coffeecatteam.tilegame.gfx.Assets;

import java.awt.*;

public class EntityUltimateTile extends EntityStatic {

    private Animation animation;

    public EntityUltimateTile(Handler handler, String id) {
        super(handler, id, Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT);

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
    public void render(Graphics g) {
        g.drawImage(animation.getCurrentFrame(), (int) (this.x - handler.getCamera().getxOffset()), (int) (this.y - handler.getCamera().getyOffset()), width, height, null);
    }
}
