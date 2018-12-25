package coffeecatteam.theultimatetile.gfx.assets;

import coffeecatteam.coffeecatutils.io.FontLoader;
import coffeecatteam.coffeecatutils.logger.CatLogger;
import coffeecatteam.theultimatetile.gfx.image.ImageLoader;
import coffeecatteam.theultimatetile.gfx.image.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.util.HashMap;
import java.util.Map;

public class Assets {

    private static CatLogger logger;

    private static final int width = 16, height = 16;
    private static final BufferedImage MISSING_TEXTURE = ImageLoader.loadImage("/assets/textures/missing.png");

    /* Sprite Sheets */
    private static SpriteSheet terrainSheet, grassSheet, sandSheet;
    private static SpriteSheet effectSheet;

    private static SpriteSheet healthSheet;
    private static SpriteSheet glubSheet;
    private static SpriteSheet itemsSheet;

    /* Entities */
    private static SpriteSheet playerSheetIdle, playerSheetMoving, playerSheetDead;
    private static SpriteSheet zombieSheet, skeletonSheet, slimeSheet, thingSheet;
    private static SpriteSheet cowSheet, pigSheet, sheepSheet, foxSheet;

    private static SpriteSheet bushSheet, cropsSheet, rockSheet, treeSheet;
    private static SpriteSheet campfireSheet, extraLifeSheet, shopStallSheet;

    /* GUI */
    private static SpriteSheet menuSheet;
    private static SpriteSheet invSheet;
    private static SpriteSheet campfireInvSheet;

    /* Fonts */
    public static Map<String, Font> FONTS = new HashMap<>();

    /* Tiles */
    public static int GRASS_ALTS, SAND_ALTS;
    public static BufferedImage[] GRASS, SAND;
    public static BufferedImage DIRT;
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
    public static BufferedImage[] FOX_IDLE, FOX_UP, FOX_DOWN, FOX_LEFT, FOX_RIGHT;

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
    public static BufferedImage[] TEXTBOX = new BufferedImage[9];

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
        for (int i = 10; i <= 100; i += 10) {
            FONTS.put(String.valueOf(i), FontLoader.loadTrueTypeFont(fontPath, i));
            logger.print("Font size [" + i + "] loaded!");
        }

        logger.print("Assets [Fonts] loaded!");
    }

    /* Tiles */
    private static void initTiles() {
        // Grass
        GRASS_ALTS = 5;
        GRASS = TileTextureAlts.getTextureAlts(GRASS_ALTS, 47, grassSheet, width, height);
        SAND_ALTS = 3;
        SAND = TileTextureAlts.getTextureAlts(SAND_ALTS, 47, sandSheet, width, height);

        DIRT = getSpriteInd(terrainSheet, 1, 0, width, height);
        STONE = getSpriteInd(terrainSheet, 2, 0, width, height);
//        SAND = getSpriteInd(terrainSheet, 3, 0, width, height);
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

        logger.print("Assets [Tiles] loaded!");
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

        logger.print("Assets [Items] loaded!");
    }

    /* Entities */
    private static void initEntities() {
        HEARTS = getFrames(healthSheet, 0, 0, 4);
        ARMOR = getFrames(healthSheet, 1, 0, 3);
        SPRINT = getFrames(healthSheet, 3, 0, 1, width * 2, height);

        GLUB_ORB = getFrames(glubSheet, 0, 0, 5, width, height);
        GLUB_METER = getFrames(glubSheet, 1, 0, 1, width * 4, height);

        /* Player Frames */
        PLAYER_IDLE = getFrames(playerSheetIdle, 0, 0, 7);
        PLAYER_UP = getFrames(playerSheetMoving, 0, 0, 3);
        PLAYER_DOWN = getFrames(playerSheetMoving, 0, 4, 7);
        PLAYER_LEFT = getFrames(playerSheetMoving, 2, 0, 3);
        PLAYER_RIGHT = getFrames(playerSheetMoving, 1, 0, 7);
        PLAYER_DEAD = getFrames(playerSheetDead, 0, 0, 7);

        /* Entities */
        EXTRA_LIFE = getFrames(extraLifeSheet, 0, 7);

        // Undead
        ZOMBIE_IDLE = getFrames(zombieSheet, 0, 0, 3);
        ZOMBIE_UP = getFrames(zombieSheet, 1, 0, 2);
        ZOMBIE_DOWN = getFrames(zombieSheet, 1, 3, 5);
        ZOMBIE_LEFT = getFrames(zombieSheet, 2, 4, 7);
        ZOMBIE_RIGHT = getFrames(zombieSheet, 2, 0, 3);

        SKELETON_IDLE = getFrames(skeletonSheet, 0, 0, 3);
        SKELETON_UP = getFrames(skeletonSheet, 1, 0, 2);
        SKELETON_DOWN = getFrames(skeletonSheet, 1, 3, 5);
        SKELETON_LEFT = getFrames(skeletonSheet, 2, 4, 7);
        SKELETON_RIGHT = getFrames(skeletonSheet, 2, 0, 3);

        BOUNCER_IDLE = getFrames(slimeSheet, 0, 0, 7);
        BOUNCER_UP = getFrames(slimeSheet, 1, 0, 7);
        BOUNCER_DOWN = getFrames(slimeSheet, 2, 0, 7);
        BOUNCER_LEFT = getFrames(slimeSheet, 4, 0, 7);
        BOUNCER_RIGHT = getFrames(slimeSheet, 3, 0, 7);

        THING_IDLE = getFrames(thingSheet, 0, 0, 9);
        THING_UP = getFrames(thingSheet, 1, 0, 9);
        THING_DOWN = getFrames(thingSheet, 2, 0, 9);
        THING_LEFT = getFrames(thingSheet, 3, 0, 9);
        THING_RIGHT = getFrames(thingSheet, 4, 0, 9);

        // Passive
        PIG_IDLE = getFrames(pigSheet, 0, 0, 7);
        PIG_UP = getFrames(pigSheet, 1, 0, 3);
        PIG_DOWN = getFrames(pigSheet, 1, 4, 7);
        PIG_LEFT = getFrames(pigSheet, 2, 4, 7);
        PIG_RIGHT = getFrames(pigSheet, 2, 0, 3);

        COW_IDLE = getFrames(cowSheet, 0, 0, 7);
        COW_UP = getFrames(cowSheet, 1, 0, 3);
        COW_DOWN = getFrames(cowSheet, 1, 4, 7);
        COW_LEFT = getFrames(cowSheet, 2, 4, 7);
        COW_RIGHT = getFrames(cowSheet, 2, 0, 3);

        SHEEP_IDLE = getFrames(sheepSheet, 0, 0, 7);
        SHEEP_UP = getFrames(sheepSheet, 1, 0, 3);
        SHEEP_DOWN = getFrames(sheepSheet, 1, 4, 7);
        SHEEP_LEFT = getFrames(sheepSheet, 2, 4, 7);
        SHEEP_RIGHT = getFrames(sheepSheet, 2, 0, 3);

        FOX_IDLE = getFrames(foxSheet, 0, 0, 7);
        FOX_UP = getFrames(foxSheet, 1, 0, 3);
        FOX_DOWN = getFrames(foxSheet, 1, 4, 7);
        FOX_LEFT = getFrames(foxSheet, 2, 4, 7);
        FOX_RIGHT = getFrames(foxSheet, 2, 0, 3);

        /* Nature / Statics */
        TREE_SMALL = getSpriteInd(treeSheet, 0, 0, width, height * 2);
        TREE_MEDIUM = getSpriteInd(treeSheet, 1, 0, width * 2, height * 2);
        TREE_LARGE = getSpriteInd(treeSheet, 3, 0, width * 2, height * 2);

        ROCK_V1 = getSpriteInd(rockSheet, 0, 0, width, height);
        ROCK_V2 = getSpriteInd(rockSheet, 1, 0, width, height);

        BUSH_SMALL = getSpriteInd(bushSheet, 0, 0, width, height);
        BUSH_LARGE = getSpriteInd(bushSheet, 1, 0, width * 2, height);

        CROP_GROUND = getSpriteInd(cropsSheet, 0, 1, width, height);
        CROP_CARROT = getSpriteInd(cropsSheet, 0, 0, width, height);
        CROP_WHEAT = getSpriteInd(cropsSheet, 1, 0, width, height);
        CROP_POTATO = getSpriteInd(cropsSheet, 2, 0, width, height);
        CROP_TOMATO = getSpriteInd(cropsSheet, 3, 0, width, height);
        CROP_CORN = getSpriteInd(cropsSheet, 4, 0, width, height);

        int shopRoofLength = 7;
        SHOP_STALL = getSpriteInd(shopStallSheet, 0, 4, width * 2, height * 2);
        SHOP_ROOF_ORANGE = getFrames(shopStallSheet, 0, 0, shopRoofLength, width * 2, height);
        SHOP_ROOF_BLUE = getFrames(shopStallSheet, 1, 0, shopRoofLength, width * 2, height);
        SHOP_ROOF_RED = getFrames(shopStallSheet, 2, 0, shopRoofLength, width * 2, height);
        SHOP_ROOF_GREY = getFrames(shopStallSheet, 3, 0, shopRoofLength, width * 2, height);

        CAMPFIRE = getFrames(campfireSheet, 0, 0, 9, width, height);

        logger.print("Assets [Entities] loaded!");
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

        TEXTBOX[0] = getSpriteInd(menuSheet, 0, 4, width, height);
        TEXTBOX[1] = getSpriteInd(menuSheet, 1, 4, width, height);
        TEXTBOX[2] = getSpriteInd(menuSheet, 2, 4, width, height);
        TEXTBOX[3] = getSpriteInd(menuSheet, 0, 5, width, height);
        TEXTBOX[4] = getSpriteInd(menuSheet, 1, 5, width, height);
        TEXTBOX[5] = getSpriteInd(menuSheet, 2, 5, width, height);
        TEXTBOX[6] = getSpriteInd(menuSheet, 0, 6, width, height);
        TEXTBOX[7] = getSpriteInd(menuSheet, 1, 6, width, height);
        TEXTBOX[8] = getSpriteInd(menuSheet, 2, 6, width, height);

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

        logger.print("Assets [GUI] loaded!");
    }

    public static void init(CatLogger loggerIn) {
        logger = loggerIn;

        /* Sprite Sheets */
        terrainSheet = getSheet("/assets/textures/tiles/terrain.png");
        grassSheet = getSheet("/assets/textures/tiles/grass.png");
        sandSheet = getSheet("/assets/textures/tiles/sand.png");
        effectSheet = getSheet("/assets/textures/effect.png");

        healthSheet = getSheet("/assets/textures/gui/overlay/health.png");
        glubSheet = getSheet("/assets/textures/glub.png");
        itemsSheet = getSheet("/assets/textures/items.png");

        /* Entities */
        playerSheetIdle = getSheet("/assets/textures/entities/living/player/idle.png");
        playerSheetMoving = getSheet("/assets/textures/entities/living/player/moving.png");
        playerSheetDead = getSheet("/assets/textures/entities/living/player/dead.png");

        zombieSheet = getSheet("/assets/textures/entities/living/undead/zombie.png");
        skeletonSheet = getSheet("/assets/textures/entities/living/undead/skeleton.png");
        slimeSheet = getSheet("/assets/textures/entities/living/undead/slime.png");
        thingSheet = getSheet("/assets/textures/entities/living/undead/thing.png");

        cowSheet = getSheet("/assets/textures/entities/living/cow.png");
        pigSheet = getSheet("/assets/textures/entities/living/pig.png");
        sheepSheet = getSheet("/assets/textures/entities/living/sheep.png");
        foxSheet = getSheet("/assets/textures/entities/living/fox.png");

        bushSheet = getSheet("/assets/textures/entities/static/nature/bush.png");
        cropsSheet = getSheet("/assets/textures/entities/static/nature/crops.png");
        rockSheet = getSheet("/assets/textures/entities/static/nature/rock.png");
        treeSheet = getSheet("/assets/textures/entities/static/nature/tree.png");

        campfireSheet = getSheet("/assets/textures/entities/static/campfire.png");
        extraLifeSheet = getSheet("/assets/textures/entities/static/extra_life.png");
        shopStallSheet = getSheet("/assets/textures/entities/static/shop_stall.png");

        /* GUI */
        menuSheet = getSheet("/assets/textures/gui/menu.png");
        invSheet = getSheet("/assets/textures/gui/inventory/inventory.png");
        campfireInvSheet = getSheet("/assets/textures/gui/inventory/campfire.png");

        initFonts();
        initTiles();
        initItems();
        initEntities();
        initGui();

        logger.print("Assets loaded!");
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
                logger.print(e + " - " + sheet.getPath());
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
            logger.print(e + " - " + sheet.getPath());
        }
        return image;
    }

    public static SpriteSheet getSheet(String path) {
        return new SpriteSheet(path);
    }
}
