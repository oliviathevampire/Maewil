package coffeecatteam.theultimatetile.inventory;

import coffeecatteam.theultimatetile.GameEngine;
import coffeecatteam.theultimatetile.entities.creatures.EntityPlayer;
import coffeecatteam.theultimatetile.gfx.Assets;

import java.awt.*;

public class InventoryPlayer extends InventoryAbstractPlayer {

    public InventoryPlayer(GameEngine gameEngine, EntityPlayer player) {
        super(gameEngine, player, "Player");
        isDefault = true;
    }

    @Override
    public void render(Graphics g) {
        if (active) {
            // Render inventory backgorund
            int multiplier = 6;
            int width = 57 * multiplier;
            int height = 41 * multiplier;
            int x = gameEngine.getWidth() / 2 - width / 2;
            int y = gameEngine.getHeight() / 2 - height / 2;

            g.drawImage(Assets.INVENTORY, x, y, width, height, null);
            g.drawImage(Assets.PLAYER_IDLE[0], x + player.getWidth() / 2, y + player.getHeight() / 2, player.getWidth(), player.getHeight(), null);

            super.renderInventorySlots(g);
        }
    }
}
