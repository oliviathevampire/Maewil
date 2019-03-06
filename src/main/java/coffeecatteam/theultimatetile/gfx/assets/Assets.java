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
    public static Image[] TILE_CRACKING;

    /* GUI */
    public static Image[] HEARTS;
    public static Image[] SPRINT;

    public static Image[] GLUB_ORB;
    public static Image[] GLUB_METER;

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
            logger.info("Font [" + pIndex + "] loaded!");

            /* BOLD */
            String bIndex = String.valueOf(i) + "-bold";
            FONTS.put(bIndex, loadTrueTypeFont(fontPath, i, java.awt.Font.BOLD));
            logger.info("Font [" + bIndex + "] loaded!");

            /* ITALIC */
            String iIndex = String.valueOf(i) + "-italic";
            FONTS.put(iIndex, loadTrueTypeFont(fontPath, i, java.awt.Font.ITALIC));
            logger.info("Font [" + iIndex + "] loaded!");
        }

        logger.info("Assets [Fonts] loaded!");
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
        TILE_CRACKING = getTileFrames("tile_cracking", 9);

        SPLASH_EFFECT = getFrames(effectSheet, 0, 0, 15);
        SPRINT_EFFECT = getFrames(effectSheet, 1, 0, 15);

        logger.info("Assets [Tiles] loaded!");
    }

    /* Entities */
    private static void initEntities() {
        HEARTS = getFrames(healthSheet, 0, 0, 8);
        SPRINT = getFrames(healthSheet, 2, 0, 1, width * 2, height);

        GLUB_ORB = getFrames(glubSheet, 0, 0, 5, width, height);
        GLUB_METER = getFrames(glubSheet, 1, 0, 1, width * 4, height);

        logger.info("Assets [Entities] loaded!");
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

        logger.info("Assets [GUI] loaded!");
    }

    public static void init() {
        logger = new CatLogger("TUT-Assets");

        MISSING_TEXTURE = ImageLoader.loadImage("/assets/textures/missing.png");

        /* Sprite Sheets */
        effectSheet = new SpriteSheet("/assets/textures/effect.png");

        healthSheet = new SpriteSheet("/assets/textures/gui/overlay/health.png");
        glubSheet = new SpriteSheet("/assets/textures/glub.png");
        itemsSheet = new SpriteSheet("/assets/textures/items.png");

        /* GUI */
        menuSheet = new SpriteSheet("/assets/textures/gui/menu.png");
        invSheet = new SpriteSheet("/assets/textures/gui/inventory/inventory.png");
        campfireInvSheet = new SpriteSheet("/assets/textures/gui/inventory/campfire.png");

        initFonts();
        initTiles();
        initEntities();
        initGui();

        logger.info("Assets loaded!");
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
                logger.error(e + " - " + sheet.getPath());
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
            logger.error(e + " - " + sheet.getPath() + " X: [" + indexX + "] Y: [" + indexY + "]");
        }
        return image;
    }
}
