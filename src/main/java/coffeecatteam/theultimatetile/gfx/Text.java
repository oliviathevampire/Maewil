package coffeecatteam.theultimatetile.gfx;

import java.awt.*;
import java.awt.font.TextAttribute;
import java.text.AttributedString;

public class Text {

    public static void drawString(Graphics2D g, String text, int xPos, int yPos, boolean centered, boolean underlined, Color c, Font font) {
        g.setColor(c);
        AttributedString as = new AttributedString(text);
        as.addAttribute(TextAttribute.FONT, font);
        if (underlined)
            as.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        int x = xPos;
        int y = yPos;
        if (centered) {
            x = xPos - getWidth(g, text, font) / 2;
            y = (yPos - getHeight(g, font) / 2) + getAscent(g, font);
        }
        g.drawString(as.getIterator(), x, y);
    }

    public static int getWidth(Graphics2D g, String text, Font font) {
        FontMetrics fm = g.getFontMetrics(font);
        return fm.stringWidth(text);
    }

    public static int getHeight(Graphics2D g, Font font) {
        FontMetrics fm = g.getFontMetrics(font);
        return fm.getHeight();
    }

    public static int getAscent(Graphics2D g, Font font) {
        FontMetrics fm = g.getFontMetrics(font);
        return fm.getAscent();
    }
}
