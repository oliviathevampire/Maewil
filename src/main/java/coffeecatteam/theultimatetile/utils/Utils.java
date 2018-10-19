package coffeecatteam.theultimatetile.utils;

import coffeecatteam.theultimatetile.TheUltimateTile;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Random;

public class Utils {

    public static final long RANDOM_SEED = new Random().nextLong();

    public static String getUsername() {
        return getUsername("Player");
    }

    public static String getUsername(String defaultName) {
        String username;
        int nameLength = 16;
        try {
            username = JOptionPane.showInputDialog("Please enter a username\nMust be max " + nameLength + " characters", defaultName);
            if (username.length() > nameLength || username.equalsIgnoreCase(""))
                username = getUsername(username);
            //username = username.substring(0, nameLength);
        } catch (NullPointerException e) {
            username = "you_clicked_cancel";
        }
        return username.replaceAll(" ", "_").replaceAll("[^a-zA-Z0-9_]+", ""); // I'ma Cat
    }

    public static Font loadFont(String path, float size) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, Utils.class.getResourceAsStream(path)).deriveFont(Font.PLAIN, size);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            TheUltimateTile.getTheUltimateTile().setRunning(false);
        }
        return null;
    }

    public static int getRandomInt(int max) {
        return getRandomInt(0, max);
    }

    public static int getRandomInt(int min, int max) {
        if (min >= max)
            throw new IllegalArgumentException("max must be greater than min");

        int out = 0;
        for (int i = 0; i < 50; i++)
            out = new Random(RANDOM_SEED).nextInt((max - min) + 1) + min;
        return out;
    }

    public static boolean getRandomBoolean() {
        return getRandomInt(0, 1) == 0;
    }

    public static BufferedReader loadFileInSideJar(String path) {
        return new BufferedReader(new InputStreamReader(Utils.class.getResourceAsStream(path)));
    }

    public static BufferedReader loadFileOutSideJar(String path) throws FileNotFoundException {
        return new BufferedReader(new FileReader(path));
    }

    public static String loadFileAsString(String path) {
        StringBuilder builder = new StringBuilder();

        try {
            BufferedReader br = loadFileInSideJar(path);
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

    public static float map(float from, float fromMin, float fromMax, float toMin, float toMax) {
        float fromAbs = from - fromMin;
        float fromMaxAbs = fromMax - fromMin;

        float normal = fromAbs / fromMaxAbs;

        float toMaxAbs = toMax - toMin;
        float toAbs = toMaxAbs * normal;

        float to = toAbs + toMin;

        return to;
    }

    public static String getKeyPressed(TheUltimateTile theUltimateTile) {
        switch (theUltimateTile.getKeyManager().getCurrentKeyPressedCode()) {
            case 8:
                return "BACKSPACE";
            case 9:
                return "TAB";
            case 16:
                return "SHIFT";
            case 17:
                return "CONTROL";
            case 18:
                return "ALT";
            case 20:
                return "CAPS LOCK";
            case 27:
                return "ESCAPE";
            case 32:
                return "SPACE";
            default:
                return String.valueOf(theUltimateTile.getKeyManager().getCurrentKeyPressedChar()).toUpperCase();
        }
    }
}
