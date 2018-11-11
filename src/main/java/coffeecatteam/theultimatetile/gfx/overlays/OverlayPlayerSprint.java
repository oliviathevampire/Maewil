package coffeecatteam.theultimatetile.gfx.overlays;

import coffeecatteam.theultimatetile.GameEngine;
import coffeecatteam.theultimatetile.entities.creatures.EntityPlayer;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.utils.Utils;

import java.awt.*;

public class OverlayPlayerSprint extends Overlay {

    public OverlayPlayerSprint(GameEngine gameEngine, EntityPlayer player) {
        super(gameEngine, player);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        int multiplier = 6;
        int sWidth = 32;
        int sHeight = 16;
        int width = sWidth * multiplier;
        int height = sHeight * multiplier;

        int sprint = (int) player.getSprintTimer();
        String text = "Sprint moveLeft: " + sprint;
        Font font = Assets.FONT_20;
        int x = gameEngine.getWidth() - width;
        int y = gameEngine.getHeight() - height;

        Text.drawString(g, text, gameEngine.getWidth() - Text.getWidth(g, text, font) - 10, gameEngine.getHeight() - height, false, false, Color.white, font);
        g.drawImage(Assets.SPRINT[0], x, y, width, height, null);

        int sprintWidth = (int) Utils.map(sprint - 1, 0, player.getMaxSprintTimer(), 0, sWidth);
        g.drawImage(Assets.SPRINT[1].getSubimage(0, 0, sWidth - sprintWidth, sHeight), x, y, width - sprintWidth * multiplier, height, null);
    }
}
