package io.github.vampirestudios.tdg.gfx.assets;

import coffeecatteam.coffeecatutils.logger.CatLogger;
import io.github.vampirestudios.tdg.gfx.image.SpriteSheet;
import io.github.vampirestudios.tdg.utils.Identifier;
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

    private static SpriteSheet glubSheet;

    /* GUI */
    private static SpriteSheet guiSheet;
    private static SpriteSheet invSheet;
    private static SpriteSheet campfireInvSheet;
    private static SpriteSheet cursorSheet;
    private static SpriteSheet iconSheet;

    /* Fonts */
    public static Map<String, Font> FONTS = new HashMap<>();

    /* Tiles */
    //TODO: Change these to json
    public static int GRASS_ALTS, DARK_GRASS_ALTS, SAND_ALTS, RED_SAND_ALTS, BROKEN_STONE_ALTS, WATER_ALTS, LAVA_ALTS, DIRT_ALTS;
    public static Image[] GRASS, DARK_GRASS, SAND, RED_SAND, BROKEN_STONE, WATER, LAVA, DIRT;

    /* Special Tiles */
    public static Image[] SPLASH_EFFECT, SPRINT_EFFECT;
    public static Image[] TILE_CRACKING;

    /* GUI */
    public static Image[] HEARTS, SPRINT, HEALTH_BAR;

    public static Image[] GLUB_ORB;
    public static Image[] GLUB_METER;

    public static Image GUI_TITLE_SMALL, GUI_TITLE_BIG;
    public static Image[] GUI_SPLASH_PLAYER, GUI_TITLE_FG;
    public static Image GUI_DEAD_OVERLAY;

    public static Image[] GUI_BUTTON_ENABLED = new Image[3];
    public static Image[] GUI_BUTTON_HOVER = new Image[3];
    public static Image[] GUI_BUTTON_DISABLED = new Image[3];

    public static Image GUI_INVENTORY, GUI_CAMPFIRE_INVENTORY;
    public static Image GUI_SLOT, GUI_SLOT_SELECTER;
    public static Image GUI_HOTBAR, GUI_HOTBAR_SELECTER;

    public static Image GUI_ICON_ON, GUI_ICON_OFF;
    public static Image[] GUI_TEXTBOX = new Image[9];

    public static Image[] GUI_SLIDER_BUTTON;
    public static Image GUI_SLIDER_BAR;

    public static Image GUI_CHECK_BOX_BG, GUI_CHECK_BOX_TICK, GUI_CHECK_BOX_CROSS;

    public static Image GUI_CURSOR, MAP_BORDER;
    public static Image[] MAP_CURSOR;

    public static Image[] GUI_ARROW_ICONS = new Image[4];
    public static Image[] GUI_LIST_GRAY = new Image[5];
    public static Image[] GUI_LIST_BLUE = new Image[5];

    public static Image GUI_SETTINGS_ICON;

    /* Fonts */
    private static void initFonts() {
        Identifier fontPath = new Identifier("maewil:fonts/lcd_solid.ttf");
        int sizeStep = 5, maxSize = 100;

        /* PLAIN */
        for (int i = sizeStep; i <= maxSize; i += sizeStep) {
            String pIndex = String.valueOf(i);
            FONTS.put(pIndex, loadTrueTypeFont(fontPath, i, java.awt.Font.PLAIN));
            logger.info("Font [" + pIndex + "] loaded!");
        }
        /* BOLD */
        for (int i = sizeStep; i <= maxSize; i += sizeStep) {
            String bIndex = i + "-bold";
            FONTS.put(bIndex, loadTrueTypeFont(fontPath, i, java.awt.Font.BOLD));
            logger.info("Font [" + bIndex + "] loaded!");
        }
        /* ITALIC */
        for (int i = sizeStep; i <= maxSize; i += sizeStep) {
            String iIndex = i + "-italic";
            FONTS.put(iIndex, loadTrueTypeFont(fontPath, i, java.awt.Font.ITALIC));
            logger.info("Font [" + iIndex + "] loaded!");
        }

        logger.info("Assets [Fonts] loaded!");
    }

    private static Font loadTrueTypeFont(Identifier path, float size, final int style) {
        try {
            java.awt.Font awtFont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, Assets.class.getResourceAsStream(path.toAssetsString())).deriveFont(style, size);

            UnicodeFont font = new UnicodeFont(awtFont);
            font.addAsciiGlyphs();
            font.getEffects().add(new ColorEffect(Color.white));
            font.addAsciiGlyphs();
            font.loadGlyphs();

            return font;
        } catch (IOException | FontFormatException | SlickException e) {
            e.printStackTrace();
            return null;
        }
    }

    // TODO: CHANGE TO JSON SYSTEM
    // TODO: REMOVE METHOD
    private static void initTiles() {
        // ALTS
        GRASS_ALTS = 11;
        GRASS = TileTextureAlts.getTextureAlts(GRASS_ALTS, 47, "grass", width, height);
        DARK_GRASS_ALTS = 7;
        DARK_GRASS = TileTextureAlts.getTextureAlts(DARK_GRASS_ALTS, 47, "dark_grass", width, height);
        SAND_ALTS = 20;
        SAND = TileTextureAlts.getTextureAlts(SAND_ALTS, 47, "sand", width, height);
        BROKEN_STONE_ALTS = 2;
        BROKEN_STONE = TileTextureAlts.getTextureAlts(BROKEN_STONE_ALTS, 47, "broken_stone", width, height);
        WATER_ALTS = 9;
        WATER = TileTextureAlts.getTextureAlts(WATER_ALTS, 47, "water", width, height);
        LAVA_ALTS = 16;
        LAVA = TileTextureAlts.getTextureAlts(LAVA_ALTS, 47, "lava", width, height);
        DIRT_ALTS = 16;
        DIRT = TileTextureAlts.getTextureAlts(DIRT_ALTS, 47, "dirt", width, height);

        // Special
        TILE_CRACKING = getTileFrames("tile_cracking", 9);

        SPLASH_EFFECT = getFrames(effectSheet, 0, 0, 15);
        SPRINT_EFFECT = getFrames(effectSheet, 1, 0, 15);

        logger.info("Assets [Tiles] loaded!");
    }

    /* Entities */
    private static void initEntities() {
        HEARTS = getFrames(new Identifier("maewil:textures/gui/overlay/player_health.png"), 0, 0, 24);
        SPRINT = getFrames(new Identifier("maewil:textures/gui/overlay/player_stats.png"), 1, 0, 1, width * 2, height);
        HEALTH_BAR = new Image[2];
        SpriteSheet hb = new SpriteSheet(new Identifier("maewil:textures/gui/overlay/health_bar.png"));
        HEALTH_BAR[0] = hb.crop(0, 0, hb.getSheet().getWidth(), hb.getSheet().getHeight() / 2);
        HEALTH_BAR[1] = hb.crop(0, hb.getSheet().getHeight() / 2, hb.getSheet().getWidth(), hb.getSheet().getHeight() / 2);

        GLUB_ORB = getFrames(glubSheet, 0, 0, 5, width, height);
        GLUB_METER = getFrames(glubSheet, 1, 0, 1, width * 4, height);

        logger.info("Assets [Entities] loaded!");
    }

    /* GUI */
    private static void initGui() {
        GUI_TITLE_SMALL = getSpriteExact(new Identifier("maewil:textures/splash/cover.png"), 0, 0, 3500, 1440);
        GUI_SPLASH_PLAYER = getFrames(new Identifier("maewil:textures/splash/player.png"), 0, 0, 11, 512, 512);

        GUI_TITLE_BIG = getSpriteExact(new Identifier("maewil:textures/splash/cover.png"), 0, 0, 101, 84);
        GUI_TITLE_FG = new Image[3];
        for (int i = 0; i < GUI_TITLE_FG.length; i++) {
            GUI_TITLE_FG[i] = getSpriteExact(new Identifier("maewil:textures/gui/title.png"), 112, 64 * i, 112, 64);
        }
        GUI_DEAD_OVERLAY = getImage(new Identifier("maewil:textures/gui/dead_overlay.png"));

        GUI_BUTTON_ENABLED = getFrames(guiSheet, 0, 0, 2, width, height);
        GUI_BUTTON_HOVER = getFrames(guiSheet, 1, 0, 2, width, height);
        GUI_BUTTON_DISABLED = getFrames(guiSheet, 2, 0, 2, width, height);

        GUI_INVENTORY = getSpriteExact(invSheet, 0, 0, 109, 80);
        GUI_CAMPFIRE_INVENTORY = getSpriteExact(campfireInvSheet, 0, 0, 57, 44);

        GUI_SLOT = getSpriteExact(invSheet, 2, 22, 8, 8);
        GUI_SLOT_SELECTER = getSpriteExact(invSheet, 98, 107, 20, 21);
        GUI_HOTBAR = getSpriteExact(invSheet, 13, 118, 82, 10);
        GUI_HOTBAR_SELECTER = getSpriteExact(invSheet, 0, 116, 12, 12);

        GUI_ICON_ON = getSpriteInd(guiSheet, 0, 3, width, height);
        GUI_ICON_OFF = getSpriteInd(guiSheet, 1, 3, width, height);

        GUI_TEXTBOX[0] = getSpriteInd(guiSheet, 5, 5, width, height);
        GUI_TEXTBOX[1] = getSpriteInd(guiSheet, 6, 5, width, height);
        GUI_TEXTBOX[2] = getSpriteInd(guiSheet, 7, 5, width, height);
        GUI_TEXTBOX[3] = getSpriteInd(guiSheet, 5, 6, width, height);
        GUI_TEXTBOX[4] = getSpriteInd(guiSheet, 6, 6, width, height);
        GUI_TEXTBOX[5] = getSpriteInd(guiSheet, 7, 6, width, height);
        GUI_TEXTBOX[6] = getSpriteInd(guiSheet, 5, 7, width, height);
        GUI_TEXTBOX[7] = getSpriteInd(guiSheet, 6, 7, width, height);
        GUI_TEXTBOX[8] = getSpriteInd(guiSheet, 7, 7, width, height);

        GUI_SLIDER_BUTTON = new Image[2];
        GUI_SLIDER_BUTTON[0] = getSpriteExact(guiSheet, 0, 120, 4, 8);
        GUI_SLIDER_BUTTON[1] = getSpriteExact(guiSheet, 4, 120, 4, 8);
        GUI_SLIDER_BAR = getSpriteExact(guiSheet, 0, 112, 48, 6);

        GUI_CHECK_BOX_BG = getSpriteInd(guiSheet, 2, 3, width, height);
        GUI_CHECK_BOX_TICK = getSpriteInd(guiSheet, 3, 3, width, height);
        GUI_CHECK_BOX_CROSS = getSpriteInd(guiSheet, 4, 3, width, height);

        GUI_CURSOR = getImage(new Identifier("maewil:textures/cursor.png"));
        MAP_BORDER = getImage(new Identifier("maewil:textures/gui/map/border.png"));
        MAP_CURSOR = new Image[8];
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 4; x++) {
                int index = x + (4 * y);
                MAP_CURSOR[index] = getSpriteInd(new SpriteSheet(new Identifier("maewil:textures/gui/map/cursor.png")), x, y, width, height);
            }
        }

        for (int x = 0; x < 4; x++)
            GUI_ARROW_ICONS[x] = getSpriteInd(guiSheet, 3 + x, 2, width, height);

        for (int x = 0; x < 5; x++)
            GUI_LIST_GRAY[x] = getSpriteInd(guiSheet, 3 + x, 0, width, height);
        for (int x = 0; x < 5; x++)
            GUI_LIST_BLUE[x] = getSpriteInd(guiSheet, 3 + x, 1, width, height);

        logger.info("Assets [GUI] loaded!");
    }

    public static void init() {
        logger = new CatLogger("Maewil-Assets");

        MISSING_TEXTURE = getImage("/assets/maewil/textures/missing.png");

        /* Sprite Sheets */
        effectSheet = new SpriteSheet(new Identifier("maewil:textures/effect.png"));

        glubSheet = new SpriteSheet(new Identifier("maewil:textures/glub.png"));

        /* GUI */
        guiSheet = new SpriteSheet(new Identifier("maewil:textures/gui/gui.png"));
        invSheet = new SpriteSheet(new Identifier("maewil:textures/gui/inventory/inventory.png"));
        campfireInvSheet = new SpriteSheet(new Identifier("maewil:textures/gui/inventory/campfire.png"));

        cursorSheet = new SpriteSheet(new Identifier("maewil:textures/cursors.png"));
        iconSheet = new SpriteSheet(new Identifier("maewil:textures/icons.png"));

        initFonts();
        initTiles();
        initEntities();
        initGui();

        logger.info("Assets loaded!");
    }

    public static Image getTileTexture(String tile, int xOff) {
        return getSpriteExact(new Identifier("maewil:textures/tiles/" + tile + ".png"), width * xOff, 0, width, height);
    }

    public static Image[] getTileFrames(String tile, int frameCount) {
        return getTileFrames(tile, frameCount, 1);
    }

    public static Image[] getTileFrames(String tile, int frameCount, int sizeMod) {
        return getFrames(new Identifier("maewil:textures/tiles/" + tile + ".png"), 0, 0, frameCount - 1, width * sizeMod, height * sizeMod);
    }

    public static Image[] getFrames(Identifier identifier, int xStart, int xEnd) {
        return getFrames(identifier, 0, xStart, xEnd);
    }

    public static Image[] getFrames(Identifier identifier, int y, int xStart, int xEnd) {
        return getFrames(new SpriteSheet(identifier), y, xStart, xEnd, width, height);
    }

    public static Image[] getFrames(Identifier identifier, int y, int xStart, int xEnd, int width, int height) {
        return getFrames(new SpriteSheet(identifier), y, xStart, xEnd, width, height);
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
                logger.error(e + " - " + sheet.getIdentifier().toAssetsString());
                frames[index] = MISSING_TEXTURE;
            }
            index++;
        }
        return frames;
    }

    public static Image getImage(Identifier identifier) {
        SpriteSheet sheet = new SpriteSheet(identifier);
        return getSpriteExact(sheet, 0, 0, sheet.getWidth(), sheet.getHeight());
    }

    public static Image getImage(String identifier) {
        SpriteSheet sheet = new SpriteSheet(identifier);
        return getSpriteExact(sheet, 0, 0, sheet.getWidth(), sheet.getHeight());
    }

    public static Image getSpriteExact(Identifier identifier, int indexX, int indexY, int width, int height) {
        return getSpriteExact(new SpriteSheet(identifier), indexX, indexY, width, height);
    }

    public static Image getSpriteInd(SpriteSheet sheet, int indexX, int indexY, int width, int height) {
        return getSpriteExact(sheet, Assets.width * indexX, Assets.height * indexY, width, height);
    }

    public static Image getSpriteExact(SpriteSheet sheet, int indexX, int indexY, int width, int height) {
        Image image = MISSING_TEXTURE;
        try {
            image = sheet.crop(indexX, indexY, width, height); /* Assets.WIDTH * indexX, Assets.HEIGHT * indexY <-- This caused so many problems! */
        } catch (RasterFormatException e) {
            logger.error(e + " - " + sheet.getIdentifier().toAssetsString() + " X: [" + indexX + "] Y: [" + indexY + "]");
        }
        return image;
    }
}
