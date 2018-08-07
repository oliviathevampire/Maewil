package coffeecatteam.tilegame.gfx;

import coffeecatteam.tilegame.utils.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;

public class Assets {

    private static final int width = 16, height = 16;

    public static Font FONT;

    /* Tiles */
    public static BufferedImage GRASS, DIRT, SAND;
    public static BufferedImage STONE, ANDESITE, DIORITE, COAL_ORE, IRON_ORE, GOLD_ORE, DIAMOND_ORE, OBSIDIAN, BROKEN_STONE;
    public static BufferedImage PLANKS;

    public static BufferedImage[] WATER, LAVA;

    /* Special Tiles */
    public static BufferedImage[] WATER_SPLASH;

    public static BufferedImage[] ULTIMATE_TILE;

    /* Items */
    public static BufferedImage ITEM_STICK, ITEM_ROCK;
    public static BufferedImage ITEM_ROTTEN_FLESH;
    public static BufferedImage ITEM_WOODEN_SWORD, ITEM_STONE_SWORD, ITEM_WOODEN_PICK, ITEM_STONE_PICK;
    public static BufferedImage ITEM_COAL, ITEM_IRON_INGOT, ITEM_GOLD_INGOT, ITEM_DIAMOND;

    /* Health */
    public static BufferedImage[] HEARTS;
    public static BufferedImage[] ARMOR;
    public static BufferedImage[] SPRINT;

    /* Player Frames */
    public static BufferedImage[] PLAYER_IDLE, PLAYER_UP, PLAYER_DOWN, PLAYER_LEFT, PLAYER_RIGHT, PLAYER_DEAD;

    /* Entities */
    public static BufferedImage[] EXTRA_LIFE;

    public static BufferedImage[] ZOMBIE_IDLE, ZOMBIE_UP, ZOMBIE_DOWN, ZOMBIE_LEFT, ZOMBIE_RIGHT;

    /* Nature */
    public static BufferedImage TREE_SMALL, TREE_MEDIUM, TREE_LARGE;
    public static BufferedImage ROCK;

    /* GUI */
    public static BufferedImage BACKGROUND;
    public static BufferedImage TITLE;

    public static BufferedImage[] BUTTON_START = new BufferedImage[2];
    public static BufferedImage[] BUTTON_QUIT = new BufferedImage[2];

    public static SpriteSheet INVENTORY;

    public static void init() {
        FONT = Utils.loadFont("/assets/fonts/slkscr.ttf", 20);

        SpriteSheet terrainSheet =  new SpriteSheet(ImageLoader.loadImage("/assets/textures/tiles/terrain.png"));
        SpriteSheet natureSheet =  new SpriteSheet(ImageLoader.loadImage("/assets/textures/entities/static.png"));

        SpriteSheet healthSheet =  new SpriteSheet(ImageLoader.loadImage("/assets/textures/gui/overlay/health.png"));
        SpriteSheet itemsSheet =  new SpriteSheet(ImageLoader.loadImage("/assets/textures/items.png"));

        SpriteSheet characrerSheet =  new SpriteSheet(ImageLoader.loadImage("/assets/textures/entities/player.png"));
        SpriteSheet zombieSheet =  new SpriteSheet(ImageLoader.loadImage("/assets/textures/entities/zombie.png"));

        SpriteSheet menuSheet =  new SpriteSheet(ImageLoader.loadImage("/assets/textures/gui/menu.png"));

        /* Tiles */
        GRASS = getSprite(terrainSheet, 0, 0, width, height);
        DIRT = getSprite(terrainSheet, 1, 0, width, height);
        STONE = getSprite(terrainSheet, 2, 0, width, height);
        SAND = getSprite(terrainSheet, 3, 0, width, height);
        ANDESITE = getSprite(terrainSheet, 4, 0, width, height);
        DIORITE = getSprite(terrainSheet, 5, 0, width, height);
        COAL_ORE = getSprite(terrainSheet, 6, 0, width, height);
        IRON_ORE = getSprite(terrainSheet, 7, 0, width, height);
        GOLD_ORE = getSprite(terrainSheet, 0, 1, width, height);
        DIAMOND_ORE = getSprite(terrainSheet, 1, 1, width, height);
        OBSIDIAN = getSprite(terrainSheet, 2, 1, width, height);

        PLANKS = getSprite(terrainSheet, 3, 1, width, height);
        BROKEN_STONE = getSprite(terrainSheet, 4, 1, width, height);

        WATER = getFrames(new SpriteSheet(ImageLoader.loadImage("/assets/textures/tiles/water.png")), 0, 15);
        LAVA = getFrames(new SpriteSheet(ImageLoader.loadImage("/assets/textures/tiles/lava.png")), 0, 15);

        /* Special Tiles */
        WATER_SPLASH = getFrames(new SpriteSheet(ImageLoader.loadImage("/assets/textures/water_splash.png")), 0, 15);

        ULTIMATE_TILE = getFrames(new SpriteSheet(ImageLoader.loadImage("/assets/textures/tiles/ultimate.png")), 0, 0, 6, width * 2, height * 2);

        /* Items */
        ITEM_STICK = getSprite(itemsSheet, 0, 0, width, height);
        ITEM_ROCK = getSprite(itemsSheet, 1, 0, width, height);
        ITEM_ROTTEN_FLESH = getSprite(itemsSheet, 2, 0, width, height);

        ITEM_WOODEN_SWORD = getSprite(itemsSheet, 0, 2, width, height);
        ITEM_STONE_SWORD = getSprite(itemsSheet, 1, 2, width, height);
        ITEM_WOODEN_PICK = getSprite(itemsSheet, 0, 3, width, height);
        ITEM_STONE_PICK = getSprite(itemsSheet, 1, 3, width, height);

        ITEM_COAL = getSprite(itemsSheet, 3, 2, width, height);
        ITEM_IRON_INGOT = getSprite(itemsSheet, 2, 3, width, height);
        ITEM_GOLD_INGOT = getSprite(itemsSheet, 3, 3, width, height);
        ITEM_DIAMOND = getSprite(itemsSheet, 2, 2, width, height);

        /* Health */
        HEARTS = getFrames(healthSheet, 0, 0, 4);
        ARMOR = getFrames(healthSheet, 1, 0, 3);
        SPRINT = getFrames(healthSheet, 2, 0, 4);

        /* Player Frames */
        PLAYER_IDLE = getFrames(characrerSheet, 0, 0, 7);
        PLAYER_UP = getFrames(characrerSheet, 1, 0, 3);
        PLAYER_DOWN = getFrames(characrerSheet, 1, 4, 7);
        PLAYER_LEFT = getFrames(characrerSheet, 3, 0, 3);
        PLAYER_RIGHT = getFrames(characrerSheet, 2, 0, 7);
        PLAYER_DEAD = getFrames(characrerSheet, 4, 0, 7);

        /* Entities */
        EXTRA_LIFE = getFrames(new SpriteSheet(ImageLoader.loadImage("/assets/textures/entities/extra_life.png")), 0, 7);

        ZOMBIE_IDLE = getFrames(zombieSheet, 0, 0, 3);
        ZOMBIE_UP = getFrames(zombieSheet, 1, 0, 2);
        ZOMBIE_DOWN = getFrames(zombieSheet, 1, 3, 5);
        ZOMBIE_LEFT = getFrames(zombieSheet, 2, 4, 7);
        ZOMBIE_RIGHT = getFrames(zombieSheet, 2, 0, 3);

        /* Nature */
        TREE_SMALL = getSprite(natureSheet, 0, 0, width, height * 2);
        TREE_MEDIUM = getSprite(natureSheet, 0, 2, width * 2, height * 2);
        TREE_LARGE = getSprite(natureSheet, 0, 4, width * 2, height * 2);
        ROCK = getSprite(natureSheet, 1, 0, width, height);

        /* GUI */
        BACKGROUND = getSprite(new SpriteSheet(ImageLoader.loadImage("/assets/textures/gui/bg.png")), 0, 0, 320, 320);
        TITLE = getSprite(menuSheet, 3, 0, 80, 48);

        BUTTON_START[0] = getSprite(menuSheet, 0, 0, width * 3, height);
        BUTTON_START[1] = getSprite(menuSheet, 0, 1, width * 3, height);
        BUTTON_QUIT[0] = getSprite(menuSheet, 0, 2, width * 3, height);
        BUTTON_QUIT[1] = getSprite(menuSheet, 0, 3, width * 3, height);

        INVENTORY = new SpriteSheet(ImageLoader.loadImage("/assets/textures/gui/inventory.png"));
    }

    private static BufferedImage[] getFrames(SpriteSheet sheet, int xStart, int xEnd) {
        return getFrames(sheet, 0, xStart, xEnd);
    }

    private static BufferedImage[] getFrames(SpriteSheet sheet, int y, int xStart, int xEnd) {
        return getFrames(sheet, y, xStart, xEnd, width, height);
    }

    private static BufferedImage[] getFrames(SpriteSheet sheet, int y, int xStart, int xEnd, int width, int height) {
        BufferedImage[] frames = new BufferedImage[(xEnd - xStart) + 1];
        int index = 0;
        for (int x = xStart; x < xEnd + 1; x++) {
            try {
                frames[index] = sheet.crop(x * width, y * height, width, height);
            } catch (RasterFormatException e) {
                e.printStackTrace();
                frames[index] = ImageLoader.loadImage("/assets/textures/missing.png");
            }
            index++;
        }
        return frames;
    }

    private static BufferedImage getSprite(SpriteSheet sheet, int indexX, int indexY, int width, int height) {
        BufferedImage image = ImageLoader.loadImage("/assets/textures/missing.png");
        try {
            image = sheet.crop(Assets.width * indexX, Assets.height * indexY, width, height);
        } catch (RasterFormatException e) {
            e.printStackTrace();
        }
        return image;
    }
}
