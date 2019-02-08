package coffeecatteam.theultimatetile.game.entities.statics;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.coffeecatutils.position.AABB;
import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.game.entities.Entity;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * @author CoffeeCatRailway
 * Created: 12/12/2018
 */
public class EntityExtraLife extends EntityStatic {

    private Animation animation;

    public EntityExtraLife(TutEngine tutEngine, String id) {
        super(tutEngine, id, Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT, EntityHitType.NONE);

        animation = new Animation(135, Assets.EXTRA_LIFE);
        isCollidable = false;
    }

    @Override
    public void update(GameContainer container, int delta) {
        bounds = new AABB(this.position, width, height);

        animation.update(container, delta);
        if (this.isTouching(((TutEngine) TutEngine).getEntityManager().getPlayer())) {
            ((TutEngine) TutEngine).getEntityManager().getPlayer().heal(NumberUtils.getRandomInt(DEFAULT_HEALTH / 2, DEFAULT_HEALTH));
            this.hurt(this.currentHealth);
        }
    }

    @Override
    public void render(Graphics g) {
        animation.getCurrentFrame().draw(this.renderX, this.renderY, width, height);
    }
}
