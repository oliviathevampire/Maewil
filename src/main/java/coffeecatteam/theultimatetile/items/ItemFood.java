package coffeecatteam.theultimatetile.items;

import coffeecatteam.theultimatetile.entities.Entity;
import coffeecatteam.theultimatetile.entities.player.EntityPlayer;

import java.awt.image.BufferedImage;

public class ItemFood extends Item implements IInteractable {

    private int healAmt;

    public ItemFood(BufferedImage texture, String name, int id, int healAmt) {
        super(texture, name, id);
        this.healAmt = healAmt;
    }

    @Override
    public boolean onInteracted(EntityPlayer player) {
        if (player.getHealth() < Entity.DEFAULT_HEALTH && player.getHealth() <= Entity.DEFAULT_HEALTH - healAmt) {
            if (player.getHealth() + healAmt > Entity.DEFAULT_HEALTH)
                player.setHealth(Entity.DEFAULT_HEALTH);
            else
                player.heal(healAmt);
            return true;
        }
        return false;
    }

    public int getHealAmt() {
        return healAmt;
    }
}
