package coffeecatteam.theultimatetile.gfx.image;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author CoffeeCatRailway
 * Created: 25/12/2018
 */
public class ColorGrayscaleImage {

    public static BufferedImage convert(BufferedImage img, Color color) {
        BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                Color imgColor = new Color(img.getRGB(x, y));
                int c0 = ((imgColor.getAlpha() * 255 & 0xFF) << 24) |
                        ((imgColor.getRed() * 255 & 0xFF) << 16) |
                        ((imgColor.getGreen() * 255 & 0xFF) << 8) |
                        ((imgColor.getBlue() * 255 & 0xFF));
                int c1 = ((color.getAlpha() * 255 & 0xFF) << 24) |
                        ((color.getRed() * 255 & 0xFF) << 16) |
                        ((color.getGreen() * 255 & 0xFF) << 8) |
                        ((color.getBlue() * 255 & 0xFF));
                newImg.setRGB(x, y, blend(c0, c1, 0.5f));
            }
        }

        return newImg;
    }

    private static int blend(int from, int to, float ratio) {
        if (ratio > 1f) {
            ratio = 1f;
        } else if (ratio < 0f) {
            ratio = 0f;
        }
        float iRatio = 1.0f - ratio;

        int aA = (from >> 24 & 0xff);
        int aR = ((from & 0xff0000) >> 16);
        int aG = ((from & 0xff00) >> 8);
        int aB = (from & 0xff);

        int bA = (to >> 24 & 0xff);
        int bR = ((to & 0xff0000) >> 16);
        int bG = ((to & 0xff00) >> 8);
        int bB = (to & 0xff);

        int A = ((int) (aA * iRatio) + (int) (bA * ratio));
        int R = ((int) (aR * iRatio) + (int) (bR * ratio));
        int G = ((int) (aG * iRatio) + (int) (bG * ratio));
        int B = ((int) (aB * iRatio) + (int) (bB * ratio));

        return A << 24 | R << 16 | G << 8 | B;
    }
}
