package coffeecatteam.theultimatetile.gfx;

import coffeecatteam.theultimatetile.TutEngine;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

public class Text {

    public static void drawStringCenteredX(Graphics g, String text, int yPos, Color c, Font font) {
        int width = getWidth(text, font);
        drawString(g, text, TutEngine.getTutEngine().getWidth() / 2 - width / 2, yPos, false, c, font);
    }

    public static void drawStringCenteredY(Graphics g, String text, int xPos, Color c, Font font) {
        int height = getHeight(text, font);
        drawString(g, text, xPos, (TutEngine.getTutEngine().getHeight() / 2 - height / 2) + getAscent(font), false, c, font);
    }

    public static void drawStringCentered(Graphics g, String text, Color c, Font font) {
        int width = getWidth(text, font), height = getHeight(text, font);
        int x = TutEngine.getTutEngine().getWidth() / 2 - width / 2, y = (TutEngine.getTutEngine().getHeight() / 2 - height / 2) + getAscent(font);
        drawString(g, text, x, y, false, c, font);
    }

    public static void drawString(Graphics g, String text, int xPos, int yPos, boolean centered, Color c, Font font) {
        drawString(g, text, xPos, yPos, centered, centered, c, font);
    }

    public static void drawString(Graphics g, String text, int xPos, int yPos, boolean centeredX, boolean centeredY, Color c, Font font) {
        g.setAntiAlias(false);
        g.setColor(c);
        g.setFont(font);
        int x = xPos;
        int y = yPos - getHeight(text, font);
        if (centeredX)
            x = xPos - getWidth(text, font) / 2;
        if (centeredY)
            y = (yPos - getHeight(text, font) / 2) + getAscent(font);
        g.drawString(text, x, y);
    }

    public static int getWidth(String text, Font font) {
        return font.getWidth(text);
    }

    public static int getHeight(String text, Font font) {
        return font.getHeight(text);
    }

    public static int getAscent(Font font) {
        return font.getLineHeight();
    }
}
