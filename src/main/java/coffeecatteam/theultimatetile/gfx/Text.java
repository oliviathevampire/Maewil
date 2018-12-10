package coffeecatteam.theultimatetile.gfx;

import coffeecatteam.theultimatetile.Engine;

import java.awt.*;
import java.awt.font.TextAttribute;
import java.text.AttributedString;

public class Text {

    public static void drawStringCenteredX(Graphics2D g, String text, int yPos, boolean underlined, Color c, Font font) {
        int width = getWidth(g, text, font);
        drawString(g, text, Engine.getEngine().getWidth() / 2 - width / 2, yPos, false, underlined, c, font);
    }

    public static void drawStringCenteredY(Graphics2D g, String text, int xPos, boolean underlined, Color c, Font font) {
        int height = getHeight(g, font);
        drawString(g, text, xPos, (Engine.getEngine().getHeight() / 2 - height / 2) + getAscent(g, font), false, underlined, c, font);
    }

    public static void drawStringCentered(Graphics2D g, String text, boolean underlined, Color c, Font font) {
        int width = getWidth(g, text, font), height = getHeight(g, font);
        int x = Engine.getEngine().getWidth() / 2 - width / 2, y = (Engine.getEngine().getHeight() / 2 - height / 2) + getAscent(g, font);
        drawString(g, text, x, y, false, underlined, c, font);
    }

    public static void drawString(Graphics2D g, String text, int xPos, int yPos, boolean centered, boolean underlined, Color c, Font font) {
        drawString(g, text, xPos, yPos, centered, centered, underlined, c, font);
    }

    public static void drawString(Graphics2D g, String text, int xPos, int yPos, boolean centeredX, boolean centeredY, boolean underlined, Color c, Font font) {
        g.setColor(c);
        AttributedString as = new AttributedString(text);
        as.addAttribute(TextAttribute.FONT, font);
        if (underlined)
            as.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        int x = xPos;
        int y = yPos;
        if (centeredX) {
            x = xPos - getWidth(g, text, font) / 2;
        }
        if (centeredY) {
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
