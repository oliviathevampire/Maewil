package coffeecatteam.theultimatetile.objs.entities.statics;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.objs.entities.Entity;
import org.newdawn.slick.GameContainer;

/**
 * @author CoffeeCatRailway
 * Created: 12/12/2018
 */
public class EntityExtraLife extends EntityStatic {

    public EntityExtraLife(TutEngine tutEngine) {
        super(tutEngine, "extra_life", Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT, EntityHitType.NONE);
        setCurrentTexture("main");
        isCollidable = false;
    }

    @Override
    public void update(GameContainer container, int delta) {
        if (this.isTouching(tutEngine.getPlayer())) {
            tutEngine.getPlayer().heal(NumberUtils.getRandomInt(DEFAULT_HEALTH / 2, DEFAULT_HEALTH));
            this.hurt(this.currentHealth);
        }
    }

    @Override
    public Entity newCopy() {
        return super.newCopy(new EntityExtraLife(tutEngine));
    }
}
