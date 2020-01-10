package io.github.vampirestudios.tdg.gfx;

import coffeecatteam.coffeecatutils.position.Vector2D;
import io.github.vampirestudios.tdg.objs.entities.Entity;
import io.github.vampirestudios.tdg.objs.tiles.Tile;
import io.github.vampirestudios.tdg.start.TutEngine;
import io.github.vampirestudios.tdg.start.TutLauncher;

import static coffeecatteam.coffeecatutils.NumberUtils.lerp;

public class Camera {

    private TutEngine tutEngine;
    private float xOffset, yOffset;
    private float smoothness = 0.1f; // (0.05 - Super Smooth) (0.1 - Smooth) (1 - Sharp)

    public Camera(TutEngine tutEngine, float xOffset, float yOffset) {
        this.tutEngine = tutEngine;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public void checkBlankSpace() {
        if (xOffset < 0)
            xOffset = 0;
        else if (xOffset > tutEngine.getWorld().getWorldWidth() * Tile.TILE_SIZE - TutLauncher.WIDTH)
            xOffset = tutEngine.getWorld().getWorldWidth() * Tile.TILE_SIZE - TutLauncher.WIDTH;

        if (yOffset < 0)
            yOffset = 0;
        else if (yOffset > tutEngine.getWorld().getWorldHeight() * Tile.TILE_SIZE - TutLauncher.HEIGHT)
            yOffset = tutEngine.getWorld().getWorldHeight() * Tile.TILE_SIZE - TutLauncher.HEIGHT;
    }

    public void centerOnEntity(Entity e) {
        changeOffset(e.getX() - TutLauncher.WIDTH / 2f + e.getWidth() / 2f, e.getY() - TutLauncher.HEIGHT / 2f + e.getHeight() / 2f);
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
