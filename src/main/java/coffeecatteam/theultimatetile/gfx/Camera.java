package coffeecatteam.theultimatetile.gfx;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.entities.Entity;
import coffeecatteam.theultimatetile.tile.Tile;

public class Camera {

    private TutEngine tutEngine;
    private float xOffset, yOffset;

    public Camera(TutEngine tutEngine, float xOffset, float yOffset) {
        this.tutEngine = tutEngine;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public void checkBlankSpace() {
        if (xOffset < 0)
            xOffset = 0;
        else if (xOffset > tutEngine.getWorld().getWidth() * Tile.TILE_WIDTH - tutEngine.getWidth())
            xOffset = tutEngine.getWorld().getWidth() * Tile.TILE_WIDTH - tutEngine.getWidth();

        if (yOffset < 0)
            yOffset = 0;
        else if (yOffset > tutEngine.getWorld().getHeight() * Tile.TILE_HEIGHT - tutEngine.getHeight())
            yOffset = tutEngine.getWorld().getHeight() * Tile.TILE_HEIGHT - tutEngine.getHeight();
    }

    public void centerOnEntity(Entity e) {
        xOffset = e.getX() - tutEngine.getWidth() / 2f + e.getWidth() / 2f;
        yOffset = e.getY() - tutEngine.getHeight() / 2f + e.getHeight() / 2f;
        checkBlankSpace();
    }

    public void move(float xAmt, float yAmt) {
        xOffset += xAmt;
        yOffset += yAmt;
        checkBlankSpace();
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
}
