package io.github.vampirestudios.tdg.objs.entities.creatures.passive;

import io.github.vampirestudios.tdg.objs.entities.Entity;
import io.github.vampirestudios.tdg.objs.entities.ai.FollowFleeGoal;
import io.github.vampirestudios.tdg.objs.entities.creatures.EntityPassive;
import io.github.vampirestudios.tdg.objs.items.Items;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public class SheepEntity extends EntityPassive {

    private FollowFleeGoal aiFollowFlee;

    public SheepEntity(MaewilEngine maewilEngine) {
        super(maewilEngine, "sheep");
        this.drop = Items.WOOL_BUNDLE;
        aiFollowFlee = new FollowFleeGoal(maewilEngine, this, maewilEngine.getPlayer(), 100f, 3.5f).setFlee();
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {
        xMove = 0;
        yMove = 0;

        // Movement
        if (maewilEngine.getPlayer().isActive()) {
            if (TAGS.hasKey("fleePlayer") && TAGS.getBoolean("fleePlayer")) {
                if (!aiFollowFlee.update(container, game, delta))
                    aiWander.update(container, game, delta);
            } else
                aiWander.update(container, game, delta);
        }
        move();
    }

    @Override
    public Entity newCopy() {
        return super.newCopy(new SheepEntity(maewilEngine));
    }
}
