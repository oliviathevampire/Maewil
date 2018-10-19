package coffeecatteam.theultimatetile.inventory;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.entities.creatures.EntityPlayer;
import coffeecatteam.theultimatetile.gfx.Assets;

import java.awt.*;

public class InventoryCampfire extends Inventory {

    public InventoryCampfire(TheUltimateTile theUltimateTile, EntityPlayer player) {
        super(theUltimateTile, player);

        // Add inventory slots
        int xd = 190, yd = 360, x, y;
        int width = 48, height = 48;
        for (int i = 0; i < maxSize; i++) {
            x = xd + 54 * i;
            y = yd;
            if (i > maxSize / 2 - 1) {
                x -= width * 7 - 12;
                y += height + 5;
            }
            addSlot(i, x, y, width, height);
            getSlot(i).setSelector(Assets.SLOT_SELECTER);
        }
    }

    @Override
    public void tick() {
        for (int i = 0; i < maxSize; i++) {
            this.slots.set(i, player.getInventoryPlayer().getSlot(i));
        }
    }

    @Override
    public void render(Graphics g) {
        if (active) {
            int multiplier = 6;
            int width = 57 * multiplier;
            int height = 44 * multiplier;
            int x = theUltimateTile.getWidth() / 2 - width / 2;
            int y = theUltimateTile.getHeight() / 2 - height / 2;

            g.drawImage(Assets.CAMPFIRE_INVENTORY, x, y, width, height, null);

            for (int i = 0; i < maxSize; i++) {
                getSlot(i).render(g);
            }
        }
    }
}
