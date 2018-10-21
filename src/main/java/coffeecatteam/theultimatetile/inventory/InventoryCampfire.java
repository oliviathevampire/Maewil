package coffeecatteam.theultimatetile.inventory;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.entities.creatures.EntityPlayer;
import coffeecatteam.theultimatetile.gfx.Assets;

import java.awt.*;

public class InventoryCampfire extends InventoryAbstractPlayer {

    public InventoryCampfire(TheUltimateTile theUltimateTile, EntityPlayer player) {
        super(theUltimateTile, player, 190, 370);
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

            super.renderInventorySlots(g);
        }
    }
}
