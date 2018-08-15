package coffeecatteam.theultimatetile.gfx.overlays;

import coffeecatteam.theultimatetile.Handler;
import coffeecatteam.theultimatetile.entities.creatures.EntityPlayer;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Text;

import java.awt.*;

public class OverlayPlayerSprint extends Overlay {

    public OverlayPlayerSprint(Handler handler, EntityPlayer player) {
        super(handler, player);
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

        String text = "Sprint left: " + (int) player.getSprintTimer();
        Font font = Assets.FONT_20;
        Text.drawString(g, text, handler.getWidth() - Text.getWidth(g, text, font) - 10, handler.getHeight() - height, Color.white, font);
        g.drawImage(Assets.SPRINT[1], handler.getWidth() - width, handler.getHeight() - height, width, height, null);

        int sprint = (int) map(player.getSprintTimer() - 1, 0, 100, 0, 32);
        g.drawImage(Assets.SPRINT[0].getSubimage(0, 0, sWidth - sprint, sHeight), handler.getWidth() - width, handler.getHeight() - height, width - sprint * multiplier, height, null);
    }

    public static float map(float from, float fromMin, float fromMax, float toMin,  float toMax) {
        float fromAbs  =  from - fromMin;
        float fromMaxAbs = fromMax - fromMin;

        float normal = fromAbs / fromMaxAbs;

        float toMaxAbs = toMax - toMin;
        float toAbs = toMaxAbs * normal;

        float to = toAbs + toMin;

        return to;
    }
}
