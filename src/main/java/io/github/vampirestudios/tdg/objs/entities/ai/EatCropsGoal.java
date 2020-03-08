package io.github.vampirestudios.tdg.objs.entities.ai;

import coffeecatteam.coffeecatutils.NumberUtils;
import io.github.vampirestudios.tdg.objs.entities.Entity;
import io.github.vampirestudios.tdg.objs.entities.creatures.LivingEntity;
import io.github.vampirestudios.tdg.objs.entities.statics.nature.CropEntity;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.tags.ListTag;
import org.mini2Dx.core.game.GameContainer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EatCropsGoal extends Goal {

    private float maxDistance, speed;
    private List<String> cropIds = new ArrayList<>();

    public EatCropsGoal(MaewilEngine maewilEngine, LivingEntity entity) {
        this(maewilEngine, entity, 300f, 2.0f);
    }

    public EatCropsGoal(MaewilEngine maewilEngine, LivingEntity entity, float maxDistance, float speed) {
        super(maewilEngine, entity);
        this.maxDistance = maxDistance;
        this.speed = speed;
    }

    public List<String> getCropIds() {
        return cropIds;
    }

    public void setCropIds(String[] cropIdsIn) {
        cropIds.addAll(Arrays.asList(cropIdsIn));
    }

    public void setCropIds(ListTag cropIdsIn) {
        cropIdsIn.forEach(tag -> {
            cropIds.add(tag.getString());
        });
    }

    public void setCropIds(List<String> cropIdsIn) {
        cropIds = cropIdsIn;
    }

    @Override
    public boolean update(GameContainer container, float delta) {
        CropEntity closestCrop = getClosestCrop();
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

    private CropEntity getClosestCrop() {
        CropEntity closest = null;
        float lastDistance = maxDistance;

        for (Entity e : maewilEngine.getEntityManager().getEntities()) {
            if (e instanceof CropEntity) {
                CropEntity crop = (CropEntity) e;
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
