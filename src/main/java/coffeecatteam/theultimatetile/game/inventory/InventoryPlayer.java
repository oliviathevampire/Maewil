package coffeecatteam.theultimatetile.game.inventory;

import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.entities.creatures.EntityPlayer;
import coffeecatteam.theultimatetile.gfx.assets.Assets;

import org.newdawn.slick.Graphics;

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
            int x = engine.getWidth() / 2 - width / 2;
            int y = engine.getHeight() / 2 - height / 2;

            Assets.INVENTORY.draw(x, y, width, height);
            Assets.PLAYER_IDLE[0].draw(x + player.getWidth() / 2f, y + player.getHeight() / 2f, player.getWidth(), player.getHeight());

            super.renderInventorySlots(g);
        }
    }
}
