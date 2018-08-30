package coffeecatteam.theultimatetile.gfx;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.entities.Entity;
import coffeecatteam.theultimatetile.tiles.Tile;

public class Camera {

    private TheUltimateTile theUltimateTile;
    private float xOffset, yOffset;

    public Camera(TheUltimateTile theUltimateTile, float xOffset, float yOffset) {
        this.theUltimateTile = theUltimateTile;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public void checkBlankSpace() {
        if (xOffset < 0)
            xOffset = 0;
        else if (xOffset > theUltimateTile.getWorld().getWidth() * Tile.TILE_WIDTH - theUltimateTile.getWidth())
            xOffset = theUltimateTile.getWorld().getWidth() * Tile.TILE_WIDTH - theUltimateTile.getWidth();

        if (yOffset < 0)
            yOffset = 0;
        else if (yOffset > theUltimateTile.getWorld().getHeight() * Tile.TILE_HEIGHT - theUltimateTile.getHeight())
            yOffset = theUltimateTile.getWorld().getHeight() * Tile.TILE_HEIGHT - theUltimateTile.getHeight();
    }

    public void centerOnEntity(Entity e) {
        xOffset = e.getX() - theUltimateTile.getWidth() / 2 + e.getWidth() / 2;
        yOffset = e.getY() - theUltimateTile.getHeight() / 2 + e.getHeight() / 2;
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
