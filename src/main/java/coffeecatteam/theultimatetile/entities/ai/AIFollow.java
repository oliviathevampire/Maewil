package coffeecatteam.theultimatetile.entities.ai;

import coffeecatteam.theultimatetile.entities.creatures.EntityCreature;

public class AIFollow extends AI {

    private EntityCreature follow;
    private float maxDistance, speed;

    public AIFollow(EntityCreature entity, EntityCreature follow) {
        this(entity, follow, 200f, 2.0f);
    }

    public AIFollow(EntityCreature entity, EntityCreature follow, float maxDistance, float speed) {
        super(entity);
        this.follow = follow;
        this.maxDistance = maxDistance;
        this.speed = speed;
    }

    @Override
    public boolean tick() {
        float x = follow.getX() - entity.getX();
        float y = follow.getY() - entity.getY();

        float distance = (float) Math.sqrt(x * x + y * y);
        float multiplier = speed / distance;

        boolean inRange = distance < maxDistance;
        if (inRange) {
            entity.setxMove(x * multiplier);
            entity.setyMove(y * multiplier);
        }
        return inRange;
    }

    public void setMaxDistance(float maxDistance) {
        this.maxDistance = maxDistance;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
