package io.github.vampirestudios.tdg.world;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.coffeecatutils.logger.CatLogger;
import io.github.vampirestudios.tdg.objs.entities.Entities;
import io.github.vampirestudios.tdg.objs.tiles.Tile;
import io.github.vampirestudios.tdg.objs.tiles.TilePos;
import io.github.vampirestudios.tdg.objs.tiles.Tiles;
import io.github.vampirestudios.tdg.objs.tiles.stone.StoneTile;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.utils.math.MatUtil;
import io.github.vampirestudios.tdg.world.colormap.Biomes;
import io.github.vampirestudios.tdg.world.colormap.WorldColors;
import io.github.vampirestudios.tdg.world.colormap.WorldMapGenerator;
import io.github.vampirestudios.tdg.world.noise.SuperSimplexNoise;

import java.awt.*;
import java.awt.image.BufferedImage;

public class WorldGenerator {

    private MaewilEngine maewilEngine;
    private int worldSize;
    private BufferedImage landMap, pathMap, biomeMap;

    private long seed, seedOreOffInc, seedOreOff;

    private double blendSize = 25.0d;

    private TileList bgTiles, fgTiles;
    private CatLogger logger;

    private boolean imageMapsGenerated = false, bgTilesGenerated = false, fgTilesGenerated = false;
    private Thread generatorThread;

    public WorldGenerator(MaewilEngine maewilEngine, long seed, int worldSize) {
        this.maewilEngine = maewilEngine;
        this.seed = seed;
        this.worldSize = worldSize;

        this.seedOreOffInc = this.seed / 4;
        this.seedOreOff = this.seedOreOffInc;
    }

    public void generate() {
        generatorThread = new Thread(() -> {
            logger = new CatLogger();
            WorldMapGenerator mapGenerator = new WorldMapGenerator(seed, worldSize, worldSize, blendSize + 5D);

            // Land maps
            landMap = mapGenerator.generateLand(0, 0, true);
            pathMap = mapGenerator.generatePaths(0, 0, landMap);
            biomeMap = mapGenerator.generateBiomes(0, 0, landMap, pathMap);
            imageMapsGenerated = true;
            logger.info("World image maps generated");

            // Tiles
            bgTiles = generateBackgroundTiles();
            fgTiles = generateForegroundTiles();
            logger.info("Tiles generated");

            // Entities
            maewilEngine.getEntityManager().reset();
            for (int y = 0; y < worldSize; y++) {
                for (int x = 0; x < worldSize; x++) {
                    Biomes.Biome biome = Biomes.getBiomeAt(biomeMap, x, y);
                    if (biome.equals(Biomes.FOREST) || biome.equals(Biomes.PLAINS)) {
                        float threshold = 0.004f;
                        float xOff = NumberUtils.getRandomFloat(-threshold, threshold);
                        float yOff = NumberUtils.getRandomFloat(-threshold, threshold);
                        maewilEngine.getEntityManager().addEntity(Entities.BUSH_MEDIUM, x + xOff, y + yOff, false);
                    }
                }
            }
        }, "WorldGenerator-Thread");
        generatorThread.start();
    }

    public TileList generateBackgroundTiles() {
        TileList tiles = new TileList(worldSize);
        for (int y = 0; y < worldSize; y++) {
            for (int x = 0; x < worldSize; x++) {
                Color lc = new Color(WorldMapGenerator.getRGBA(landMap.getRGB(x, y)));
                Color pc = new Color(WorldMapGenerator.getRGBA(pathMap.getRGB(x, y)));
                Tile tile = Tiles.AIR;

                if (lc.getRGB() == WorldColors.DIRT.getRGB()
                        || pc.getRGB() == WorldColors.DIRT.getRGB()
                        || lc.getRGB() == WorldColors.GRASS.getRGB()
                        || lc.getRGB() == WorldColors.DEEP_OCEAN.getRGB())
                    tile = Tiles.DIRT;

                if (lc.getRGB() == WorldColors.DARK_GRASS.getRGB())
                    tile = Tiles.DARK_GRASS;

                if (lc.getRGB() == WorldColors.SAND.getRGB() || lc.getRGB() == WorldColors.WATER.getRGB())
                    tile = Tiles.SAND;

                if (lc.getRGB() == WorldColors.STONE.getRGB())
                    tile = Tiles.STONE;

                tile = tile.newCopy();
                checkBorderTilePos(tile, x, y, true);
                tiles.setTileAtPos(x, y, tile.pos(new TilePos(x, y)));
            }
        }
        generateTiles(tiles, seedOreOffInc, true);
        bgTilesGenerated = true;
        logger.info("Background tiles generated");
        return tiles;
    }

    public TileList generateForegroundTiles() {
        TileList tiles = new TileList(worldSize);
        for (int y = 0; y < worldSize; y++) {
            for (int x = 0; x < worldSize; x++) {
                Color lc = new Color(WorldMapGenerator.getRGBA(landMap.getRGB(x, y)));
                Color pc = new Color(WorldMapGenerator.getRGBA(pathMap.getRGB(x, y)));
                Tile tile = Tiles.AIR;

                if (lc.getRGB() == WorldColors.GRASS.getRGB())
                    tile = Tiles.GRASS;

                if (lc.getRGB() == WorldColors.DEEP_OCEAN.getRGB() || lc.getRGB() == WorldColors.WATER.getRGB())
                    tile = Tiles.WATER;

                if (lc.getRGB() == WorldColors.SAND.getRGB())
                    tile = Tiles.SAND;

                if (lc.getRGB() == WorldColors.RED_SAND.getRGB())
                    tile = Tiles.RED_SAND;

                if (lc.getRGB() == WorldColors.STONE.getRGB())
                    tile = Tiles.STONE;

                if (lc.getRGB() == WorldColors.DIRT.getRGB() || pc.getRGB() == WorldColors.DIRT.getRGB())
                    tile = Tiles.DIRT;

                tile = tile.newCopy();
                checkBorderTilePos(tile, x, y, false);
                tiles.setTileAtPos(x, y, tile.pos(new TilePos(x, y)));
            }
        }
        generateTiles(tiles, 0, false);
        fgTilesGenerated = true;
        logger.info("Foreground tiles generated");
        return tiles;
    }

    private void generateTiles(TileList tiles, long seedOff, boolean bg) {
        double oreSize = 1.0d;
        double stoneSize = 10.0d;

        generateTile(tiles, Tiles.COAL_ORE, -0.05d, 0.02d, oreSize, seedOff, bg);
        generateTile(tiles, Tiles.IRON_ORE, -0.05d, 0.03d, oreSize, seedOff, bg);
        generateTile(tiles, Tiles.GOLD_ORE, -0.05d, 0.04d, oreSize, seedOff, bg);
        generateTile(tiles, Tiles.DIAMOND_ORE, -0.05d, 0.05d, oreSize, seedOff, bg);
        generateTile(tiles, Tiles.EMERALD_ORE, -0.05d, 0.05d, oreSize, seedOff, bg);
        generateTile(tiles, Tiles.AMETHYST_ORE, -0.05d, 0.05d, oreSize, seedOff, bg);

        generateTile(tiles, Tiles.ANDESITE, 0.7d, 1.0d, stoneSize, seedOff, bg);
        generateTile(tiles, Tiles.DIORITE, 0.7d, 1.0d, stoneSize, seedOff, bg);

//        addOre(tiles, Tiles.BROKEN_STONE, -0.15d, 0.15d, stoneSize, seedOff, bg);
    }

    /**
     * Generates a specific tile wit
     **/
    private void generateTile(TileList tiles, Tile ore, double minThreshold, double maxThreshold, double blendSize, long seedOff, boolean bg) {
        SuperSimplexNoise noise = new SuperSimplexNoise(seed + seedOreOff + seedOff);
        seedOreOff += seedOreOffInc;

        for (int y = 0; y < worldSize; y++) {
            for (int x = 0; x < worldSize; x++) {
                double value = noise.noise2((float) (x / blendSize), (float) (y / blendSize));
                if (tiles.getTileAtPos(x, y) instanceof StoneTile) {
                    Tile tile = tiles.getTileAtPos(x, y);

                    if (MatUtil.isValueBetweenThreshold(value, minThreshold, maxThreshold)) {
                        tile = ore.newCopy();
                    }

                    checkBorderTilePos(tile, x, y, bg);
                    tiles.setTileAtPos(x, y, tile.pos(new TilePos(x, y)));
                }
            }
        }
    }

    private void checkBorderTilePos(Tile tile, int x, int y, boolean bg) {
        if (bg)
            tile.isSolid(false);
        if (x == 0 || y == 0 || x >= worldSize - 1 || y >= worldSize - 1) {
            tile.isSolid(true);
            tile.isUnbreakable(true);
        }
    }

    public void setBlendSize(double blendSize) {
        this.blendSize = blendSize;
    }

    /**
     * The tiles in the background, can not be mined and can be walked over, does not have any collision
     **/
    public TileList getBackgroundTiles() {
        return bgTiles;
    }

    /**
     * The tiles in the foreground, can be mined and some can be walked through
     **/
    public TileList getForegroundTiles() {
        return fgTiles;
    }

    public boolean isGenerated() {
        return imageMapsGenerated && bgTilesGenerated && fgTilesGenerated;
    }

    public boolean isGenerating() {
        if (generatorThread == null) return false;
        return generatorThread.isAlive();
    }
}
