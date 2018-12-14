package coffeecatteam.theultimatetile.game.entities.ai;

import coffeecatteam.coffeecatutils.Logger;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.entities.Entity;
import coffeecatteam.theultimatetile.game.entities.creatures.EntityCreature;
import coffeecatteam.theultimatetile.game.entities.statics.nature.EntityCrop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author CoffeeCatRailway
 * Created: 12/12/2018
 */
public class AIEatCrops extends AI {

    private float maxDistance, speed;
    private List<String> cropIds = new ArrayList<>();

    public AIEatCrops(EntityCreature entity) {
        this(entity, 300f, 2.0f);
    }

    public AIEatCrops(EntityCreature entity, float maxDistance, float speed) {
        super(entity);
        this.maxDistance = maxDistance;
        this.speed = speed;
    }

    public void setCropIds(String[] cropIds) {
        this.cropIds.addAll(Arrays.asList(cropIds));
    }

    public void setCropIds(List<String> cropIds) {
        this.cropIds = cropIds;
    }

    public List<String> getCropIds() {
        return cropIds;
    }

    @Override
    public boolean tick() {
        EntityCrop closestCrop = getClosestCrop();
        if (closestCrop != null) {
            float x = closestCrop.getX() - entity.getX();
            float y = closestCrop.getY() - entity.getY();

            float distance = getDistance(entity, closestCrop);
            float multiplier = speed / distance;

            boolean inRange = (distance > 10f) && (distance < maxDistance);
            if (inRange) {
                entity.setxMove(x * multiplier);
                entity.setyMove(y * multiplier);
                if (closestCrop.isTouching(entity)) {
                    Logger.print("EATING");
                    closestCrop.hurt(closestCrop.getCurrentHealth());
                }
            }
            return inRange;
        }
        return false;
    }

    private EntityCrop getClosestCrop() {
        EntityCrop closest = null;
        float lastDistance = maxDistance;

        for (Entity entity : GameEngine.getGameEngine().getEntityManager().getEntities()) {
            if (entity instanceof EntityCrop) {
                EntityCrop crop = (EntityCrop) entity;
                float dist = getDistance(entity, crop);
                if (dist < lastDistance) {
                    lastDistance = dist;
                    if (cropIds.contains(crop.getId()))
                        closest = crop;
                }
            }
        }

        return closest;
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
}
