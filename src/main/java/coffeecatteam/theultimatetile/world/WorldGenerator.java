package coffeecatteam.theultimatetile.world;

import coffeecatteam.coffeecatutils.logger.CatLogger;
import coffeecatteam.theultimatetile.objs.tiles.Tile;
import coffeecatteam.theultimatetile.objs.tiles.TilePos;
import coffeecatteam.theultimatetile.objs.tiles.Tiles;
import coffeecatteam.theultimatetile.objs.tiles.stone.TileStone;
import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.world.colormap.WorldColors;
import coffeecatteam.theultimatetile.world.colormap.WorldMapGenerator;
import coffeecatteam.theultimatetile.world.noise.OpenSimplexNoise;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author CoffeeCatRailway
 * Created: 7/01/2019
 */
public class WorldGenerator {

    private TutEngine tutEngine;
    private int worldSize;
    private BufferedImage landMap, pathMap, biomeMap;

    private long seed, seedOreOffInc, seedOreOff;

    private double blendSize = 25.0d;

    private TileList bgTiles, fgTiles;
    private CatLogger logger;

    private boolean imageMapsGenerated = false, bgTilesGenerated = false, fgTilesGenerated = false;
    private Thread generatorThread;

    public WorldGenerator(TutEngine tutEngine, long seed, int worldSize) {
        this.tutEngine = tutEngine;
        this.seed = seed;
        this.worldSize = worldSize;

        this.seedOreOffInc = this.seed / 4;
        this.seedOreOff = this.seedOreOffInc;
    }

    public void generate() {
        generatorThread = new Thread(() -> {
            logger = new CatLogger();
            WorldMapGenerator mapGenerator = new WorldMapGenerator(seed, worldSize, worldSize, blendSize);

            // Land maps
            landMap = mapGenerator.generateLand(0, 0, true);
            pathMap = mapGenerator.generatePaths(0, 0, landMap);
            biomeMap = mapGenerator.generateBiomes(0, 0, landMap, pathMap);
            imageMapsGenerated = true;
            logger.info("World image maps generated");

            // Tiles
            bgTiles = generateBGTiles();
            fgTiles = generateFGTiles();
            logger.info("Tiles generated");

            // Entities
            tutEngine.getEntityManager().reset();
//            for (int y = 0; y < worldSize; y++) {
//                for (int x = 0; x < worldSize; x++) {
//                    Biomes.Biome biome = Biomes.getBiomeAt(biomeMap, x, y);
//                    if (biome.equals(Biomes.FOREST)) {
//                        float threshold = 0.2f;
//                        float xOff = NumberUtils.getRandomFloat(-threshold, threshold);
//                        float yOff = NumberUtils.getRandomFloat(-threshold, threshold);
//                        tutEngine.getEntityManager().addEntity(Entities.BUSH_SMALL, x + xOff, y + yOff, true);
//                    }
//                }
//            }
        }, "WorldGenerator-Thread");
        generatorThread.start();
    }

    private TileList generateBGTiles() {
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

                if (lc.getRGB() == WorldColors.SAND.getRGB() || lc.getRGB() == WorldColors.WATER.getRGB())
                    tile = Tiles.SAND;

                if (lc.getRGB() == WorldColors.STONE.getRGB())
                    tile = Tiles.STONE;
                if(lc.getRGB() == WorldColors.STONE_TILES.getRGB())
                    tile = Tiles.STONE_TILE;

                tile = tile.newCopy();
                checkBorderTilePos(tile, x, y, true);
                tiles.setTile(x, y, tile.setPos(new TilePos(x, y)));
            }
        }
        tiles = generateOres(tiles, seedOreOffInc, true);
        bgTilesGenerated = true;
        logger.info("Background tiles generated");
        return tiles;
    }

    private TileList generateFGTiles() {
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

                if (lc.getRGB() == WorldColors.STONE.getRGB())
                    tile = Tiles.STONE;

                if (lc.getRGB() == WorldColors.DIRT.getRGB() || pc.getRGB() == WorldColors.DIRT.getRGB())
                    tile = Tiles.DIRT;

                tile = tile.newCopy();
                checkBorderTilePos(tile, x, y, false);
                tiles.setTile(x, y, tile.setPos(new TilePos(x, y)));
            }
        }
        tiles = generateOres(tiles, 0, false);
        fgTilesGenerated = true;
        logger.info("Foreground tiles generated");
        return tiles;
    }

    private TileList generateOres(TileList tiles, long seedOff, boolean bg) {
        double oreSize = 1.0d;
        double stoneSize = 10.0d;

        addOre(tiles, Tiles.COAL_ORE, -0.05d, 0.02d, oreSize, seedOff, bg);
        addOre(tiles, Tiles.IRON_ORE, -0.05d, 0.03d, oreSize, seedOff, bg);
        addOre(tiles, Tiles.GOLD_ORE, -0.05d, 0.04d, oreSize, seedOff, bg);
        addOre(tiles, Tiles.DIAMOND_ORE, -0.05d, 0.05d, oreSize, seedOff, bg);

        addOre(tiles, Tiles.ANDESITE, 0.7d, 1.0d, stoneSize, seedOff, bg);
        addOre(tiles, Tiles.DIORITE, 0.7d, 1.0d, stoneSize, seedOff, bg);

        addOre(tiles, Tiles.BROKEN_STONE, -0.15d, 0.15d, stoneSize, seedOff, bg);

        return tiles;
    }

    private void addOre(TileList tiles, Tile ore, double minThreshold, double maxThreshold, double blendSize, long seedOff, boolean bg) {
        OpenSimplexNoise noise = new OpenSimplexNoise(seed + seedOreOff + seedOff);
        seedOreOff += seedOreOffInc;

        for (int y = 0; y < worldSize; y++) {
            for (int x = 0; x < worldSize; x++) {
                double value = noise.eval((float) (x / blendSize), (float) (y / blendSize));
                if (tiles.getTile(x, y) instanceof TileStone) {
                    Tile tile = tiles.getTile(x, y);

                    if (value > minThreshold && value < maxThreshold) {
                        tile = ore.newCopy();
                    }

                    checkBorderTilePos(tile, x, y, bg);
                    tiles.setTile(x, y, tile.setPos(new TilePos(x, y)));
                }
            }
        }
    }

    private void checkBorderTilePos(Tile tile, int x, int y, boolean bg) {
        if (bg)
            tile.setSolid(false);
        if (x == 0 || y == 0 || x >= worldSize - 1 || y >= worldSize - 1) {
            tile.setSolid(true);
            tile.setUnbreakable(true);
        }
    }

    public void setBlendSize(double blendSize) {
        this.blendSize = blendSize;
    }

    public TileList getBgTiles() {
        return bgTiles;
    }

    public TileList getFgTiles() {
        return fgTiles;
    }

    public BufferedImage getBiomeMap() {
        return biomeMap;
    }

    public boolean isGenerated() {
        return imageMapsGenerated && bgTilesGenerated && fgTilesGenerated;
    }

    public boolean isGenerating() {
        if (generatorThread == null) return false;
        return generatorThread.isAlive();
    }
}
