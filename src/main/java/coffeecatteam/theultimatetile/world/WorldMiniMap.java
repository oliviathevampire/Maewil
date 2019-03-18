package coffeecatteam.theultimatetile.world;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.objs.entities.creatures.EntityPlayer;
import coffeecatteam.theultimatetile.objs.tiles.Tile;
import coffeecatteam.theultimatetile.world.colormap.WorldMapGenerator;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.util.BufferedImageUtil;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author CoffeeCatRailway
 * Created: 13/02/2019
 */
public class WorldMiniMap {

    private static Image mapCursor = Assets.MAP_CURSOR[0];
    private static final Color halfTransparent = new Color(1.0f, 1.0f, 1.0f, 0.5f);

    public static void render(Graphics g, TutEngine tutEngine, int width, int height, Color tint) {
        float padding = 10;
        float x = padding, y = padding, mapSize = 180;
        g.setColor(Color.white);

        int viewSize = 100;
        BufferedImage mapbg = new BufferedImage(viewSize, viewSize, BufferedImage.TYPE_INT_ARGB); /* BG */
        BufferedImage mapfg = new BufferedImage(viewSize, viewSize, BufferedImage.TYPE_INT_ARGB); /* FG */
        int pwx = (int) (tutEngine.getPlayer().getPosition().x / Tile.TILE_SIZE);
        int pwy = (int) (tutEngine.getPlayer().getPosition().y / Tile.TILE_SIZE);

        /*
         * Get world viewing coords
         */
        int mxStart = pwx - viewSize / 2;
        int mxEnd = pwx + viewSize / 2;
        int myStart = pwy - viewSize / 2;
        int myEnd = pwy + viewSize / 2;

        if (mxStart < 0) {
            mxStart = 0;
            mxEnd = viewSize;
        }
        if (mxEnd > width - 1) {
            mxStart = width - 1 - viewSize;
            mxEnd = width - 1;
        }
        if (myStart < 0) {
            myStart = 0;
            myEnd = viewSize;
        }
        if (myEnd > height - 1) {
            myStart = height - 1 - viewSize;
            myEnd = height - 1;
        }

        /*
         * Generate map image
         */
        for (int my = myStart; my < myEnd; my++) {
            for (int mx = mxStart; mx < mxEnd; mx++) {
                /* BG */
                int cbg = WorldMapGenerator.getRGBA(tutEngine.getWorld().getBGTile(mx, my).getMapColor().getRGB());
                int pixelXbg = (int) NumberUtils.map(mx, mxStart, mxEnd, 0, viewSize - 1);
                int pixelYbg = (int) NumberUtils.map(my, myStart, myEnd, 0, viewSize - 1);
                mapbg.setRGB(pixelXbg, pixelYbg, cbg);

                /* FG */
                int cfg = WorldMapGenerator.getRGBA(tutEngine.getWorld().getFGTile(mx, my).getMapColor().getRGB());
                int pixelXfg = (int) NumberUtils.map(mx, mxStart, mxEnd, 0, viewSize - 1);
                int pixelYfg = (int) NumberUtils.map(my, myStart, myEnd, 0, viewSize - 1);
                mapfg.setRGB(pixelXfg, pixelYfg, cfg);
            }
        }

        int mapSizeOff = 14;
        float mapBorderSize = mapSize + padding;
        EntityPlayer player = tutEngine.getPlayer();
        Color trans = ((player.getPosition().x + player.getWidth() / 2f < mapBorderSize && player.getPosition().y + player.getHeight() / 2f < mapBorderSize) ? halfTransparent : Color.white);
        try {
            /* BG */
            Image mapDrawbg = new Image(BufferedImageUtil.getTexture("mapbg", mapbg));
            mapDrawbg.setFilter(Image.FILTER_NEAREST);
            mapDrawbg.draw(x + mapSizeOff / 2f, y + mapSizeOff / 2f, mapSize - mapSizeOff + 2, mapSize - mapSizeOff + 2, trans);

            tint.a = trans.a;
            g.setColor(tint);
            g.fillRect(x + mapSizeOff / 2f, y + mapSizeOff / 2f, mapSize - mapSizeOff + 2, mapSize - mapSizeOff + 2);

            /* FG */
            Image mapDrawfg = new Image(BufferedImageUtil.getTexture("mapfg", mapfg));
            mapDrawfg.setFilter(Image.FILTER_NEAREST);
            mapDrawfg.draw(x + mapSizeOff / 2f, y + mapSizeOff / 2f, mapSize - mapSizeOff + 2, mapSize - mapSizeOff + 2, trans);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
         * Convert player world coords to map coords
         */
        float cursorSize = 20f;
        float pxd = x + mapSize / 2f - cursorSize / 2f;
        float pyd = y + mapSize / 2f - cursorSize / 2f;

        float px = pxd;
        float py = pyd;

        if (mxStart <= 0)
            px = NumberUtils.map((float) tutEngine.getPlayer().getPosition().x, 0, (viewSize + cursorSize) * Tile.TILE_SIZE, x, x + mapSize);
        if (mxEnd >= width - 1)
            px = NumberUtils.map((float) tutEngine.getPlayer().getPosition().x, (width - (viewSize - cursorSize / 2)) * Tile.TILE_SIZE, width * Tile.TILE_SIZE, x, x + mapSize - cursorSize / 2);
        if (myStart <= 0)
            py = NumberUtils.map((float) tutEngine.getPlayer().getPosition().y, 0, (viewSize + cursorSize) * Tile.TILE_SIZE, y, y + mapSize);
        if (myEnd >= height - 1)
            py = NumberUtils.map((float) tutEngine.getPlayer().getPosition().y, (height - (viewSize - cursorSize / 2)) * Tile.TILE_SIZE, height * Tile.TILE_SIZE, y, y + mapSize - cursorSize / 2);

        /*
         * Draw cursor (arrow) and border
         */
        updateMapCursor(tutEngine);
        mapCursor.draw(px, py, cursorSize, cursorSize);

        Assets.MAP_BORDER.draw(x - padding / 2, y - padding / 2, mapBorderSize, mapBorderSize, trans);
    }

    private static void updateMapCursor(TutEngine tutEngine) {
        if (tutEngine.getKeyManager().moveUp)
            mapCursor = Assets.MAP_CURSOR[0];
        if (tutEngine.getKeyManager().moveRight)
            mapCursor = Assets.MAP_CURSOR[1];
        if (tutEngine.getKeyManager().moveDown)
            mapCursor = Assets.MAP_CURSOR[2];
        if (tutEngine.getKeyManager().moveLeft)
            mapCursor = Assets.MAP_CURSOR[3];

        if (tutEngine.getKeyManager().moveUp && tutEngine.getKeyManager().moveRight)
            mapCursor = Assets.MAP_CURSOR[4];
        if (tutEngine.getKeyManager().moveRight && tutEngine.getKeyManager().moveDown)
            mapCursor = Assets.MAP_CURSOR[5];
        if (tutEngine.getKeyManager().moveDown && tutEngine.getKeyManager().moveLeft)
            mapCursor = Assets.MAP_CURSOR[6];
        if (tutEngine.getKeyManager().moveLeft && tutEngine.getKeyManager().moveUp)
            mapCursor = Assets.MAP_CURSOR[7];
    }
}
