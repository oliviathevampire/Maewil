package io.github.vampirestudios.tdg.objs.items;

import io.github.vampirestudios.tdg.objs.entities.Entity;
import io.github.vampirestudios.tdg.objs.entities.creatures.PlayerEntity;
import io.github.vampirestudios.tdg.start.TutEngine;

public class FoodItem extends Item implements IInteractable {

    private int healAmt;

    public FoodItem(TutEngine tutEngine, String id, int healAmt) {
        super(tutEngine, id);
        this.healAmt = healAmt;
    }

    @Override
    public boolean onInteracted(PlayerEntity player) {
        if (player.getCurrentHealth() < Entity.DEFAULT_HEALTH && player.getCurrentHealth() <= Entity.DEFAULT_HEALTH - healAmt) {
            if (player.getCurrentHealth() + healAmt >= Entity.DEFAULT_HEALTH)
                player.setCurrentHealth(Entity.DEFAULT_HEALTH);
            else
                player.heal(healAmt);
            return true;
        }
        return false;
    }

    public int getHealAmt() {
        return healAmt;
    }

    @Override
    public FoodItem newCopy() {
        return super.newCopy(new FoodItem(tutEngine, id, healAmt));
    }
}
