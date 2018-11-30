package coffeecatteam.theultimatetile.gfx;

import coffeecatteam.coffeecatutils.Logger;
import coffeecatteam.coffeecatutils.io.FileUtils;
import coffeecatteam.coffeecatutils.io.FontLoader;
import coffeecatteam.theultimatetile.utils.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;

public class Assets {

    private static final int width = 16, height = 16;
    private static final BufferedImage MISSING_TEXTURE = ImageLoader.loadImage("/assets/textures/missing.png");

    /* Sprite Sheets */
    private static SpriteSheet terrainSheet;
    private static SpriteSheet natureSheet;
    private static SpriteSheet staticSheet;
    private static SpriteSheet effectSheet;

    private static SpriteSheet healthSheet;
    private static SpriteSheet glubSheet;
    private static SpriteSheet itemsSheet;

    private static SpriteSheet playerSheet;
    private static SpriteSheet undeadSheet;
    private static SpriteSheet passiveSheet;

    private static SpriteSheet menuSheet;
    private static SpriteSheet invSheet;
    private static SpriteSheet campfireInvSheet;

    /* Fonts */
    public static Font FONT_20, FONT_30, FONT_40, FONT_80;

    /* Tiles */
    public static BufferedImage GRASS, DIRT, SAND;
    public static BufferedImage STONE, ANDESITE, DIORITE, COAL_ORE, IRON_ORE, GOLD_ORE, DIAMOND_ORE, OBSIDIAN, BROKEN_STONE;
    public static BufferedImage PLANKS, BOOKSHELF, CHEST;
    public static BufferedImage[] TILE_CRACKING;

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

    public static BufferedImage ITEM_CARROT, ITEM_APPLE, ITEM_RAW_PORK, ITEM_COOKED_PORK, ITEM_BREAD, ITEM_WHEAT, ITEM_RAW_STEAK, ITEM_COOKED_STEAK, ITEM_WOOL_BUNDLE, ITEM_POTATO, ITEM_TOMATO, ITEM_CORN;

    public static BufferedImage ITEM_COIN_PENNY, ITEM_COIN_IRON, ITEM_COIN_GOLD;

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

    // Undead
    public static BufferedImage[] ZOMBIE_IDLE, ZOMBIE_UP, ZOMBIE_DOWN, ZOMBIE_LEFT, ZOMBIE_RIGHT;
    public static BufferedImage[] SKELETON_IDLE, SKELETON_UP, SKELETON_DOWN, SKELETON_LEFT, SKELETON_RIGHT;
    public static BufferedImage[] BOUNCER_IDLE, BOUNCER_UP, BOUNCER_DOWN, BOUNCER_LEFT, BOUNCER_RIGHT;
    public static BufferedImage[] THING_IDLE, THING_UP, THING_DOWN, THING_LEFT, THING_RIGHT;

    // Passive
    public static BufferedImage[] PIG_IDLE, PIG_UP, PIG_DOWN, PIG_LEFT, PIG_RIGHT;
    public static BufferedImage[] COW_IDLE, COW_UP, COW_DOWN, COW_LEFT, COW_RIGHT;
    public static BufferedImage[] SHEEP_IDLE, SHEEP_UP, SHEEP_DOWN, SHEEP_LEFT, SHEEP_RIGHT;

    /* Nature / Statics */
    public static BufferedImage TREE_SMALL, TREE_MEDIUM, TREE_LARGE;
    public static BufferedImage ROCK_V1, ROCK_V2;
    public static BufferedImage BUSH_SMALL, BUSH_LARGE;
    public static BufferedImage CROP_GROUND, CROP_CARROT, CROP_WHEAT, CROP_POTATO, CROP_TOMATO, CROP_CORN;

    public static BufferedImage SHOP_STALL;
    public static BufferedImage[] SHOP_ROOF_ORANGE, SHOP_ROOF_BLUE, SHOP_ROOF_RED, SHOP_ROOF_GREY;

    public static BufferedImage[] CAMPFIRE;

    /* GUI */
    public static BufferedImage BACKGROUND;
    public static BufferedImage TITLE;
    public static BufferedImage DEAD_OVERLAY;

    public static BufferedImage[] BUTTON_ENABLED = new BufferedImage[3];
    public static BufferedImage[] BUTTON_HOVER = new BufferedImage[3];
    public static BufferedImage[] BUTTON_DISABLED = new BufferedImage[3];

    public static BufferedImage INVENTORY, CAMPFIRE_INVENTORY;
    public static BufferedImage SLOT, SLOT_SELECTER;
    public static BufferedImage HOTBAR, HOTBAR_SELECTER;

    public static BufferedImage ICON_ON, ICON_OFF;
    public static BufferedImage TOOLTIP_SHORT_SMALL, TOOLTIP_SHORT_LARGE, TOOLTIP_LONG_SMALL, TOOLTIP_LONG_LARGE;

    public static BufferedImage LOGO;

    public static BufferedImage[] SLIDER_BUTTON;
    public static BufferedImage SLIDER_BAR;

    public static BufferedImage CHECK_BOX_BG, CHECK_BOX_FG;

    // Level Creator
    public static BufferedImage MG_OVERLAY_OUTER, MG_OVERLAY_INNER_MID, MG_OVERLAY_INNER_MID_RIGHT;
    public static BufferedImage EDIT_GRID_TILE, SELECTED_TILE;

    /* Fonts */
    private static void initFonts() {
        String fontPath = "/assets/fonts/LCD_Solid.ttf";
        FONT_20 = FontLoader.loadTrueTypeFont(fontPath, 20);
        FONT_30 = FontLoader.loadTrueTypeFont(fontPath, 30);
        FONT_40 = FontLoader.loadTrueTypeFont(fontPath, 40);
        FONT_80 = FontLoader.loadTrueTypeFont(fontPath, 80);

        Logger.print("Assets [Fonts] loaded!");
    }

    /* Tiles */
    private static void initTiles() {
        GRASS = getSpriteInd(terrainSheet, 0, 0, width, height);
        DIRT = getSpriteInd(terrainSheet, 1, 0, width, height);
        STONE = getSpriteInd(terrainSheet, 2, 0, width, height);
        SAND = getSpriteInd(terrainSheet, 3, 0, width, height);
        ANDESITE = getSpriteInd(terrainSheet, 4, 0, width, height);
        DIORITE = getSpriteInd(terrainSheet, 5, 0, width, height);
        COAL_ORE = getSpriteInd(terrainSheet, 6, 0, width, height);
        IRON_ORE = getSpriteInd(terrainSheet, 7, 0, width, height);
        GOLD_ORE = getSpriteInd(terrainSheet, 8, 0, width, height);
        DIAMOND_ORE = getSpriteInd(terrainSheet, 9, 0, width, height);
        OBSIDIAN = getSpriteInd(terrainSheet, 10, 0, width, height);

        PLANKS = getSpriteInd(terrainSheet, 11, 0, width, height);
        BROKEN_STONE = getSpriteInd(terrainSheet, 12, 0, width, height);

        WATER = getFrames(terrainSheet, 6, 0, 15);
        LAVA = getFrames(terrainSheet, 7, 0, 15);

        AIR = getSpriteInd(terrainSheet, 13, 0, width, height);

        BOOKSHELF = getSpriteInd(terrainSheet, 14, 0, width, height);
        CHEST = getSpriteInd(terrainSheet, 15, 0, width, height);

        TILE_CRACKING = getFrames("/assets/textures/tiles/tile_cracking.png", 0, 8);

        /* Special Tiles */
        SPLASH_EFFECT = getFrames(effectSheet, 0, 0, 15);
        SPRINT_EFFECT = getFrames(effectSheet, 1, 0, 15);

        ULTIMATE_TILE = getFrames("/assets/textures/tiles/ultimate.png", 0, 0, 6, width * 2, height * 2);

        Logger.print("Assets [Tiles] loaded!");
    }

    /* Items */
    private static void initItems() {
        ITEM_STICK = getSpriteInd(itemsSheet, 0, 0, width, height);
        ITEM_LEAF = getSpriteInd(itemsSheet, 0, 1, width, height);
        ITEM_ROCK = getSpriteInd(itemsSheet, 0, 2, width, height);

        ITEM_ROTTEN_FLESH = getSpriteInd(itemsSheet, 1, 0, width, height);
        ITEM_BONE = getSpriteInd(itemsSheet, 1, 1, width, height);
        ITEM_BOUNCY_BALL = getSpriteInd(itemsSheet, 1, 2, width, height);

        ITEM_WOODEN_SWORD = getSpriteInd(itemsSheet, 6, 0, width, height);
        ITEM_WOODEN_PICK = getSpriteInd(itemsSheet, 6, 1, width, height);
        ITEM_WOODEN_HOE = getSpriteInd(itemsSheet, 6, 2, width, height);

        ITEM_STONE_SWORD = getSpriteInd(itemsSheet, 7, 0, width, height);
        ITEM_STONE_PICK = getSpriteInd(itemsSheet, 7, 1, width, height);
        ITEM_STONE_HOE = getSpriteInd(itemsSheet, 7, 2, width, height);

        ITEM_COAL = getSpriteInd(itemsSheet, 2, 0, width, height);
        ITEM_IRON_INGOT = getSpriteInd(itemsSheet, 2, 1, width, height);
        ITEM_GOLD_INGOT = getSpriteInd(itemsSheet, 2, 2, width, height);
        ITEM_DIAMOND = getSpriteInd(itemsSheet, 2, 3, width, height);

        ITEM_CARROT = getSpriteInd(itemsSheet, 3, 0, width, height);
        ITEM_APPLE = getSpriteInd(itemsSheet, 3, 1, width, height);
        ITEM_RAW_PORK = getSpriteInd(itemsSheet, 3, 2, width, height);
        ITEM_COOKED_PORK = getSpriteInd(itemsSheet, 3, 3, width, height);
        ITEM_BREAD = getSpriteInd(itemsSheet, 3, 4, width, height);
        ITEM_WHEAT = getSpriteInd(itemsSheet, 3, 5, width, height);
        ITEM_RAW_STEAK = getSpriteInd(itemsSheet, 4, 0, width, height);
        ITEM_COOKED_STEAK = getSpriteInd(itemsSheet, 4, 1, width, height);
        ITEM_WOOL_BUNDLE = getSpriteInd(itemsSheet, 1, 3, width, height);
        ITEM_POTATO = getSpriteInd(itemsSheet, 4, 2, width, height);
        ITEM_TOMATO = getSpriteInd(itemsSheet, 3, 6, width, height);
        ITEM_CORN = getSpriteInd(itemsSheet, 3, 7, width, height);

        ITEM_COIN_PENNY = getSpriteInd(itemsSheet, 5, 0, width, height);
        ITEM_COIN_IRON = getSpriteInd(itemsSheet, 5, 1, width, height);
        ITEM_COIN_GOLD = getSpriteInd(itemsSheet, 5, 2, width, height);

        Logger.print("Assets [Items] loaded!");
    }

    /* Entities */
    private static void initEntities() {
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

        // Undead
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

        // Passive
        PIG_IDLE = getFrames(passiveSheet, 0, 0, 7);
        PIG_UP = getFrames(passiveSheet, 1, 0, 3);
        PIG_DOWN = getFrames(passiveSheet, 1, 4, 7);
        PIG_LEFT = getFrames(passiveSheet, 2, 4, 7);
        PIG_RIGHT = getFrames(passiveSheet, 2, 0, 3);

        COW_IDLE = getFrames(passiveSheet, 3, 0, 7);
        COW_UP = getFrames(passiveSheet, 4, 0, 3);
        COW_DOWN = getFrames(passiveSheet, 4, 4, 7);
        COW_LEFT = getFrames(passiveSheet, 5, 4, 7);
        COW_RIGHT = getFrames(passiveSheet, 5, 0, 3);

        SHEEP_IDLE = getFrames(passiveSheet, 0, 8, 15);
        SHEEP_UP = getFrames(passiveSheet, 1, 8, 11);
        SHEEP_DOWN = getFrames(passiveSheet, 1, 12, 15);
        SHEEP_LEFT = getFrames(passiveSheet, 2, 12, 15);
        SHEEP_RIGHT = getFrames(passiveSheet, 2, 8, 11);

        /* Nature / Statics */
        TREE_SMALL = getSpriteInd(natureSheet, 0, 0, width, height * 2);
        TREE_MEDIUM = getSpriteInd(natureSheet, 0, 2, width * 2, height * 2);
        TREE_LARGE = getSpriteInd(natureSheet, 0, 4, width * 2, height * 2);
        ROCK_V1 = getSpriteInd(natureSheet, 1, 0, width, height);
        ROCK_V2 = getSpriteInd(natureSheet, 1, 1, width, height);
        BUSH_SMALL = getSpriteInd(natureSheet, 2, 0, width, height);
        BUSH_LARGE = getSpriteInd(natureSheet, 2, 1, width * 2, height);

        CROP_GROUND = getSpriteInd(natureSheet, 6, 0, width, height);
        CROP_CARROT = getSpriteInd(natureSheet, 7, 0, width, height);
        CROP_WHEAT = getSpriteInd(natureSheet, 7, 1, width, height);
        CROP_POTATO = getSpriteInd(natureSheet, 7, 2, width, height);
        CROP_TOMATO = getSpriteInd(natureSheet, 7, 3, width, height);
        CROP_CORN = getSpriteInd(natureSheet, 7, 4, width, height);

        int shopRoofLength = 7;
        SHOP_STALL = getSpriteInd(staticSheet, 0, 4, width * 2, height * 2);
        SHOP_ROOF_ORANGE = getFrames(staticSheet, 0, 0, shopRoofLength, width * 2, height);
        SHOP_ROOF_BLUE = getFrames(staticSheet, 1, 0, shopRoofLength, width * 2, height);
        SHOP_ROOF_RED = getFrames(staticSheet, 2, 0, shopRoofLength, width * 2, height);
        SHOP_ROOF_GREY = getFrames(staticSheet, 3, 0, shopRoofLength, width * 2, height);

        CAMPFIRE = getFrames(staticSheet, 7, 0, 9, width, height);

        Logger.print("Assets [Entities] loaded!");
    }

    /* GUI */
    private static void initGui() {
        BACKGROUND = getSpriteExact("/assets/textures/gui/bg.png", 0, 0, 320, 320);
        TITLE = getSpriteInd(menuSheet, 3, 0, 80, 48);
        DEAD_OVERLAY = getSpriteExact("/assets/textures/gui/dead_overlay.png", 0, 0, 512, 512);

        BUTTON_ENABLED = getFrames(menuSheet, 0, 0, 2, width, height);
        BUTTON_HOVER = getFrames(menuSheet, 1, 0, 2, width, height);
        BUTTON_DISABLED = getFrames(menuSheet, 2, 0, 2, width, height);

        INVENTORY = getSpriteExact(invSheet, 0, 0, 57, 41);
        CAMPFIRE_INVENTORY = getSpriteExact(campfireInvSheet, 0, 0, 57, 44);

        SLOT = getSpriteExact(invSheet, 2, 22, 8, 8);
        SLOT_SELECTER = getSpriteExact(invSheet, 48, 48, width, height);
        HOTBAR = getSpriteExact(invSheet, 12, 54, 28, 10);
        HOTBAR_SELECTER = getSpriteExact(invSheet, 0, 52, 12, 12);

        ICON_ON = getSpriteInd(menuSheet, 0, 3, width, height);
        ICON_OFF = getSpriteInd(menuSheet, 1, 3, width, height);

        TOOLTIP_SHORT_SMALL = getSpriteInd(menuSheet, 0, 4, width * 2, height);
        TOOLTIP_SHORT_LARGE = getSpriteInd(menuSheet, 0, 4, width * 2, height * 2);
        TOOLTIP_LONG_SMALL = getSpriteInd(menuSheet, 2, 4, width * 3, height);
        TOOLTIP_LONG_LARGE = getSpriteInd(menuSheet, 2, 4, width * 3, height * 2);

        LOGO = getSpriteExact("/assets/textures/logo.png", 0, 0, 128, 128);

        SLIDER_BUTTON = new BufferedImage[2];
        SLIDER_BUTTON[0] = getSpriteExact(menuSheet, 0, 120, 4, 8);
        SLIDER_BUTTON[1] = getSpriteExact(menuSheet, 4, 120, 4, 8);
        SLIDER_BAR = getSpriteExact(menuSheet, 0, 112, 48, 6);

        CHECK_BOX_BG = getSpriteInd(menuSheet, 2, 3, width, height);
        CHECK_BOX_FG = getSpriteInd(menuSheet, 3, 3, width, height);

        // Level Creator
        MG_OVERLAY_OUTER = getSpriteExact("/assets/textures/gui/level_creator/mg_overlay_outer.png", 0, 0, 700, 700);
        MG_OVERLAY_INNER_MID = getSpriteExact("/assets/textures/gui/level_creator/mg_overlay_inner_mid.png", 0, 0, 700, 700);
        MG_OVERLAY_INNER_MID_RIGHT = getSpriteExact("/assets/textures/gui/level_creator/mg_overlay_inner_mid_right.png", 0, 0, 700, 700);

        EDIT_GRID_TILE = getSpriteExact("/assets/textures/gui/level_creator/edit_grid_tile.png", 0, 0, 32, 32);
        SELECTED_TILE = getSpriteExact("/assets/textures/gui/level_creator/selected_tile.png", 0, 0, 64, 64);

        Logger.print("Assets [GUI] loaded!");
    }

    public static void init() {
        /* Sprite Sheets */
        terrainSheet = getSheet("/assets/textures/tiles/terrain.png");
        natureSheet = getSheet("/assets/textures/entities/nature.png");
        staticSheet = getSheet("/assets/textures/entities/static.png");
        effectSheet = getSheet("/assets/textures/effect.png");

        healthSheet = getSheet("/assets/textures/gui/overlay/health.png");
        glubSheet = getSheet("/assets/textures/glub.png");
        itemsSheet = getSheet("/assets/textures/items.png");

        playerSheet = getSheet("/assets/textures/entities/player.png");
        undeadSheet = getSheet("/assets/textures/entities/undead.png");
        passiveSheet = getSheet("/assets/textures/entities/passive.png");

        menuSheet = getSheet("/assets/textures/gui/menu.png");
        invSheet = getSheet("/assets/textures/gui/inventory/inventory.png");
        campfireInvSheet = getSheet("/assets/textures/gui/inventory/campfire.png");

        initFonts();
        initTiles();
        initItems();
        initEntities();
        initGui();

        Logger.print("Assets loaded!");
    }

    public static BufferedImage[] getFrames(String sheet, int xStart, int xEnd) {
        return getFrames(sheet, 0, xStart, xEnd);
    }

    public static BufferedImage[] getFrames(String sheet, int y, int xStart, int xEnd) {
        return getFrames(getSheet(sheet), y, xStart, xEnd, width, height);
    }

    public static BufferedImage[] getFrames(String sheet, int y, int xStart, int xEnd, int width, int height) {
        return getFrames(getSheet(sheet), y, xStart, xEnd, width, height);
    }

    public static BufferedImage[] getFrames(SpriteSheet sheet, int xStart, int xEnd) {
        return getFrames(sheet, 0, xStart, xEnd);
    }

    public static BufferedImage[] getFrames(SpriteSheet sheet, int y, int xStart, int xEnd) {
        return getFrames(sheet, y, xStart, xEnd, width, height);
    }

    public static BufferedImage[] getFrames(SpriteSheet sheet, int y, int xStart, int xEnd, int width, int height) {
        BufferedImage[] frames = new BufferedImage[(xEnd - xStart) + 1];
        int index = 0;
        for (int x = xStart; x < xEnd + 1; x++) {
            try {
                frames[index] = sheet.crop(x * width, y * height, width, height);
            } catch (RasterFormatException e) {
                e.printStackTrace();
                frames[index] = MISSING_TEXTURE;
            }
            index++;
        }
        return frames;
    }

    public static BufferedImage getSpriteExact(String sheet, int indexX, int indexY, int width, int height) {
        return getSpriteExact(getSheet(sheet), indexX, indexY, width, height);
    }

    public static BufferedImage getSpriteInd(SpriteSheet sheet, int indexX, int indexY, int width, int height) {
        return getSpriteExact(sheet, Assets.width * indexX, Assets.height * indexY, width, height);
    }

    public static BufferedImage getSpriteExact(SpriteSheet sheet, int indexX, int indexY, int width, int height) {
        BufferedImage image = MISSING_TEXTURE;
        try {
            image = sheet.crop(indexX, indexY, width, height); // Assets.width * indexX, Assets.height * indexY <-- This caused so many problems!
        } catch (RasterFormatException e) {
            e.printStackTrace();
        }
        return image;
    }

    public static SpriteSheet getSheet(String path) {
        return new SpriteSheet(ImageLoader.loadImage(path));
    }
}
