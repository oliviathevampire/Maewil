package coffeecatteam.theultimatetile.world.colormap;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.theultimatetile.world.noise.OpenSimplexNoise;

import java.awt.*;
import java.awt.image.BufferedImage;

public class WorldMapGenerator {

    private static float path_threshold = 0.07f;

    /*
     * Seeds
     */
    private final long seedLand, seedPath, seedBiome;
    private final long seedExtra1, seedExtra2, seedExtra3;

    private int width, height;
    private double sizeLand, sizePath, sizeBiome;

    public WorldMapGenerator(long seedLand, int width, int height, double sizeLand) {
        this.seedLand = seedLand;
        this.seedPath = seedLand + NumberUtils.getRandomInt(1, 5);
        this.seedBiome = seedLand + NumberUtils.getRandomInt(1, 5);

        this.seedExtra1 = this.seedLand + NumberUtils.getRandomInt(100);
        this.seedExtra2 = this.seedExtra1 + NumberUtils.getRandomInt(100);
        this.seedExtra3 = this.seedExtra2 + NumberUtils.getRandomInt(100);

        this.width = width;
        this.height = height;

        this.sizeLand = sizeLand;
        this.sizePath = sizeLand + NumberUtils.getRandomFloat(1.0f, 10.0f);
        this.sizeBiome = sizeLand + NumberUtils.getRandomFloat(1.0f, 10.0f);
    }

    public BufferedImage generateLand(float xOff, float yOff, boolean hollowCaves) {
        OpenSimplexNoise noise = new OpenSimplexNoise(seedLand);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double value = noise.eval((float) ((x + xOff) / sizeLand), (float) ((y + yOff) / sizeLand));
                Color c = WorldColors.DEAP_OCEAN;
                if (value > -0.3 && value < -0.1) {
                    c = WorldColors.WATER;
                } else {
                    if (value > -0.1 && value < 0.15) {
                        c = WorldColors.SAND;
                    } else {
                        if (value > 0.15 && value < 0.55) {
                            c = WorldColors.GRASS;
                        } else {
                            if (value > 0.55 && value < 0.7) {
                                c = WorldColors.STONE;
                            } else {
                                if (hollowCaves) {
                                    if (value > 0.7) {
                                        c = WorldColors.DIRT;
                                    }
                                } else {
                                    c = WorldColors.STONE;
                                }
                            }
                        }
                    }
                }
                image.setRGB(x, y, c.getRGB());
            }
        }
        addSpots(xOff, yOff, image, 0.65d, 1.0d, 10.0d, WorldColors.DIRT, WorldColors.GRASS, seedExtra1);
        addSpots(xOff, yOff, image, 0.7d, 1.0d, 10.0d, WorldColors.DIRT, WorldColors.GRASS, seedExtra2);
        addSpots(xOff, yOff, image, 0.75d, 1.0d, 10.0d, WorldColors.DIRT, WorldColors.GRASS, seedExtra3);

        addSpots(xOff, yOff, image, 0.65d, 1.0d, 10.0d, WorldColors.STONE, WorldColors.DIRT, seedExtra1);
        addSpots(xOff, yOff, image, 0.7d, 1.0d, 10.0d, WorldColors.STONE, WorldColors.DIRT, seedExtra2);
        return image;
    }

    public BufferedImage generatePaths(float xOff, float yOff, BufferedImage landMap) {
        OpenSimplexNoise noise = new OpenSimplexNoise(seedPath);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double value = noise.eval((float) ((x + xOff) / sizePath), (float) ((y + yOff) / sizePath));
                Color c = new Color(255, 255, 255, 0);
                if (value > -path_threshold && value < path_threshold) {
                    if (landMap.getRGB(x, y) == WorldColors.GRASS.getRGB() || landMap.getRGB(x, y) == WorldColors.STONE.getRGB()) {
                        c = WorldColors.DIRT;
                    }
                }
                image.setRGB(x, y, getRGBA(c.getRGB()));
            }
        }
        addSpots(xOff, yOff, image, 0.65d, 1.0d, 10.0d, WorldColors.DIRT, WorldColors.GRASS, seedExtra1);
        addSpots(xOff, yOff, image, 0.7d, 1.0d, 10.0d, WorldColors.DIRT, WorldColors.GRASS, seedExtra2);
        addSpots(xOff, yOff, image, 0.75d, 1.0d, 10.0d, WorldColors.DIRT, WorldColors.GRASS, seedExtra3);
        return image;
    }

    private void addSpots(float xOff, float yOff, BufferedImage pathMap, double minThreshold, double maxThreshold, double blendSize, Color replace, Color spot, long seed) {
        OpenSimplexNoise noise = new OpenSimplexNoise(seed);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double value = noise.eval((float) ((x + xOff) / blendSize), (float) ((y + yOff) / blendSize));
                if (pathMap.getRGB(x, y) == replace.getRGB()) {
                    if (value > minThreshold && value < maxThreshold) {
                        pathMap.setRGB(x, y, getRGBA(spot.getRGB()));
                    }
                }
            }
        }
    }

    public BufferedImage generateBiomes(float xOff, float yOff, BufferedImage landMap, BufferedImage pathMap) {
        OpenSimplexNoise noise = new OpenSimplexNoise(seedBiome);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double value = noise.eval((float) ((x + xOff) / sizeBiome), (float) ((y + yOff) / sizeBiome));
                Color c = Biomes.NONE.getColor();
                if (landMap.getRGB(x, y) == WorldColors.GRASS.getRGB() && pathMap.getRGB(x, y) != WorldColors.DIRT.getRGB()) {
                    if (value < 0.15) {
                        c = Biomes.PLAINS.getColor();
                    } else if (value > 0.15 && value < 0.55) {
                        c = Biomes.FOREST.getColor();
                    } else {
                        c = Biomes.PLAINS.getColor();
                    }
                }
                if (landMap.getRGB(x, y) == WorldColors.DIRT.getRGB()) {
                    c = Biomes.CAVE.getColor();
                }
                if (landMap.getRGB(x, y) == WorldColors.STONE.getRGB()) {
                    if (pathMap.getRGB(x, y) == WorldColors.DIRT.getRGB()) {
                        c = Biomes.CAVE.getColor();
                    } else {
                        c = Biomes.UNDERGROUND.getColor();
                    }
                }
                image.setRGB(x, y, getRGBA(c.getRGB()));
            }
        }
        return image;
    }

    public static int getRGBA(int color) {
        int a = ((color >> 24) & 0xff);
        int r = ((color & 0xff0000) >> 16);
        int g = ((color & 0xff00) >> 8);
        int b = (color & 0xff);

        return a << 24 | r << 16 | g << 8 | b;
    }

    public long getSeedLand() {
        return seedLand;
    }

    public long getSeedPath() {
        return seedPath;
    }

    public long getSeedBiome() {
        return seedBiome;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getSizeLand() {
        return sizeLand;
    }

    public void setSizeLand(double sizeLand) {
        this.sizeLand = sizeLand;
    }

    public double getSizePath() {
        return sizePath;
    }

    public void setSizePath(double sizePath) {
        this.sizePath = sizePath;
    }

    public double getSizeBiome() {
        return sizeBiome;
    }

    public void setSizeBiome(double sizeBiome) {
        this.sizeBiome = sizeBiome;
    }
}