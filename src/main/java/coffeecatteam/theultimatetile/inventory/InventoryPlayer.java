package coffeecatteam.theultimatetile.inventory;

import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.objs.entities.creatures.EntityPlayer;
import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.start.TutLauncher;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class InventoryPlayer extends InventoryAbstractPlayer {

    public InventoryPlayer(TutEngine tutEngine, EntityPlayer player) {
        super(tutEngine, player, "Player");
        isDefault = true;
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        if (active) {
            // Render inventory backgorund
            int multiplier = 6;
            int width = 57 * multiplier;
            int height = 41 * multiplier;
            int x = TutLauncher.WIDTH / 2 - width / 2;
            int y = TutLauncher.HEIGHT / 2 - height / 2;

            Assets.GUI_INVENTORY.draw(x, y, width, height);
            player.getTexture("idle").draw(x + player.getWidth() / 2f, y + player.getHeight() / 2f, player.getWidth(), player.getHeight());

            super.renderInventorySlots(container, game, g);
        }
    }
}
