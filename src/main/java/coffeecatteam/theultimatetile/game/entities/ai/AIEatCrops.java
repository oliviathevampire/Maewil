package coffeecatteam.theultimatetile.game.entities.ai;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.entities.Entity;
import coffeecatteam.theultimatetile.game.entities.creatures.EntityCreature;
import coffeecatteam.theultimatetile.game.entities.statics.nature.EntityCrop;
import coffeecatteam.theultimatetile.game.tags.TagList;
import org.newdawn.slick.GameContainer;

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

    public AIEatCrops(Engine engine, EntityCreature entity) {
        this(engine, entity, 300f, 2.0f);
    }

    public AIEatCrops(Engine engine, EntityCreature entity, float maxDistance, float speed) {
        super(engine, entity);
        this.maxDistance = maxDistance;
        this.speed = speed;
    }

    public List<String> getCropIds() {
        return cropIds;
    }

    public void setCropIds(String[] cropIdsIn) {
        cropIds.addAll(Arrays.asList(cropIdsIn));
    }

    public void setCropIds(TagList cropIdsIn) {
        cropIdsIn.forEach(tag -> {
            cropIds.add(tag.getString());
        });
    }

    public void setCropIds(List<String> cropIdsIn) {
        cropIds = cropIdsIn;
    }

    @Override
    public boolean update(GameContainer container, int delta) {
        EntityCrop closestCrop = getClosestCrop();
        if (closestCrop != null) {
            float x = closestCrop.getX() - entity.getX();
            float y = closestCrop.getY() - entity.getY();

            float distance = getDistance(entity, closestCrop);
            float multiplier = speed / distance;

            boolean inRange = (distance > 20f) && (distance < maxDistance) && canTrigger();
            if (inRange) {
                entity.setxMove(x * multiplier);
                entity.setyMove(y * multiplier);
                if (closestCrop.isTouching(entity)) {
                    closestCrop.hurt(NumberUtils.getRandomInt(closestCrop.getMaxHealth() / 4));
                }
            }
            return inRange;
        }
        return false;
    }

    private boolean trigger = false;

    private boolean canTrigger() {
        float percentage = trigger ? 20.0f : 1000.0f;
        if (NumberUtils.getRandomFloat(percentage) < 1.0f)
            trigger = !trigger;
        return trigger;
    }

    private EntityCrop getClosestCrop() {
        EntityCrop closest = null;
        float lastDistance = maxDistance;

        for (Entity e : GameEngine.getGameEngine().getEntityManager().getEntities()) {
            if (e instanceof EntityCrop) {
                EntityCrop crop = (EntityCrop) e;
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
