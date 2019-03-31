package coffeecatteam.theultimatetile.gfx;

import coffeecatteam.theultimatetile.TutLauncher;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

public class Text {

    public static void drawStringCenteredX(Graphics g, String text, float yPos, Color c, Font font) {
        float width = getWidth(text, font);
        drawString(g, text, TutLauncher.WIDTH / 2f - width / 2, yPos, false, c, font);
    }

    public static void drawStringCenteredY(Graphics g, String text, float xPos, Color c, Font font) {
        float height = getHeight(text, font);
        drawString(g, text, xPos, (TutLauncher.HEIGHT / 2f - height / 2) + getAscent(font), false, c, font);
    }

    public static void drawStringCentered(Graphics g, String text, Color c, Font font) {
        float width = getWidth(text, font), height = getHeight(text, font);
        float x = TutLauncher.WIDTH / 2f - width / 2, y = (TutLauncher.HEIGHT / 2f - height / 2) + getAscent(font);
        drawString(g, text, x, y, false, c, font);
    }

    public static void drawString(Graphics g, String text, float xPos, float yPos, boolean centered, Color c, Font font) {
        drawString(g, text, xPos, yPos, centered, centered, c, font);
    }

    public static void drawString(Graphics g, String text, float xPos, float yPos, boolean centeredX, boolean centeredY, Color c, Font font) {
        g.setAntiAlias(false);
        g.setColor(c);
        g.setFont(font);
        float x = xPos;
        float y = yPos - getHeight(text, font);
        if (centeredX)
            x = xPos - getWidth(text, font) / 2;
        if (centeredY)
            y = (yPos - getHeight(text, font) / 2) + getAscent(font);
        g.drawString(text, x, y);
    }

    public static float getWidth(String text, Font font) {
        return font.getWidth(text);
    }

    public static float getHeight(String text, Font font) {
        return font.getHeight(text);
    }

    public static float getAscent(Font font) {
        return font.getLineHeight();
    }
}
