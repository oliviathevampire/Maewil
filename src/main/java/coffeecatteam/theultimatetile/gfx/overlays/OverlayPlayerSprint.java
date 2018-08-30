package coffeecatteam.theultimatetile.gfx.overlays;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.entities.player.EntityPlayer;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.utils.Utils;

import java.awt.*;

public class OverlayPlayerSprint extends Overlay {

    public OverlayPlayerSprint(TheUltimateTile theUltimateTile, EntityPlayer player) {
        super(theUltimateTile, player);
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
        String text = "Sprint left: " + sprint;
        Font font = Assets.FONT_20;
        int x = theUltimateTile.getWidth() - width;
        int y = theUltimateTile.getHeight() - height;

        Text.drawString(g, text, theUltimateTile.getWidth() - Text.getWidth(g, text, font) - 10, theUltimateTile.getHeight() - height, false, false, Color.white, font);
        g.drawImage(Assets.SPRINT[0], x, y, width, height, null);

        int sprintWidth = (int) Utils.map(sprint - 1, 0, player.getMaxSprintTimer(), 0, sWidth);
        g.drawImage(Assets.SPRINT[1].getSubimage(0, 0, sWidth - sprintWidth, sHeight), x, y, width - sprintWidth * multiplier, height, null);
    }
}
