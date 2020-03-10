package io.github.vampirestudios.tdg.objs.entities.ai;

import io.github.vampirestudios.tdg.objs.entities.creatures.LivingEntity;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public class FollowFleeGoal extends Goal {

    private LivingEntity target;
    private float maxDistance, speed;
    private boolean flee = false;

    public FollowFleeGoal(MaewilEngine maewilEngine, LivingEntity entity, LivingEntity target) {
        this(maewilEngine, entity, target, 200f, 2.0f);
    }

    public FollowFleeGoal(MaewilEngine maewilEngine, LivingEntity entity, LivingEntity target, float maxDistance, float speed) {
        super(maewilEngine, entity);
        this.target = target;
        this.maxDistance = maxDistance;
        this.speed = speed;
    }

    @Override
    public boolean update(GameContainer container, StateBasedGame game, int delta) {
        float x = target.getX() - entity.getX();
        float y = target.getY() - entity.getY();

        float distance = (float) Math.sqrt(x * x + y * y);
        float multiplier = speed / distance;

        boolean inRange = distance < maxDistance;
        if (flee) {
            x = -x;
            y = -y;
        }
        if (inRange) {
            entity.setxMove(x * multiplier);
            entity.setyMove(y * multiplier);
        }
        return inRange;
    }

    public float getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(float maxDistance) {
        this.maxDistance = maxDistance;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public FollowFleeGoal setFlee() {
        this.flee = true;
        return this;
    }
}
