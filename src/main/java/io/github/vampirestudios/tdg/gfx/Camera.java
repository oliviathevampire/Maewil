package io.github.vampirestudios.tdg.gfx;

import coffeecatteam.coffeecatutils.position.Vector2D;
import io.github.vampirestudios.tdg.objs.entities.Entity;
import io.github.vampirestudios.tdg.objs.tiles.Tile;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.start.MaewilLauncher;

import static coffeecatteam.coffeecatutils.NumberUtils.lerp;

public class Camera {

    private MaewilEngine maewilEngine;
    private float xOffset, yOffset;
    private float smoothness = 0.1f; // (0.05 - Super Smooth) (0.1 - Smooth) (1 - Sharp)

    public Camera(MaewilEngine maewilEngine, float xOffset, float yOffset) {
        this.maewilEngine = maewilEngine;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public void checkBlankSpace() {
        if (xOffset < 0)
            xOffset = 0;
        else if (xOffset > maewilEngine.getWorld().getWorldWidth() * Tile.TILE_SIZE - MaewilLauncher.WIDTH)
            xOffset = maewilEngine.getWorld().getWorldWidth() * Tile.TILE_SIZE - MaewilLauncher.WIDTH;

        if (yOffset < 0)
            yOffset = 0;
        else if (yOffset > maewilEngine.getWorld().getWorldHeight() * Tile.TILE_SIZE - MaewilLauncher.HEIGHT)
            yOffset = maewilEngine.getWorld().getWorldHeight() * Tile.TILE_SIZE - MaewilLauncher.HEIGHT;
    }

    public void centerOnEntity(Entity e) {
        changeOffset(e.getX() - MaewilLauncher.WIDTH / 2f + e.getWidth() / 2f, e.getY() - MaewilLauncher.HEIGHT / 2f + e.getHeight() / 2f);
        checkBlankSpace();
    }

    public void move(float xAmt, float yAmt) {
        changeOffset(xOffset + xAmt, yOffset + yAmt);
        checkBlankSpace();
    }

    private void changeOffset(float x, float y) {
        Vector2D offset = lerp(xOffset, yOffset, x, y, smoothness);
        xOffset = (float) offset.x;
        yOffset = (float) offset.y;
    }

    public float getXOffset() {
        return xOffset;
    }

    public void setXOffset(float xOffset) {
        this.xOffset = xOffset;
    }

    public float getYOffset() {
        return yOffset;
    }

    public void setYOffset(float yOffset) {
        this.yOffset = yOffset;
    }

    public void setSmoothness(float smoothness) {
        this.smoothness = smoothness;
    }
}
