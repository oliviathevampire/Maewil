package io.github.vampirestudios.tdg.objs.entities.statics;

import coffeecatteam.coffeecatutils.NumberUtils;
import io.github.vampirestudios.tdg.objs.entities.Entity;
import io.github.vampirestudios.tdg.start.MaewilEngine;

public class ExtraLifeEntity extends StaticEntity {

    private int healAmt;

    public ExtraLifeEntity(MaewilEngine maewilEngine) {
        super(maewilEngine, "extra_life", Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT, Entity.HitType.NONE);
        setCurrentTexture("main");
        isCollidable = false;

        healAmt = NumberUtils.getRandomInt(Entity.DEFAULT_HEALTH / 4, Entity.DEFAULT_HEALTH);
    }

    @Override
    public void update(org.mini2Dx.core.game.GameContainer container, float delta) {
        if (this.isTouching(maewilEngine.getPlayer())) {
            maewilEngine.getPlayer().heal(healAmt);
            this.hurt(getCurrentHealth());
        }
    }

    @Override
    public Entity newCopy() {
        return super.newCopy(new ExtraLifeEntity(maewilEngine));
    }
}
