package coffeecatteam.theultimatetile.world;

import coffeecatteam.coffeecatutils.logger.CatLogger;
import coffeecatteam.theultimatetile.tile.Tile;
import coffeecatteam.theultimatetile.tile.TilePos;
import coffeecatteam.theultimatetile.tile.Tiles;
import coffeecatteam.theultimatetile.tile.tiles.TileStone;
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

    private int worldSize;
    private BufferedImage landMap, pathMap, biomeMap;

    private long seed;
    private long seedOreOffInc = seed / 4, seedOreOff = seedOreOffInc;

    private double blendSize = 25.0d;

    private Tile[][] bgTiles, fgTiles;
    private CatLogger logger;

    private boolean imageMapsGenerated = false, bgTilesGenerated = false, fgTilesGenerated = false;
    private Thread generatorThread;

    public WorldGenerator(long seed, int worldSize) {
        this.seed = seed;
        this.worldSize = worldSize;
    }

    public void generate() {
        generatorThread = new Thread(() -> {
            logger = new CatLogger();
            WorldMapGenerator mapGenerator = new WorldMapGenerator(seed, worldSize, worldSize, blendSize);

            landMap = mapGenerator.generateLand(0, 0, true);
            pathMap = mapGenerator.generatePaths(0, 0, landMap);
            biomeMap = mapGenerator.generateBiomes(0, 0, landMap, pathMap);
            imageMapsGenerated = true;
            logger.print("World image maps generated");

            bgTiles = generateBGTiles();
            fgTiles = generateFGTiles();
            logger.print("Tiles generated");
        }, "WorldGenerator-Thread");
        generatorThread.start();
    }

    private Tile[][] generateBGTiles() {
        Tile[][] tiles = new Tile[worldSize][worldSize];
        for (int y = 0; y < worldSize; y++) {
            for (int x = 0; x < worldSize; x++) {
                Color lc = new Color(WorldMapGenerator.getRGBA(landMap.getRGB(x, y)));
                Color pc = new Color(WorldMapGenerator.getRGBA(pathMap.getRGB(x, y)));
                Tile tile = Tiles.AIR.newTile();

                if (lc.getRGB() == WorldColors.DIRT.getRGB()
                        || pc.getRGB() == WorldColors.DIRT.getRGB()
                        || lc.getRGB() == WorldColors.GRASS.getRGB()
                        || lc.getRGB() == WorldColors.DEAP_OCEAN.getRGB())
                    tile = Tiles.DIRT.newTile();

                if (lc.getRGB() == WorldColors.SAND.getRGB() || lc.getRGB() == WorldColors.OCEAN.getRGB())
                    tile = Tiles.SAND.newTile();

                if (lc.getRGB() == WorldColors.STONE.getRGB())
                    tile = Tiles.STONE.newTile();

                checkBorderTilePos(tile, x, y, true);
                tiles[x][y] = tile.setPos(new TilePos(x, y));
            }
        }
        tiles = generateOres(tiles, true);
        bgTilesGenerated = true;
        logger.print("Background tiles generated");
        return tiles;
    }

    private Tile[][] generateFGTiles() {
        Tile[][] tiles = new Tile[worldSize][worldSize];
        for (int y = 0; y < worldSize; y++) {
            for (int x = 0; x < worldSize; x++) {
                Color lc = new Color(WorldMapGenerator.getRGBA(landMap.getRGB(x, y)));
                Color pc = new Color(WorldMapGenerator.getRGBA(pathMap.getRGB(x, y)));
                Tile tile = Tiles.AIR.newTile();

                if (lc.getRGB() == WorldColors.GRASS.getRGB())
                    tile = Tiles.GRASS.newTile();

                if (lc.getRGB() == WorldColors.DEAP_OCEAN.getRGB() || lc.getRGB() == WorldColors.OCEAN.getRGB())
                    tile = Tiles.WATER.newTile();

                if (lc.getRGB() == WorldColors.SAND.getRGB())
                    tile = Tiles.SAND.newTile();

                if (lc.getRGB() == WorldColors.STONE.getRGB())
                    tile = Tiles.STONE.newTile();

                if (lc.getRGB() == WorldColors.DIRT.getRGB() || pc.getRGB() == WorldColors.DIRT.getRGB())
                    tile = Tiles.DIRT.newTile();

                checkBorderTilePos(tile, x, y, false);
                tiles[x][y] = tile.setPos(new TilePos(x, y));
            }
        }
        tiles = generateOres(tiles, false);
        fgTilesGenerated = true;
        logger.print("Foreground tiles generated");
        return tiles;
    }

    private Tile[][] generateOres(Tile[][] tiles, boolean bg) {
        double oreSize = 1.0d;
        double stoneSize = 10.0d;

        addOre(tiles, Tiles.COAL_ORE, -0.05d, 0.05d, oreSize, bg);
        addOre(tiles, Tiles.IRON_ORE, -0.05d, 0.05d, oreSize, bg);
        addOre(tiles, Tiles.GOLD_ORE, -0.02d, 0.02d, oreSize, bg);
        addOre(tiles, Tiles.DIAMOND_ORE, -0.02d, 0.02d, oreSize, bg);

        addOre(tiles, Tiles.ANDESITE, 0.7d, 1.0d, stoneSize, bg);
        addOre(tiles, Tiles.DIORITE, 0.7d, 1.0d, stoneSize, bg);

        addOre(tiles, Tiles.BROKEN_STONE, -0.15d, 0.15d, stoneSize, bg);

        return tiles.clone();
    }

    private void addOre(Tile[][] tiles, Tile ore, double minThreshold, double maxThreshold, double blendSize, boolean bg) {
        OpenSimplexNoise noise = new OpenSimplexNoise(seed + seedOreOff);
        seedOreOff += seedOreOffInc;

        for (int y = 0; y < worldSize; y++) {
            for (int x = 0; x < worldSize; x++) {
                double value = noise.eval((float) (x / blendSize), (float) (y / blendSize));
                if (tiles[x][y] instanceof TileStone) {
                    Tile tile = tiles[x][y];

                    if (value > minThreshold && value < maxThreshold) {
                        tile = ore.newTile();
                    }

                    checkBorderTilePos(tile, x, y, bg);
                    tiles[x][y] = tile.setPos(new TilePos(x, y));
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

    public Tile[][] getBgTiles() {
        return bgTiles;
    }

    public Tile[][] getFgTiles() {
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
