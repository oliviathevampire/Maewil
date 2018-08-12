package coffeecatteam.tilegame.gfx;

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
            x = xPos - getWidth(g, text, font) / 2;
            y = (yPos - getHeight(g, font) / 2) + getAscent(g, font);
        }
        g.drawString(text, x, y);
    }

    public static int getWidth(Graphics g, String text, Font font) {
        FontMetrics fm = g.getFontMetrics(font);
        return fm.stringWidth(text);
    }

    public static int getHeight(Graphics g, Font font) {
        FontMetrics fm = g.getFontMetrics(font);
        return fm.getHeight();
    }

    public static int getAscent(Graphics g, Font font) {
        FontMetrics fm = g.getFontMetrics(font);
        return fm.getAscent();
    }
}
