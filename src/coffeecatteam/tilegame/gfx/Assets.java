package coffeecatteam.tilegame.gfx;

import java.awt.image.BufferedImage;

public class Assets {

    private static final int width = 16, height = 16;

    /* Terrain Tiles */
    public static BufferedImage GRASS, DIRT, SAND;
    public static BufferedImage STONE, ANDESITE, DIORITE, COAL_ORE, IRON_COAL, GOLD_ORE, DIAMOND_ORE, OBSIDIAN;
    public static BufferedImage WATER, LAVA;

    public static void init() {
        SpriteSheet terrainSheet =  new SpriteSheet(ImageLoader.loadImage("/assets/textures/terrain_tiles.png"));

        /* Terrain Tiles */
        GRASS = getSprite(terrainSheet, 0, 0, width, height);
        DIRT = getSprite(terrainSheet, 1, 0, width, height);
        STONE = getSprite(terrainSheet, 2, 0, width, height);
        SAND = getSprite(terrainSheet, 3, 0, width, height);
        ANDESITE = getSprite(terrainSheet, 4, 0, width, height);
        DIORITE = getSprite(terrainSheet, 5, 0, width, height);
        COAL_ORE = getSprite(terrainSheet, 6, 0, width, height);
        IRON_COAL = getSprite(terrainSheet, 7, 0, width, height);
        GOLD_ORE = getSprite(terrainSheet, 0, 1, width, height);
        DIAMOND_ORE = getSprite(terrainSheet, 1, 1, width, height);
        OBSIDIAN = getSprite(terrainSheet, 2, 1, width, height);
        WATER = getSprite(terrainSheet, 3, 1, width, height);
        LAVA = getSprite(terrainSheet, 4, 1, width, height);
    }

    private static BufferedImage getSprite(SpriteSheet sheet, int indexX, int indexY, int width, int height) {
        return sheet.crop(width * indexX, height * indexY, width, height);
    }
}
