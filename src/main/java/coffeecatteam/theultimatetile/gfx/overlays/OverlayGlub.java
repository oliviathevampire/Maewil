package coffeecatteam.theultimatetile.gfx.overlays;

import coffeecatteam.theultimatetile.Handler;
import coffeecatteam.theultimatetile.entities.creatures.EntityPlayer;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.utils.Logger;
import coffeecatteam.theultimatetile.utils.Utils;

import java.awt.*;

public class OverlayGlub extends Overlay {

    public OverlayGlub(Handler handler, EntityPlayer player) {
        super(handler, player);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        int multiplier = 6;
        int sWidth = (16 * 4) / 2;
        int sHeight = 8;
        int width = sWidth * multiplier;
        int height = sHeight * multiplier;

        int glub = 100;
        String text = "Glubel: " + 50;
        Font font = Assets.FONT_20_BOLD;
        int x = handler.getWidth() / 2 - width / 2;

        Text.drawString(g, text, handler.getWidth() / 2, height + Text.getHeight(g, font) / 2, true, new Color(4, 79, 163), font);
        g.drawImage(Assets.GLUB_METER[0], x, 0, width, height, null);

        int glubWidth = (int) Utils.map(glub - 1, 0, 100, 0, sWidth);
        g.drawImage(Assets.GLUB_METER[1].getSubimage(0, 0, glubWidth * 2 + 1, sHeight * 2), x, 0, glubWidth * multiplier, height, null);
    }
}
