package io.github.vampirestudios.tdg.inventory;

import io.github.vampirestudios.tdg.gfx.assets.Assets;
import io.github.vampirestudios.tdg.objs.entities.creatures.PlayerEntity;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.start.MaewilLauncher;
import org.newdawn.slick.Graphics;

public class InventoryPlayer extends InventoryAbstractPlayer {

    public InventoryPlayer(MaewilEngine maewilEngine, PlayerEntity player) {
        super(maewilEngine, player, player.getName(), MaewilLauncher.WIDTH / 2 - 56 * 6 / 2, MaewilLauncher.HEIGHT / 2 - 7 / 2);
        isDefault = true;
    }

    @Override
    public void render(Graphics g) {
        if (active) {
            // Render inventory backgorund
            int multiplier = 6;
            int width = 57 * multiplier;
            int height = 41 * multiplier;
            int x = MaewilLauncher.WIDTH / 2 - width / 2;
            int y = MaewilLauncher.HEIGHT / 2 - height / 2;

            Assets.GUI_INVENTORY.draw(x, y, width, height);
//            player.getTextures().forEach((s, animation) -> System.out.println(s));
//            if (player.getTextures().containsKey("idle")) {
//                System.out.println("Testing");
//            } else {
//                System.out.println("Not Testing");
//            }
//            player.getTexture("idle").draw(x + player.getWidth() / 0.75F, y + player.getHeight() / 1.4f, player.getWidth(), player.getHeight());

            super.renderInventorySlots(g);
        }
    }
}
