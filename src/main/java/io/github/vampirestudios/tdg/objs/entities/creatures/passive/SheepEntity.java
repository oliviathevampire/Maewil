package io.github.vampirestudios.tdg.objs.entities.creatures.passive;

import io.github.vampirestudios.tdg.objs.entities.Entity;
import io.github.vampirestudios.tdg.objs.entities.ai.FollowFleeGoal;
import io.github.vampirestudios.tdg.objs.entities.creatures.EntityPassive;
import io.github.vampirestudios.tdg.objs.items.Items;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import org.mini2Dx.core.game.GameContainer;

public class SheepEntity extends EntityPassive {

    private FollowFleeGoal aiFollowFlee;

    public SheepEntity(MaewilEngine maewilEngine) {
        super(maewilEngine, "sheep");
        this.drop = Items.WOOL_BUNDLE;
        aiFollowFlee = new FollowFleeGoal(maewilEngine, this, maewilEngine.getPlayer(), 100f, 3.5f).setFlee();
    }

    @Override
    public void update(GameContainer container, float delta) {
        xMove = 0;
        yMove = 0;

        // Movement
        if (maewilEngine.getPlayer().isActive()) {
            if (TAGS.hasKey("fleePlayer") && TAGS.getBoolean("fleePlayer")) {
                if (!aiFollowFlee.update(container, delta))
                    aiWander.update(container, delta);
            } else
                aiWander.update(container, delta);
        }
        move();
    }

    @Override
    public Entity newCopy() {
        return super.newCopy(new SheepEntity(maewilEngine));
    }
}
