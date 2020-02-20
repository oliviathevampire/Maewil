package io.github.vampirestudios.tdg.world;

import coffeecatteam.coffeecatutils.NumberUtils;
import io.github.vampirestudios.tdg.gfx.assets.Assets;
import io.github.vampirestudios.tdg.objs.entities.creatures.PlayerEntity;
import io.github.vampirestudios.tdg.objs.tiles.Tile;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.world.colormap.WorldMapGenerator;
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

    public static void render(Graphics g, MaewilEngine maewilEngine, int width, int height, Color tint) {
        float padding = 10;
        float mapSize = 180;
        g.setColor(Color.white);

        int viewSize = 100;
        BufferedImage mapbg = new BufferedImage(viewSize, viewSize, BufferedImage.TYPE_INT_ARGB); /* BG */
        BufferedImage mapfg = new BufferedImage(viewSize, viewSize, BufferedImage.TYPE_INT_ARGB); /* FG */
        int pwx = (int) (maewilEngine.getPlayer().getPosition().x / Tile.TILE_SIZE);
        int pwy = (int) (maewilEngine.getPlayer().getPosition().y / Tile.TILE_SIZE);

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
                int cbg = WorldMapGenerator.getRGBA(maewilEngine.getWorld().getBackgroundTile(mx, my).getMapColor().getRGB());
                int pixelXbg = (int) NumberUtils.map(mx, mxStart, mxEnd, 0, viewSize - 1);
                int pixelYbg = (int) NumberUtils.map(my, myStart, myEnd, 0, viewSize - 1);
                mapbg.setRGB(pixelXbg, pixelYbg, cbg);

//                /* FG */
                int cfg = WorldMapGenerator.getRGBA(maewilEngine.getWorld().getForegroundTile(mx, my).getMapColor().getRGB());
                int pixelXfg = (int) NumberUtils.map(mx, mxStart, mxEnd, 0, viewSize - 1);
                int pixelYfg = (int) NumberUtils.map(my, myStart, myEnd, 0, viewSize - 1);
                mapfg.setRGB(pixelXfg, pixelYfg, cfg);
            }
        }

        int mapSizeOff = 14;
        float mapBorderSize = mapSize + padding;
        PlayerEntity player = maewilEngine.getPlayer();
        Color trans = ((player.getPosition().x + player.getWidth() / 2f < mapBorderSize && player.getPosition().y + player.getHeight() / 2f < mapBorderSize) ? halfTransparent : Color.white);
        try {
            /* BG */
            Image mapDrawbg = new Image(BufferedImageUtil.getTexture("mapbg", mapbg));
            mapDrawbg.setFilter(Image.FILTER_NEAREST);
            mapDrawbg.draw(padding + mapSizeOff / 2f, padding + mapSizeOff / 2f, mapSize - mapSizeOff + 2, mapSize - mapSizeOff + 2, trans);

            tint.a = trans.a;
            g.setColor(tint);
            g.fillRect(padding + mapSizeOff / 2f, padding + mapSizeOff / 2f, mapSize - mapSizeOff + 2, mapSize - mapSizeOff + 2);

            /* FG */
            Image mapDrawfg = new Image(BufferedImageUtil.getTexture("mapfg", mapfg));
            mapDrawfg.setFilter(Image.FILTER_NEAREST);
            mapDrawfg.draw(padding + mapSizeOff / 2f, padding + mapSizeOff / 2f, mapSize - mapSizeOff + 2, mapSize - mapSizeOff + 2, trans);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
         * Convert player world coords to map coords
         */
        float cursorSize = 20f;
        float pxd = padding + mapSize / 2f - cursorSize / 2f;
        float pyd = padding + mapSize / 2f - cursorSize / 2f;

        float px = pxd;
        float py = pyd;

        if (mxStart <= 0)
            px = NumberUtils.map((float) maewilEngine.getPlayer().getPosition().x, 0, (viewSize + cursorSize) * Tile.TILE_SIZE, padding, padding + mapSize);
        if (mxEnd >= width - 1)
            px = NumberUtils.map((float) maewilEngine.getPlayer().getPosition().x, (width - (viewSize - cursorSize / 2)) * Tile.TILE_SIZE, width * Tile.TILE_SIZE, padding, padding + mapSize - cursorSize / 2);
        if (myStart <= 0)
            py = NumberUtils.map((float) maewilEngine.getPlayer().getPosition().y, 0, (viewSize + cursorSize) * Tile.TILE_SIZE, padding, padding + mapSize);
        if (myEnd >= height - 1)
            py = NumberUtils.map((float) maewilEngine.getPlayer().getPosition().y, (height - (viewSize - cursorSize / 2)) * Tile.TILE_SIZE, height * Tile.TILE_SIZE, padding, padding + mapSize - cursorSize / 2);

        /*
         * Draw cursor (arrow) and border
         */
        updateMapCursor(maewilEngine);
        mapCursor.draw(px, py, cursorSize, cursorSize);

        Assets.MAP_BORDER.draw(padding - padding / 2, padding - padding / 2, mapBorderSize, mapBorderSize, trans);
    }

    private static void updateMapCursor(MaewilEngine maewilEngine) {
        if (maewilEngine.getKeyManager().moveUp)
            mapCursor = Assets.MAP_CURSOR[0];
        if (maewilEngine.getKeyManager().moveRight)
            mapCursor = Assets.MAP_CURSOR[1];
        if (maewilEngine.getKeyManager().moveDown)
            mapCursor = Assets.MAP_CURSOR[2];
        if (maewilEngine.getKeyManager().moveLeft)
            mapCursor = Assets.MAP_CURSOR[3];

        if (maewilEngine.getKeyManager().moveUp && maewilEngine.getKeyManager().moveRight)
            mapCursor = Assets.MAP_CURSOR[4];
        if (maewilEngine.getKeyManager().moveRight && maewilEngine.getKeyManager().moveDown)
            mapCursor = Assets.MAP_CURSOR[5];
        if (maewilEngine.getKeyManager().moveDown && maewilEngine.getKeyManager().moveLeft)
            mapCursor = Assets.MAP_CURSOR[6];
        if (maewilEngine.getKeyManager().moveLeft && maewilEngine.getKeyManager().moveUp)
            mapCursor = Assets.MAP_CURSOR[7];
    }
}
