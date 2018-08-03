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

    /* Nature */
    public static BufferedImage TREE_SMALL, TREE_MEDIUM, TREE_LARGE;
    public static BufferedImage ROCK;

    /* GUI */
    public static BufferedImage[] BUTTON_START = new BufferedImage[2];
    public static BufferedImage[] BUTTON_QUIT = new BufferedImage[2];

    public static void init() {
        SpriteSheet terrainSheet =  new SpriteSheet(ImageLoader.loadImage("/assets/textures/tiles/terrain.png"));
        SpriteSheet characrerSheet =  new SpriteSheet(ImageLoader.loadImage("/assets/textures/entities/player.png"));
        SpriteSheet natureSheet =  new SpriteSheet(ImageLoader.loadImage("/assets/textures/entities/static.png"));
        SpriteSheet menuSheet =  new SpriteSheet(ImageLoader.loadImage("/assets/textures/gui/menu.png"));

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

        WATER = getFrames(new SpriteSheet(ImageLoader.loadImage("/assets/textures/tiles/water.png")), 0, 15);
        LAVA = getFrames(new SpriteSheet(ImageLoader.loadImage("/assets/textures/tiles/lava.png")), 0, 15);

        /* Player Frames */
        PLAYER_IDLE = getFrames(characrerSheet, 0, 0, 7);
        PLAYER_UP = getFrames(characrerSheet, 1, 0, 2);
        PLAYER_DOWN = getFrames(characrerSheet, 1, 3, 5);
        PLAYER_LEFT = getFrames(characrerSheet, 3, 0, 3);
        PLAYER_RIGHT = getFrames(characrerSheet, 2, 0, 7);

        /* Nature */
        TREE_SMALL = getSprite(natureSheet, 0, 0, width, height * 2);
        TREE_MEDIUM = getSprite(natureSheet, 0, 1, width * 2, height * 2);
        TREE_LARGE = getSprite(natureSheet, 0, 2, width * 2, height * 2);
        ROCK = getSprite(natureSheet, 1, 0, width, height);

        /* GUI */
        BUTTON_START[0] = getSprite(menuSheet, 0, 0, width * 3, height);
        BUTTON_START[1] = getSprite(menuSheet, 0, 1, width * 3, height);
        BUTTON_QUIT[0] = getSprite(menuSheet, 0, 2, width * 3, height);
        BUTTON_QUIT[1] = getSprite(menuSheet, 0, 3, width * 3, height);
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
