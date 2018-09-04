package coffeecatteam.theultimatetile.gfx;

import coffeecatteam.theultimatetile.utils.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;

public class Assets {

    private static final int width = 16, height = 16;

    /* Fonts */
    public static Font FONT_20, FONT_30, FONT_40;

    /* Tiles */
    public static BufferedImage GRASS, DIRT, SAND;
    public static BufferedImage STONE, ANDESITE, DIORITE, COAL_ORE, IRON_ORE, GOLD_ORE, DIAMOND_ORE, OBSIDIAN, BROKEN_STONE;
    public static BufferedImage PLANKS, BOOKSHELF, CHEST;

    public static BufferedImage[] WATER, LAVA;

    public static BufferedImage AIR;

    /* Special Tiles */
    public static BufferedImage[] SPLASH_EFFECT, SPRINT_EFFECT;
    public static BufferedImage[] ULTIMATE_TILE;

    /* Items */
    public static BufferedImage ITEM_STICK, ITEM_LEAF, ITEM_ROCK;
    public static BufferedImage ITEM_ROTTEN_FLESH, ITEM_BONE, ITEM_BOUNCY_BALL;

    public static BufferedImage ITEM_WOODEN_SWORD, ITEM_WOODEN_PICK, ITEM_WOODEN_HOE;
    public static BufferedImage ITEM_STONE_SWORD, ITEM_STONE_PICK, ITEM_STONE_HOE;

    public static BufferedImage ITEM_COAL, ITEM_IRON_INGOT, ITEM_GOLD_INGOT, ITEM_DIAMOND;

    public static BufferedImage ITEM_CARROT, ITEM_APPLE;

    public static BufferedImage ITEM_COIN_IRON, ITEM_COIN_GOLD;

    /* Player */
    public static BufferedImage[] HEARTS;
    public static BufferedImage[] ARMOR;
    public static BufferedImage[] SPRINT;

    public static BufferedImage[] GLUB_ORB;
    public static BufferedImage[] GLUB_METER;

    /* Player Frames */
    public static BufferedImage[] PLAYER_IDLE, PLAYER_UP, PLAYER_DOWN, PLAYER_LEFT, PLAYER_RIGHT, PLAYER_DEAD;

    /* Entities */
    public static BufferedImage[] EXTRA_LIFE;

    public static BufferedImage[] ZOMBIE_IDLE, ZOMBIE_UP, ZOMBIE_DOWN, ZOMBIE_LEFT, ZOMBIE_RIGHT;
    public static BufferedImage[] SKELETON_IDLE, SKELETON_UP, SKELETON_DOWN, SKELETON_LEFT, SKELETON_RIGHT;
    public static BufferedImage[] BOUNCER_IDLE, BOUNCER_UP, BOUNCER_DOWN, BOUNCER_LEFT, BOUNCER_RIGHT;
    public static BufferedImage[] THING_IDLE, THING_UP, THING_DOWN, THING_LEFT, THING_RIGHT;

    /* Nature */
    public static BufferedImage TREE_SMALL, TREE_MEDIUM, TREE_LARGE;
    public static BufferedImage ROCK_V1, ROCK_V2;
    public static BufferedImage BUSH_SMALL, BUSH_LARGE;
    public static BufferedImage CARROT_CROP;

    /* GUI */
    public static BufferedImage BACKGROUND;
    public static BufferedImage TITLE;
    public static BufferedImage DEAD_OVERLAY;

    public static BufferedImage[] BUTTON_ENABLED = new BufferedImage[3];
    public static BufferedImage[] BUTTON_HOVER = new BufferedImage[3];
    public static BufferedImage[] BUTTON_DISABLED = new BufferedImage[3];

    public static SpriteSheet INVENTORY;

    public static void init() {
        /* Fonts */
        FONT_20 = Utils.loadFont("/assets/fonts/LCD_Solid.ttf", 20);
        FONT_30 = Utils.loadFont("/assets/fonts/LCD_Solid.ttf", 30);
        FONT_40 = Utils.loadFont("/assets/fonts/LCD_Solid.ttf", 40);

        /* Sprite Sheets */
        SpriteSheet terrainSheet = getSheet("/assets/textures/tiles/terrain.png");
        SpriteSheet natureSheet = getSheet("/assets/textures/entities/static.png");
        SpriteSheet effectSheet = getSheet("/assets/textures/effect.png");

        SpriteSheet healthSheet = getSheet("/assets/textures/gui/overlay/health.png");
        SpriteSheet glubSheet = getSheet("/assets/textures/glub.png");
        SpriteSheet itemsSheet = getSheet("/assets/textures/items.png");

        SpriteSheet playerSheet = getSheet("/assets/textures/entities/player.png");
        SpriteSheet undeadSheet = getSheet("/assets/textures/entities/undead.png");

        SpriteSheet menuSheet = getSheet("/assets/textures/gui/menu.png");

        /* Tiles */
        GRASS = getSprite(terrainSheet, 0, 0, width, height);
        DIRT = getSprite(terrainSheet, 1, 0, width, height);
        STONE = getSprite(terrainSheet, 2, 0, width, height);
        SAND = getSprite(terrainSheet, 3, 0, width, height);
        ANDESITE = getSprite(terrainSheet, 4, 0, width, height);
        DIORITE = getSprite(terrainSheet, 5, 0, width, height);
        COAL_ORE = getSprite(terrainSheet, 6, 0, width, height);
        IRON_ORE = getSprite(terrainSheet, 7, 0, width, height);
        GOLD_ORE = getSprite(terrainSheet, 8, 0, width, height);
        DIAMOND_ORE = getSprite(terrainSheet, 9, 0, width, height);
        OBSIDIAN = getSprite(terrainSheet, 10, 0, width, height);

        PLANKS = getSprite(terrainSheet, 11, 0, width, height);
        BROKEN_STONE = getSprite(terrainSheet, 12, 0, width, height);

        WATER = getFrames(terrainSheet, 6, 0, 15);
        LAVA = getFrames(terrainSheet, 7, 0, 15);

        AIR = getSprite(terrainSheet, 13, 0, width, height);

        BOOKSHELF = getSprite(terrainSheet, 14, 0, width, height);
        CHEST = getSprite(terrainSheet, 15, 0, width, height);

        /* Special Tiles */
        SPLASH_EFFECT = getFrames(effectSheet, 0, 0, 15);
        SPRINT_EFFECT = getFrames(effectSheet, 1, 0, 15);

        ULTIMATE_TILE = getFrames("/assets/textures/tiles/ultimate.png", 0, 0, 6, width * 2, height * 2);

        /* Items */
        ITEM_STICK = getSprite(itemsSheet, 0, 0, width, height);
        ITEM_LEAF = getSprite(itemsSheet, 0, 1, width, height);
        ITEM_ROCK = getSprite(itemsSheet, 0, 2, width, height);

        ITEM_ROTTEN_FLESH = getSprite(itemsSheet, 1, 0, width, height);
        ITEM_BONE = getSprite(itemsSheet, 1, 1, width, height);
        ITEM_BOUNCY_BALL = getSprite(itemsSheet, 1, 2, width, height);

        ITEM_WOODEN_SWORD = getSprite(itemsSheet, 6, 0, width, height);
        ITEM_WOODEN_PICK = getSprite(itemsSheet, 6, 1, width, height);
        ITEM_WOODEN_HOE = getSprite(itemsSheet, 6, 2, width, height);

        ITEM_STONE_SWORD = getSprite(itemsSheet, 7, 0, width, height);
        ITEM_STONE_PICK = getSprite(itemsSheet, 7, 1, width, height);
        ITEM_STONE_HOE = getSprite(itemsSheet, 7, 2, width, height);

        ITEM_COAL = getSprite(itemsSheet, 2, 0, width, height);
        ITEM_IRON_INGOT = getSprite(itemsSheet, 2, 1, width, height);
        ITEM_GOLD_INGOT = getSprite(itemsSheet, 2, 2, width, height);
        ITEM_DIAMOND = getSprite(itemsSheet, 2, 3, width, height);

        ITEM_CARROT = getSprite(itemsSheet, 3, 0, width, height);
        ITEM_APPLE = getSprite(itemsSheet, 3, 1, width, height);

        ITEM_COIN_IRON = getSprite(itemsSheet, 4, 0, width, height);
        ITEM_COIN_GOLD = getSprite(itemsSheet, 4, 1, width, height);

        /* Player */
        HEARTS = getFrames(healthSheet, 0, 0, 4);
        ARMOR = getFrames(healthSheet, 1, 0, 3);
        SPRINT = getFrames(healthSheet, 3, 0, 1, width * 2, height);

        GLUB_ORB = getFrames(glubSheet, 0, 0, 5, width, height);
        GLUB_METER = getFrames(glubSheet, 1, 0, 1, width * 4, height);

        /* Player Frames */
        PLAYER_IDLE = getFrames(playerSheet, 0, 0, 7);
        PLAYER_UP = getFrames(playerSheet, 1, 0, 3);
        PLAYER_DOWN = getFrames(playerSheet, 1, 4, 7);
        PLAYER_LEFT = getFrames(playerSheet, 3, 0, 3);
        PLAYER_RIGHT = getFrames(playerSheet, 2, 0, 7);
        PLAYER_DEAD = getFrames(playerSheet, 4, 0, 7);

        /* Entities */
        EXTRA_LIFE = getFrames("/assets/textures/entities/extra_life.png", 0, 7);

        ZOMBIE_IDLE = getFrames(undeadSheet, 0, 0, 3);
        ZOMBIE_UP = getFrames(undeadSheet, 1, 0, 2);
        ZOMBIE_DOWN = getFrames(undeadSheet, 1, 3, 5);
        ZOMBIE_LEFT = getFrames(undeadSheet, 2, 4, 7);
        ZOMBIE_RIGHT = getFrames(undeadSheet, 2, 0, 3);

        SKELETON_IDLE = getFrames(undeadSheet, 4, 0, 3);
        SKELETON_UP = getFrames(undeadSheet, 5, 0, 2);
        SKELETON_DOWN = getFrames(undeadSheet, 5, 3, 5);
        SKELETON_LEFT = getFrames(undeadSheet, 6, 4, 7);
        SKELETON_RIGHT = getFrames(undeadSheet, 6, 0, 3);

        BOUNCER_IDLE = getFrames(undeadSheet, 0, 8, 15);
        BOUNCER_UP = getFrames(undeadSheet, 1, 8, 15);
        BOUNCER_DOWN = getFrames(undeadSheet, 2, 8, 15);
        BOUNCER_LEFT = getFrames(undeadSheet, 4, 8, 15);
        BOUNCER_RIGHT = getFrames(undeadSheet, 3, 8, 15);

        THING_IDLE = getFrames(undeadSheet, 8, 0, 9);
        THING_UP = getFrames(undeadSheet, 9, 0, 9);
        THING_DOWN = getFrames(undeadSheet, 10, 0, 9);
        THING_LEFT = getFrames(undeadSheet, 11, 0, 9);
        THING_RIGHT = getFrames(undeadSheet, 12, 0, 9);

        /* Nature */
        TREE_SMALL = getSprite(natureSheet, 0, 0, width, height * 2);
        TREE_MEDIUM = getSprite(natureSheet, 0, 2, width * 2, height * 2);
        TREE_LARGE = getSprite(natureSheet, 0, 4, width * 2, height * 2);
        ROCK_V1 = getSprite(natureSheet, 1, 0, width, height);
        ROCK_V2 = getSprite(natureSheet, 1, 1, width, height);
        BUSH_SMALL = getSprite(natureSheet, 2, 0, width, height);
        BUSH_LARGE = getSprite(natureSheet, 2, 1, width * 2, height);
        CARROT_CROP = getSprite(natureSheet, 3, 0, width, height);

        /* GUI */
        BACKGROUND = getSprite("/assets/textures/gui/bg.png", 0, 0, 320, 320);
        TITLE = getSprite(menuSheet, 3, 0, 80, 48);
        DEAD_OVERLAY = getSprite("/assets/textures/gui/dead_overlay.png", 0, 0, 512, 512);

        BUTTON_ENABLED = getFrames(menuSheet, 0, 0, 2, width, height);
        BUTTON_HOVER = getFrames(menuSheet, 1, 0, 2, width, height);
        BUTTON_DISABLED = getFrames(menuSheet, 2, 0, 2, width, height);

        INVENTORY = getSheet("/assets/textures/gui/inventory.png");
    }

    private static BufferedImage[] getFrames(String sheet, int xStart, int xEnd) {
        return getFrames(sheet, 0, xStart, xEnd);
    }

    private static BufferedImage[] getFrames(String sheet, int y, int xStart, int xEnd) {
        return getFrames(getSheet(sheet), y, xStart, xEnd, width, height);
    }

    private static BufferedImage[] getFrames(String sheet, int y, int xStart, int xEnd, int width, int height) {
        return getFrames(getSheet(sheet), y, xStart, xEnd, width, height);
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

    private static BufferedImage getSprite(String sheet, int indexX, int indexY, int width, int height) {
        return getSprite(getSheet(sheet), indexX, indexY, width, height);
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
    
    private static SpriteSheet getSheet(String path) {
        return new SpriteSheet(ImageLoader.loadImage(path));
    }
}
