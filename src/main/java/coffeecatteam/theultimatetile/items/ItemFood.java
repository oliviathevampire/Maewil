package coffeecatteam.theultimatetile.items;

import coffeecatteam.theultimatetile.entities.Entity;
import coffeecatteam.theultimatetile.entities.creatures.EntityPlayer;

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
            player.heal(healAmt);
            return true;
        } else {
            player.setHealth(Entity.DEFAULT_HEALTH);
            return true;
        }
    }

    public int getHealAmt() {
        return healAmt;
    }
}
