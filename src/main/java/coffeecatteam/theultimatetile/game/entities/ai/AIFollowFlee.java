package coffeecatteam.theultimatetile.game.entities.ai;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.entities.creatures.EntityCreature;
import org.newdawn.slick.GameContainer;

public class AIFollowFlee extends AI {

    private EntityCreature target;
    private float maxDistance, speed;
    private boolean flee = false;

    public AIFollowFlee(Engine engine, EntityCreature entity, EntityCreature target) {
        this(engine, entity, target, 200f, 2.0f);
    }

    public AIFollowFlee(Engine engine, EntityCreature entity, EntityCreature target, float maxDistance, float speed) {
        super(engine, entity);
        this.target = target;
        this.maxDistance = maxDistance;
        this.speed = speed;
    }

    @Override
    public boolean update(GameContainer container, int delta) {
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

    public AIFollowFlee setFlee() {
        this.flee = true;
        return this;
    }
}
