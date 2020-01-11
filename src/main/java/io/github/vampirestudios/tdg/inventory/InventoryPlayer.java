package io.github.vampirestudios.tdg.inventory;

import io.github.vampirestudios.tdg.gfx.assets.Assets;
import io.github.vampirestudios.tdg.objs.entities.creatures.PlayerEntity;
import io.github.vampirestudios.tdg.start.TutEngine;
import io.github.vampirestudios.tdg.start.TutLauncher;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class InventoryPlayer extends InventoryAbstractPlayer {

    public InventoryPlayer(TutEngine tutEngine, PlayerEntity player) {
        super(tutEngine, player, player.getName(), TutLauncher.WIDTH / 2 - 56 * 6 / 2, TutLauncher.HEIGHT / 2 - 7 / 2);
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
            player.getTexture("idle").draw(x + player.getWidth() / 0.75F, y + player.getHeight() / 1.4f, player.getWidth(), player.getHeight());

            super.renderInventorySlots(container, game, g);
        }
    }
}
