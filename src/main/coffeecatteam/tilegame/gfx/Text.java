package coffeecatteam.tilegame.gfx;

import coffeecatteam.tilegame.utils.Utils;

import java.awt.*;

public class Text {

    public static void drawString(Graphics g, String text, int xPos, int yPos, Color c, Font font) {
        drawString(g, text, xPos, yPos, false, c, font);
    }

    public static void drawString(Graphics g, String text, int xPos, int yPos, boolean centered, Color c, Font font) {
        g.setColor(c);
        g.setFont(font);
        int x = xPos;
        int y = yPos;
        if (centered) {
            FontMetrics fm = g.getFontMetrics(font);
            x = xPos - fm.stringWidth(text) / 2;
            y = (yPos - fm.getHeight() / 2) + fm.getAscent();
        }
        g.drawString(text, x, y);
    }
}
