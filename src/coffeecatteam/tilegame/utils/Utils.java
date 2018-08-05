package coffeecatteam.tilegame.utils;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

public class Utils {

    private static Font SLKSCR_FONT;

    static {
        try {
            InputStream in = Utils.class.getResourceAsStream("/assets/fonts/slkscr.ttf");
            SLKSCR_FONT = Font.createFont(Font.TRUETYPE_FONT, in);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    public static Font getSlkscrFont(double x, double y) {
        return getSlkscrFont(x, y, 20f);
    }

    public static Font getSlkscrFont(double x, double y, float size) {
        AffineTransform at = AffineTransform.getTranslateInstance(x, y);
        return SLKSCR_FONT.deriveFont(size).deriveFont(at);
    }

    public static String loadFileAsString(String path) {
        StringBuilder builder = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            while ((line = br.readLine()) != null)
                builder.append(line + "\n");

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }

    public static int parseInt(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
