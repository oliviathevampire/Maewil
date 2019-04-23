package coffeecatteam.theultimatetile.objs.items;

import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.objs.entities.creatures.EntityPlayer;

public class ItemFood extends Item implements IInteractable {

    private int healAmt;

    public ItemFood(TutEngine tutEngine, String id, int healAmt) {
        super(tutEngine, id);
        this.healAmt = healAmt;
    }

    @Override
    public boolean onInteracted(EntityPlayer player) {
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
    public ItemFood newCopy() {
        return super.newCopy(new ItemFood(tutEngine, id, healAmt));
    }
}
