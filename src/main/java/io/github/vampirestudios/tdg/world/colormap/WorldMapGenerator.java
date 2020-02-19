package io.github.vampirestudios.tdg.world.colormap;

import coffeecatteam.coffeecatutils.NumberUtils;
import io.github.vampirestudios.tdg.world.noise.SuperSimplexNoise;

import java.awt.*;
import java.awt.image.BufferedImage;

public class WorldMapGenerator {

    /*
     * Seeds
     */
    private final long seedLand, seedLand2, seedPath, seedPath2, seedBiome, seedBiome2;
    private final long seedExtra1, seedExtra2, seedExtra3;

    private int width, height;
    private double sizeLand, sizePath, sizeBiome;

    public WorldMapGenerator(long seedLand, int width, int height, double sizeLand) {
        this.seedLand = seedLand;
        this.seedLand2 = seedLand + NumberUtils.getRandomInt(1, 5);
        this.seedPath = seedLand + NumberUtils.getRandomInt(1, 5);
        this.seedPath2 = seedLand2 + NumberUtils.getRandomInt(1, 5);
        this.seedBiome = seedLand + NumberUtils.getRandomInt(1, 5);
        this.seedBiome2 = seedLand2 + NumberUtils.getRandomInt(1, 5);

        this.seedExtra1 = this.seedLand + NumberUtils.getRandomInt(100);
        this.seedExtra2 = this.seedExtra1 + NumberUtils.getRandomInt(100);
        this.seedExtra3 = this.seedExtra2 + NumberUtils.getRandomInt(100);

        this.width = width;
        this.height = height;

        this.sizeLand = sizeLand;
        this.sizePath = sizeLand + NumberUtils.getRandomFloat(1.0f, 10.0f);
        this.sizeBiome = sizeLand + NumberUtils.getRandomFloat(1.0f, 10.0f);
    }

    /**
     * Generates a BufferedImage for the land parts based on the x-offset, y-offset and if it should have hollow caves
     **/
    public BufferedImage generateLand(float xOff, float yOff, boolean hollowCaves) {
        SuperSimplexNoise noise = new SuperSimplexNoise(seedLand);
        SuperSimplexNoise noise2 = new SuperSimplexNoise(seedLand2);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double value = noise.noise2(((x + xOff) / sizeLand), ((y + yOff) / sizeLand)) +
                        noise2.noise2(((x + xOff * 2) / sizeLand), ((y + yOff * 2) / sizeLand)) / 2;
                Color c = WorldColors.DEEP_OCEAN;
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
        addSpots(xOff, yOff, image, 0.65d, WorldColors.DIRT, WorldColors.GRASS, seedExtra1);
        addSpots(xOff, yOff, image, 0.7d, WorldColors.DIRT, WorldColors.GRASS, seedExtra2);
        addSpots(xOff, yOff, image, 0.75d, WorldColors.DIRT, WorldColors.GRASS, seedExtra3);

        addSpots(xOff, yOff, image, 0.65d, WorldColors.STONE, WorldColors.DIRT, seedExtra1);
        addSpots(xOff, yOff, image, 0.7d, WorldColors.STONE, WorldColors.DIRT, seedExtra2);
        return image;
    }

    /**
     * Generates a BufferedImage for paths around the world based on the x-offset, y-offset and the land map
     **/
    public BufferedImage generatePaths(float xOff, float yOff, BufferedImage landMap) {
        SuperSimplexNoise noise = new SuperSimplexNoise(seedPath);
        SuperSimplexNoise noise2 = new SuperSimplexNoise(seedPath2);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double value = noise.noise2(((x + xOff) / sizePath), ((y + yOff) / sizePath)) +
                        noise2.noise2(((x + xOff * 2) / sizePath), ((y + yOff * 2) / sizePath)) / 2;
                Color c = new Color(255, 255, 255, 0);
                float path_threshold = 0.07f;
                if (value > -path_threshold && value < path_threshold) {
                    if (landMap.getRGB(x, y) == WorldColors.GRASS.getRGB() || landMap.getRGB(x, y) == WorldColors.STONE.getRGB()) {
                        c = WorldColors.DIRT;
                    }
                }
                image.setRGB(x, y, getRGBA(c.getRGB()));
            }
        }
        addSpots(xOff, yOff, image, 0.65d, WorldColors.DIRT, WorldColors.GRASS, seedExtra1);
        addSpots(xOff, yOff, image, 0.7d, WorldColors.DIRT, WorldColors.GRASS, seedExtra2);
        addSpots(xOff, yOff, image, 0.75d, WorldColors.DIRT, WorldColors.GRASS, seedExtra3);
        return image;
    }

    /**
     * Generates
     **/
    private void addSpots(float xOff, float yOff, BufferedImage pathMap, double minThreshold, Color replace, Color spot, long seed) {
        SuperSimplexNoise noise = new SuperSimplexNoise(seed);
        SuperSimplexNoise noise2 = new SuperSimplexNoise(seed);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double value = noise.noise2(((x + xOff) / sizeBiome), ((y + yOff) / sizeBiome)) +
                        noise2.noise2(((x + xOff * 2) / sizeBiome), ((y + yOff * 2) / sizeBiome)) / 2;
                if (pathMap.getRGB(x, y) == replace.getRGB()) {
                    if (value > minThreshold && value < 1.0) {
                        pathMap.setRGB(x, y, getRGBA(spot.getRGB()));
                    }
                }
            }
        }
    }

    public BufferedImage generateBiomes(float xOff, float yOff, BufferedImage landMap, BufferedImage pathMap) {
        SuperSimplexNoise noise = new SuperSimplexNoise(seedBiome);
        SuperSimplexNoise noise2 = new SuperSimplexNoise(seedBiome2);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double value = noise.noise2(((x + xOff) / sizeBiome), ((y + yOff) / sizeBiome)) +
                        noise2.noise2(((x + xOff * 2) / sizeBiome), ((y + yOff * 2) / sizeBiome)) / 2;
                Color c = Biomes.NONE.getColor();
                if (landMap.getRGB(x, y) == WorldColors.GRASS.getRGB() && pathMap.getRGB(x, y) != WorldColors.DIRT.getRGB() && landMap.getRGB(x, y) != WorldColors.DIRT.getRGB()) {
                    if (value > 0.15 && value < 0.55) {
                        c = Biomes.FOREST.getColor();
                    } else {
                        c = Biomes.PLAINS.getColor();
                    }
                }else if(landMap.getRGB(x, y) == WorldColors.DARK_GRASS.getRGB() && pathMap.getRGB(x, y) != WorldColors.DIRT.getRGB() && landMap.getRGB(x, y) != WorldColors.DIRT.getRGB()) {
                    if (value > 0.15 && value < 0.55) {

                    }
                } else {
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