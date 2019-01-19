package coffeecatteam.theultimatetile.utils.noise;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class NoiseMapGenerator {

    public static final Color DEAP_OCEAN = new Color(0, 0, 216);
    public static final Color OCEAN = Color.blue;
    public static final Color SAND = Color.orange;
    public static final Color GRASS = Color.green;
    public static final Color STONE = Color.gray;
    public static final Color DIRT = new Color(145, 74, 36);

    public static BufferedImage generateLand(long seed, int width, int height, double size) throws IOException {
        OpenSimplexNoise noise = new OpenSimplexNoise(seed);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double value = noise.eval((float) (x / size), (float) (y / size));
                Color c = DEAP_OCEAN;
                if (value > -0.3 && value < -0.1) {
                    c = OCEAN;
                } else {
                    if (value > -0.1 && value < 0.15) {
                        c = SAND;
                    } else {
                        if (value > 0.15 && value < 0.55) {
                            c = GRASS;
                        } else {
                            if (value > 0.55 && value < 0.7) {
                                c = STONE;
                            } else {
                                if (value > 0.7) {
                                    c = DIRT;
                                }
                            }
                        }
                    }
                }
                image.setRGB(x, y, c.getRGB());
            }
        }
        return image;
    }

    public static BufferedImage generatePaths(long seed, int width, int height, double size, BufferedImage landMap) throws IOException {
        OpenSimplexNoise noise = new OpenSimplexNoise(seed);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double value = noise.eval((float) (x / size), (float) (y / size));
                Color c = new Color(255, 255, 255, 0);
                if (landMap.getRGB(x, y) == GRASS.getRGB() || landMap.getRGB(x, y) == STONE.getRGB()) {
                    if (value > -0.1 && value < 0.1) {
                        c = DIRT;
                    }
                }
                image.setRGB(x, y, getRGBA(c.getRGB()));
            }
        }
        return image;
    }

    private static int getRGBA(int color) {
        int a = ((color >> 24) & 0xff);
        int r = ((color & 0xff0000) >> 16);
        int g = ((color & 0xff00) >> 8);
        int b = (color & 0xff);

        return a << 24 | r << 16 | g << 8 | b;
    }
}