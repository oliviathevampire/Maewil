package coffeecatteam.theultimatetile.gfx.assets;

import coffeecatteam.coffeecatutils.logger.CatLogger;
import coffeecatteam.theultimatetile.gfx.image.ImageLoader;
import coffeecatteam.theultimatetile.gfx.image.SpriteSheet;
import org.newdawn.slick.Font;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

import java.awt.*;
import java.awt.image.RasterFormatException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Assets {

    private static CatLogger logger;

    private static final int width = 16, height = 16;
    public static Image MISSING_TEXTURE;

    /* Sprite Sheets */
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

    /* Tiles */ //TODO: Change these to json
    public static int GRASS_ALTS, SAND_ALTS, BROKEN_STONE_ALTS;
    public static Image[] GRASS, SAND, BROKEN_STONE;

    /* Special Tiles */
    public static Image[] SPLASH_EFFECT, SPRINT_EFFECT;
    public static Image[] ULTIMATE_TILE, TILE_CRACKING;

    /* Items */
    public static Image ITEM_STICK, ITEM_LEAF, ITEM_ROCK;
    public static Image ITEM_ROTTEN_FLESH, ITEM_BONE, ITEM_BOUNCY_BALL;

    public static Image ITEM_WOODEN_SWORD, ITEM_WOODEN_PICK, ITEM_WOODEN_HOE;
    public static Image ITEM_STONE_SWORD, ITEM_STONE_PICK, ITEM_STONE_HOE;

    public static Image ITEM_COAL, ITEM_IRON_INGOT, ITEM_GOLD_INGOT, ITEM_DIAMOND;

    public static Image ITEM_CARROT, ITEM_APPLE, ITEM_RAW_PORK, ITEM_COOKED_PORK, ITEM_BREAD, ITEM_WHEAT, ITEM_RAW_STEAK, ITEM_COOKED_STEAK, ITEM_WOOL_BUNDLE, ITEM_POTATO, ITEM_TOMATO, ITEM_CORN;

    public static Image ITEM_COIN_PENNY, ITEM_COIN_IRON, ITEM_COIN_GOLD;

    /* Player */
    public static Image[] HEARTS;
    public static Image[] ARMOR;
    public static Image[] SPRINT;

    public static Image[] GLUB_ORB;
    public static Image[] GLUB_METER;

    /* Player Frames */
    public static Image[] PLAYER_IDLE, PLAYER_UP, PLAYER_DOWN, PLAYER_LEFT, PLAYER_RIGHT, PLAYER_DEAD;

    /* Entities */
    public static Image[] EXTRA_LIFE;

    // Undead
    public static Image[] ZOMBIE_IDLE, ZOMBIE_UP, ZOMBIE_DOWN, ZOMBIE_LEFT, ZOMBIE_RIGHT;
    public static Image[] SKELETON_IDLE, SKELETON_UP, SKELETON_DOWN, SKELETON_LEFT, SKELETON_RIGHT;
    public static Image[] BOUNCER_IDLE, BOUNCER_UP, BOUNCER_DOWN, BOUNCER_LEFT, BOUNCER_RIGHT;
    public static Image[] THING_IDLE, THING_UP, THING_DOWN, THING_LEFT, THING_RIGHT;

    // Passive
    public static Image[] PIG_IDLE, PIG_UP, PIG_DOWN, PIG_LEFT, PIG_RIGHT;
    public static Image[] COW_IDLE, COW_UP, COW_DOWN, COW_LEFT, COW_RIGHT;
    public static Image[] SHEEP_IDLE, SHEEP_UP, SHEEP_DOWN, SHEEP_LEFT, SHEEP_RIGHT;
    public static Image[] FOX_IDLE, FOX_UP, FOX_DOWN, FOX_LEFT, FOX_RIGHT;

    /* Nature / Statics */
    public static Image TREE_SMALL, TREE_MEDIUM, TREE_LARGE, TREE_EXTRA_LARGE;
    public static Image ROCK_V1, ROCK_V2;
    public static Image BUSH_SMALL, BUSH_LARGE;
    public static Image CROP_GROUND, CROP_CARROT, CROP_WHEAT, CROP_POTATO, CROP_TOMATO, CROP_CORN;

    public static Image SHOP_STALL;
    public static Image[] SHOP_ROOF_ORANGE, SHOP_ROOF_BLUE, SHOP_ROOF_RED, SHOP_ROOF_GREY;

    public static Image[] CAMPFIRE;

    /* GUI */
    public static Image TITLE;
    public static Image DEAD_OVERLAY;

    public static Image[] BUTTON_ENABLED = new Image[3];
    public static Image[] BUTTON_HOVER = new Image[3];
    public static Image[] BUTTON_DISABLED = new Image[3];

    public static Image INVENTORY, CAMPFIRE_INVENTORY;
    public static Image SLOT, SLOT_SELECTER;
    public static Image HOTBAR, HOTBAR_SELECTER;

    public static Image ICON_ON, ICON_OFF;
    public static Image[] TEXTBOX = new Image[9];

    public static Image[] SLIDER_BUTTON;
    public static Image SLIDER_BAR;

    public static Image CHECK_BOX_BG, CHECK_BOX_FG;

    public static Image CURSOR, MAP_BORDER;
    public static Image[] MAP_CURSOR;

    /* Fonts */
    private static void initFonts() {
        String fontPath = "/assets/fonts/LCD_Solid.ttf";
        for (int i = 5; i <= 100; i += 5) {
            /* PLAIN */
            String pIndex = String.valueOf(i);
            FONTS.put(pIndex, loadTrueTypeFont(fontPath, i, java.awt.Font.PLAIN));
            logger.print("Font [" + pIndex + "] loaded!");

            /* BOLD */
            String bIndex = String.valueOf(i) + "-bold";
            FONTS.put(bIndex, loadTrueTypeFont(fontPath, i, java.awt.Font.BOLD));
            logger.print("Font [" + bIndex + "] loaded!");

            /* ITALIC */
            String iIndex = String.valueOf(i) + "-italic";
            FONTS.put(iIndex, loadTrueTypeFont(fontPath, i, java.awt.Font.ITALIC));
            logger.print("Font [" + iIndex + "] loaded!");
        }

        logger.print("Assets [Fonts] loaded!");
    }

    private static Font loadTrueTypeFont(String path, float size, final int style) {
        try {
            java.awt.Font awtFont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, Assets.class.getResourceAsStream(path)).deriveFont(style, size);

            UnicodeFont font = new org.newdawn.slick.UnicodeFont(awtFont);
            font.addAsciiGlyphs();
            font.getEffects().add(new ColorEffect(java.awt.Color.white));
            font.addAsciiGlyphs();
            font.loadGlyphs();

            return font;
        } catch (IOException | FontFormatException | SlickException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    // TODO: CHANGE TO JSON SYSTEM
    // TODO: REMOVE METHOD
    private static void initTiles() {
        // ALTS
        GRASS_ALTS = 7;
        GRASS = TileTextureAlts.getTextureAlts(GRASS_ALTS, 47, "grass", width, height);
        SAND_ALTS = 4;
        SAND = TileTextureAlts.getTextureAlts(SAND_ALTS, 47, "sand", width, height);
        BROKEN_STONE_ALTS = 2;
        BROKEN_STONE = TileTextureAlts.getTextureAlts(BROKEN_STONE_ALTS, 47, "broken_stone", width, height);

        // Special
        ULTIMATE_TILE = getTileFrames("ultimate", 7, 2);

        TILE_CRACKING = getTileFrames("tile_cracking", 9);

        SPLASH_EFFECT = getFrames(effectSheet, 0, 0, 15);
        SPRINT_EFFECT = getFrames(effectSheet, 1, 0, 15);

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
        TREE_EXTRA_LARGE = getSpriteInd(treeSheet, 4, 0, width * 2, height * 4);

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

        SLIDER_BUTTON = new Image[2];
        SLIDER_BUTTON[0] = getSpriteExact(menuSheet, 0, 120, 4, 8);
        SLIDER_BUTTON[1] = getSpriteExact(menuSheet, 4, 120, 4, 8);
        SLIDER_BAR = getSpriteExact(menuSheet, 0, 112, 48, 6);

        CHECK_BOX_BG = getSpriteInd(menuSheet, 2, 3, width, height);
        CHECK_BOX_FG = getSpriteInd(menuSheet, 3, 3, width, height);

        CURSOR = getSpriteExact("/assets/textures/cursor.png", 0, 0, 32, 32);
        MAP_BORDER = getSpriteExact("/assets/textures/gui/map/border.png", 0, 0, 64, 64);
        MAP_CURSOR = new Image[8];
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 4; x++) {
                int index = x + (4 * y);
                MAP_CURSOR[index] = getSpriteInd(new SpriteSheet("/assets/textures/gui/map/cursor.png"), x, y, 16, 16);
            }
        }

        logger.print("Assets [GUI] loaded!");
    }

    public static void init() {
        logger = new CatLogger("TUT-Assets");

        MISSING_TEXTURE = ImageLoader.loadImage("/assets/textures/missing.png");

        /* Sprite Sheets */
        effectSheet = new SpriteSheet("/assets/textures/effect.png");

        healthSheet = new SpriteSheet("/assets/textures/gui/overlay/health.png");
        glubSheet = new SpriteSheet("/assets/textures/glub.png");
        itemsSheet = new SpriteSheet("/assets/textures/items.png");

        /* Entities */
        playerSheetIdle = new SpriteSheet("/assets/textures/entities/living/player/idle.png");
        playerSheetMoving = new SpriteSheet("/assets/textures/entities/living/player/moving.png");
        playerSheetDead = new SpriteSheet("/assets/textures/entities/living/player/dead.png");

        zombieSheet = new SpriteSheet("/assets/textures/entities/living/undead/zombie.png");
        skeletonSheet = new SpriteSheet("/assets/textures/entities/living/undead/skeleton.png");
        slimeSheet = new SpriteSheet("/assets/textures/entities/living/undead/slime.png");
        thingSheet = new SpriteSheet("/assets/textures/entities/living/undead/thing.png");

        cowSheet = new SpriteSheet("/assets/textures/entities/living/cow.png");
        pigSheet = new SpriteSheet("/assets/textures/entities/living/pig.png");
        sheepSheet = new SpriteSheet("/assets/textures/entities/living/sheep.png");
        foxSheet = new SpriteSheet("/assets/textures/entities/living/fox.png");

        bushSheet = new SpriteSheet("/assets/textures/entities/static/nature/bush.png");
        cropsSheet = new SpriteSheet("/assets/textures/entities/static/nature/crops.png");
        rockSheet = new SpriteSheet("/assets/textures/entities/static/nature/rock.png");
        treeSheet = new SpriteSheet("/assets/textures/entities/static/nature/tree.png");

        campfireSheet = new SpriteSheet("/assets/textures/entities/static/campfire.png");
        extraLifeSheet = new SpriteSheet("/assets/textures/entities/static/extra_life.png");
        shopStallSheet = new SpriteSheet("/assets/textures/entities/static/shop_stall.png");

        /* GUI */
        menuSheet = new SpriteSheet("/assets/textures/gui/menu.png");
        invSheet = new SpriteSheet("/assets/textures/gui/inventory/inventory.png");
        campfireInvSheet = new SpriteSheet("/assets/textures/gui/inventory/campfire.png");

        initFonts();
        initTiles();
        initItems();
        initEntities();
        initGui();

        logger.print("Assets loaded!");
    }

    public static Image getTileTexture(String tile) {
        return getTileTexture(tile, 0);
    }

    public static Image getTileTexture(String tile, int xOff) {
        return getSpriteExact("/assets/textures/tiles/" + tile + ".png", width * xOff, 0, width, height);
    }

    public static Image[] getTileFrames(String tile, int frameCount) {
        return getTileFrames(tile, frameCount, 1);
    }

    public static Image[] getTileFrames(String tile, int frameCount, int sizeMod) {
        return getFrames("/assets/textures/tiles/" + tile + ".png", 0, 0, frameCount - 1, width * sizeMod, height * sizeMod);
    }

    public static Image[] getFrames(String sheet, int xStart, int xEnd) {
        return getFrames(sheet, 0, xStart, xEnd);
    }

    public static Image[] getFrames(String sheet, int y, int xStart, int xEnd) {
        return getFrames(new SpriteSheet(sheet), y, xStart, xEnd, width, height);
    }

    public static Image[] getFrames(String sheet, int y, int xStart, int xEnd, int width, int height) {
        return getFrames(new SpriteSheet(sheet), y, xStart, xEnd, width, height);
    }

    public static Image[] getFrames(SpriteSheet sheet, int xStart, int xEnd) {
        return getFrames(sheet, 0, xStart, xEnd);
    }

    public static Image[] getFrames(SpriteSheet sheet, int y, int xStart, int xEnd) {
        return getFrames(sheet, y, xStart, xEnd, width, height);
    }

    public static Image[] getFrames(SpriteSheet sheet, int y, int xStart, int xEnd, int width, int height) {
        Image[] frames = new Image[(xEnd - xStart) + 1];
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

    public static Image getSpriteExact(String sheet, int indexX, int indexY, int width, int height) {
        return getSpriteExact(new SpriteSheet(sheet), indexX, indexY, width, height);
    }

    public static Image getSpriteInd(SpriteSheet sheet, int indexX, int indexY, int width, int height) {
        return getSpriteExact(sheet, Assets.width * indexX, Assets.height * indexY, width, height);
    }

    public static Image getSpriteExact(SpriteSheet sheet, int indexX, int indexY, int width, int height) {
        Image image = MISSING_TEXTURE;
        try {
            image = sheet.crop(indexX, indexY, width, height); /* Assets.width * indexX, Assets.height * indexY <-- This caused so many problems! */
        } catch (RasterFormatException e) {
            logger.print(e + " - " + sheet.getPath() + " X: [" + indexX + "] Y: [" + indexY + "]");
        }
        return image;
    }
}
