package coffeecatteam.tilegame.items;

import coffeecatteam.tilegame.entities.Entity;
import coffeecatteam.tilegame.entities.creatures.EntityPlayer;

import java.awt.image.BufferedImage;

public class ItemFood extends Item {

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
        }
        return false;
    }

    public int getHealAmt() {
        return healAmt;
    }
}
