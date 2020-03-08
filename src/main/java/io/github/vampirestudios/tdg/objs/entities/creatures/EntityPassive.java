package io.github.vampirestudios.tdg.objs.entities.creatures;

import coffeecatteam.coffeecatutils.position.AABB;
import io.github.vampirestudios.tdg.objs.entities.Entity;
import io.github.vampirestudios.tdg.objs.entities.ai.WanderGoal;
import io.github.vampirestudios.tdg.start.MaewilEngine;

public abstract class EntityPassive extends LivingEntity {

    // Goal
    protected WanderGoal aiWander;

    public EntityPassive(MaewilEngine maewilEngine, String id) {
        super(maewilEngine, id, Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT);
        aiWander = new WanderGoal(maewilEngine, this, 1.5f);
    }

    @Override
    public AABB getTileBounds() {
        return new AABB(0, height - ((height / 4f) * 3), width, (height / 4) * 3);
    }

    @Override
    public void update(org.mini2Dx.core.game.GameContainer container, float delta) {
        xMove = 0;
        yMove = 0;

        // Movement
        if (maewilEngine.getPlayer().isActive())
            aiWander.update(container, delta);
        move();
    }
}
