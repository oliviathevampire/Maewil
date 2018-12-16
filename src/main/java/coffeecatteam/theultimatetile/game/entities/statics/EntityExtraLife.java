package coffeecatteam.theultimatetile.game.entities.statics;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.coffeecatutils.position.AABB;
import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.entities.Entity;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.Assets;

import java.awt.*;

/**
 * @author CoffeeCatRailway
 * Created: 12/12/2018
 */
public class EntityExtraLife extends EntityStatic {

    private Animation animation;

    public EntityExtraLife(Engine engine, String id) {
        super(engine, id, Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT, EntityHitType.NONE);

        animation = new Animation(135, Assets.EXTRA_LIFE);
        isCollidable = false;
    }

    @Override
    public void tick() {
        bounds = new AABB(this.position, width, height);

        animation.tick();
        if (this.isTouching(((GameEngine) engine).getEntityManager().getPlayer())) {
            ((GameEngine) engine).getEntityManager().getPlayer().heal(NumberUtils.getRandomInt(DEFAULT_HEALTH / 2, DEFAULT_HEALTH));
            this.hurt(this.currentHealth);
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(animation.getCurrentFrame(), this.renderX, this.renderY, width, height, null);
    }
}
