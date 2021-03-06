package io.github.vampirestudios.tdg.gfx;

import io.github.vampirestudios.tdg.gfx.assets.Assets;
import io.github.vampirestudios.tdg.start.MaewilLauncher;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.List;

public class Text {

    public static void drawStringCenteredX(Graphics g, String text, float yPos, Color c, Font font) {
        float width = getWidth(text, font);
        drawString(g, text, MaewilLauncher.WIDTH / 2f - width / 2, yPos, false, c, font);
    }

    public static void drawStringCenteredY(Graphics g, String text, float xPos, Color c, Font font) {
        float height = getHeight(text, font);
        drawString(g, text, xPos, (MaewilLauncher.HEIGHT / 2f - height / 2) + getAscent(font), false, c, font);
    }

    public static void drawStringCentered(Graphics g, String text, Color c, Font font) {
        float width = getWidth(text, font), height = getHeight(text, font);
        float x = MaewilLauncher.WIDTH / 2f - width / 2, y = (MaewilLauncher.HEIGHT / 2f - height / 2) + getAscent(font);
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

    public static Font getCorrectFont(String text, float height) {
        List<Font> fonts = new ArrayList<>();
        for (int i = 5; i <= 100; i += 5) {
            Font current = Assets.FONTS.get(String.valueOf(i));
            if (getHeight(text, current) <= height)
                fonts.add(current);
        }
        return fonts.get(fonts.size() - 1);
    }

    public static Object[] fitTextInWidth(String baseText, Font baseFont, float width, float height, boolean addDots) {
        String newText = baseText;
        Font newFont = baseFont;
        boolean toLong = false;

        while (getWidth(newText, newFont) > width) {
            newText = newText.substring(0, newText.length() - 1);
            newFont = getCorrectFont(newText, height);
            toLong = true;
        }
        if (toLong && addDots)
            newText = newText.substring(0, newText.length() - 2) + "..";

        return new Object[] {newText, newFont, toLong};
    }
}
