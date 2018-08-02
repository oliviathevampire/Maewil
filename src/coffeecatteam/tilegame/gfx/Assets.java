package coffeecatteam.tilegame.gfx;

import java.awt.image.BufferedImage;

public class Assets {

    private static final int width = 16, height = 16;

    /* Terrain Tiles */
    public static BufferedImage GRASS, DIRT, SAND;
    public static BufferedImage STONE, ANDESITE, DIORITE, COAL_ORE, IRON_COAL, GOLD_ORE, DIAMOND_ORE, OBSIDIAN;

    public static BufferedImage[] WATER, LAVA;
    public static BufferedImage[] WATER_SPLASH;

    /* Player Frames */
    public static BufferedImage[] PLAYER_IDLE;
    public static BufferedImage[] PLAYER_UP;
    public static BufferedImage[] PLAYER_DOWN;
    public static BufferedImage[] PLAYER_LEFT;
    public static BufferedImage[] PLAYER_RIGHT;

    public static void init() {
        SpriteSheet terrainSheet =  new SpriteSheet(ImageLoader.loadImage("/assets/textures/terrain_tiles.png"));
        SpriteSheet characrerSheet =  new SpriteSheet(ImageLoader.loadImage("/assets/textures/character_sheet.png"));

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

        WATER = getFrames(new SpriteSheet(ImageLoader.loadImage("/assets/textures/water.png")), 0, 15);
        LAVA = getFrames(new SpriteSheet(ImageLoader.loadImage("/assets/textures/lava.png")), 0, 15);

        /* Player Frames */
        PLAYER_IDLE = getFrames(characrerSheet, 0, 0, 7);
        PLAYER_UP = getFrames(characrerSheet, 1, 0, 2);
        PLAYER_DOWN = getFrames(characrerSheet, 1, 3, 5);
        PLAYER_LEFT = getFrames(characrerSheet, 3, 0, 3);
        PLAYER_RIGHT = getFrames(characrerSheet, 2, 0, 7);
    }

    private static BufferedImage[] getFrames(SpriteSheet sheet, int xStart, int xEnd) {
        return getFrames(sheet, 0, xStart, xEnd);
    }

    private static BufferedImage[] getFrames(SpriteSheet sheet, int y, int xStart, int xEnd) {
        BufferedImage[] frames = new BufferedImage[(xEnd - xStart) + 1];
        int index = 0;
        for (int x = xStart; x < xEnd + 1; x++) {
            frames[index] = sheet.crop(x * width, y * height, width, height);
            index++;
        }
        return frames;
    }

    private static BufferedImage getSprite(SpriteSheet sheet, int indexX, int indexY, int width, int height) {
        return sheet.crop(width * indexX, height * indexY, width, height);
    }
}
