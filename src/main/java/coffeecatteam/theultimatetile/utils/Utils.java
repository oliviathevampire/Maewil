package coffeecatteam.theultimatetile.utils;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Utils {

    public static String getUsername() {
        String username;
        int nameLength = 16;
        try {
            username = JOptionPane.showInputDialog("Please enter a username\nMust be max " + nameLength + " characters", "Player");
            if (username.length() > nameLength)
                username = getUsername();
                //username = username.substring(0, nameLength);
        } catch (NullPointerException e) {
            username = "you_clicked_cancel";
        }
        return username;
    }

    public static Font loadFont(String path, float size) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, Utils.class.getResourceAsStream(path)).deriveFont(Font.PLAIN, size);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    public static int getRandomInt(int min, int max) {
        if (min >= max)
            throw new IllegalArgumentException("max must be greater than min");

        return new Random().nextInt((max - min) + 1) + min;
    }

    public static String loadFileAsString(String path) {
        StringBuilder builder = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(Utils.class.getResourceAsStream(path)));
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

    public static float parseFloat(String number) {
        try {
            return Float.parseFloat(number);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
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
