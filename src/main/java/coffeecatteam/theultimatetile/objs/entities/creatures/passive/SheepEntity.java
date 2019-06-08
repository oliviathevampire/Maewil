package coffeecatteam.theultimatetile.objs.entities.creatures.passive;

import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.objs.entities.ai.FollowFleeGoal;
import coffeecatteam.theultimatetile.objs.entities.creatures.EntityPassive;
import coffeecatteam.theultimatetile.objs.items.Items;
import coffeecatteam.theultimatetile.start.TutEngine;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public class SheepEntity extends EntityPassive {

    private FollowFleeGoal aiFollowFlee;

    public SheepEntity(TutEngine tutEngine) {
        super(tutEngine, "sheep");
        this.drop = Items.WOOL_BUNDLE;
        aiFollowFlee = new FollowFleeGoal(tutEngine, this, tutEngine.getPlayer(), 100f, 3.5f).setFlee();
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {
        xMove = 0;
        yMove = 0;

        // Movement
        if (tutEngine.getPlayer().isActive()) {
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
        return super.newCopy(new SheepEntity(tutEngine));
    }
}
