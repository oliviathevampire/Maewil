package coffeecatteam.theultimatetile.gfx;

import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.objs.tiles.Tile;

import static coffeecatteam.coffeecatutils.NumberUtils.lerp;

public class Camera {

    private TutEngine tutEngine;
    private float xOffset, yOffset;
    private float smoothness = 0.05f; // (0.05 - Super Smooth) (0.1 - Smooth) (1 - Sharp)

    public Camera(TutEngine tutEngine, float xOffset, float yOffset) {
        this.tutEngine = tutEngine;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public void checkBlankSpace() {
        if (xOffset < 0)
            xOffset = 0;
        else if (xOffset > tutEngine.getWorld().getWidth() * Tile.TILE_SIZE - tutEngine.getWidth())
            xOffset = tutEngine.getWorld().getWidth() * Tile.TILE_SIZE - tutEngine.getWidth();

        if (yOffset < 0)
            yOffset = 0;
        else if (yOffset > tutEngine.getWorld().getHeight() * Tile.TILE_SIZE - tutEngine.getHeight())
            yOffset = tutEngine.getWorld().getHeight() * Tile.TILE_SIZE - tutEngine.getHeight();
    }

    public void centerOnEntity(Entity e) {
        changeOffset(e.getX() - tutEngine.getWidth() / 2f + e.getWidth() / 2f, e.getY() - tutEngine.getHeight() / 2f + e.getHeight() / 2f);
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

    public float getxOffset() {
        return xOffset;
    }

    public void setxOffset(float xOffset) {
        this.xOffset = xOffset;
    }

    public float getyOffset() {
        return yOffset;
    }

    public void setyOffset(float yOffset) {
        this.yOffset = yOffset;
    }

    public void setSmoothness(float smoothness) {
        this.smoothness = smoothness;
    }
}
