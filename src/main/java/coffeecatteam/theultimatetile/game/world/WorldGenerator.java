package coffeecatteam.theultimatetile.game.world;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.theultimatetile.game.tile.Tile;
import coffeecatteam.theultimatetile.game.tile.Tiles;
import coffeecatteam.theultimatetile.utils.noise.NoiseMapGenerator;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author CoffeeCatRailway
 * Created: 7/01/2019
 */
public class WorldGenerator {

    private int width, height;
    private BufferedImage landMap, pathMap;
    private double blendSize = 25d;

    private Tile[][] fg_tiles, bg_tiles;

    public WorldGenerator(int width, int height) {
        this((long) (Math.random() * 1000000000), width, height);
    }

    public WorldGenerator(long seed, int width, int height) {
        this.width = width;
        this.height = height;

        try {
            landMap = NoiseMapGenerator.generateLand(seed, this.width, this.height, blendSize);
            pathMap = NoiseMapGenerator.generatePaths(seed, this.width, this.height, blendSize - NumberUtils.getRandomInt(2, 5), landMap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.fg_tiles = generateTiles(landMap, pathMap, false);
        this.bg_tiles = generateTiles(landMap, pathMap, true);
    }

    public Tile[][] generateTiles(BufferedImage landMap, BufferedImage pathMap, boolean bg) {
        Tile[][] out = new Tile[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pColor = landMap.getRGB(x, y);
                Tile t = Tiles.AIR.copy();
                if (pColor == NoiseMapGenerator.DEAP_OCEAN.getRGB() || pColor == NoiseMapGenerator.OCEAN.getRGB()) {
                    if (bg) {
                        if (pColor == NoiseMapGenerator.DEAP_OCEAN.getRGB()) {
                            t = Tiles.DIRT.copy();
                        }
                        if (pColor == NoiseMapGenerator.OCEAN.getRGB()) {
                            t = Tiles.SAND.copy();
                        }
                    } else {
                        t = Tiles.WATER.copy();
                    }
                }
                if (pColor == NoiseMapGenerator.SAND.getRGB()) {
                    t = Tiles.SAND.copy();
                }
                if (pColor == NoiseMapGenerator.GRASS.getRGB()) {
                    if (bg) {
                        t = Tiles.DIRT.copy();
                    } else {
                        t = Tiles.GRASS.copy();
                    }
                }
                if (pColor == NoiseMapGenerator.STONE.getRGB()) {
                    if (bg) {
                        t = Tiles.DIRT.copy();
                    } else {
                        t = Tiles.STONE.setSolid(true).copy();
                    }
                }
                if (pColor == NoiseMapGenerator.DIRT.getRGB()) {
                    t = Tiles.DIRT.copy();
                }
                out[x][y] = t.copy();
            }
        }
        if (bg)
            return out;
        else
            return generatePaths(pathMap, out);
    }

    public Tile[][] generatePaths(BufferedImage map, Tile[][] tileSet) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pColor = map.getRGB(x, y);
                if (pColor == NoiseMapGenerator.DIRT.getRGB()) {
                    tileSet[x][y] = Tiles.DIRT.copy();
                }
            }
        }
        return tileSet;
    }

    public Tile[][] getFg_tiles() {
        return fg_tiles;
    }

    public Tile[][] getBg_tiles() {
        return bg_tiles;
    }

    public BufferedImage getLandMap() {
        return landMap;
    }

    public BufferedImage getPathMap() {
        return pathMap;
    }
}
