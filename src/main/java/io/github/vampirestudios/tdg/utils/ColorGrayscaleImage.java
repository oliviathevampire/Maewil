package io.github.vampirestudios.tdg.utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ColorGrayscaleImage {

    public static BufferedImage convert(BufferedImage img, Color color) {
        BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                newImg.setRGB(x, y, blend(img.getRGB(x, y), color.getRGB(), 0.5f));
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

        int aA = ((from >> 24) & 0xff);
        int aR = ((from & 0xff0000) >> 16);
        int aG = ((from & 0xff00) >> 8);
        int aB = (from & 0xff);

        int bR = ((to & 0xff0000) >> 16);
        int bG = ((to & 0xff00) >> 8);
        int bB = (to & 0xff);

        int R = ((int) (aR * iRatio) + (int) (bR * ratio));
        int G = ((int) (aG * iRatio) + (int) (bG * ratio));
        int B = ((int) (aB * iRatio) + (int) (bB * ratio));

        return aA << 24 | R << 16 | G << 8 | B;
    }
}