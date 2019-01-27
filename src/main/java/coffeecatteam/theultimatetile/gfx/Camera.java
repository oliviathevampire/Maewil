package coffeecatteam.theultimatetile.gfx;

import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.entities.Entity;
import coffeecatteam.theultimatetile.game.tile.Tile;

public class Camera {

    private GameEngine gameEngine;
    private float xOffset, yOffset;

    public Camera(GameEngine gameEngine, float xOffset, float yOffset) {
        this.gameEngine = gameEngine;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public void checkBlankSpace() {
        if (xOffset < 0)
            xOffset = 0;
        else if (xOffset > gameEngine.getWorld().getWidth() * Tile.TILE_WIDTH - gameEngine.getWidth())
            xOffset = gameEngine.getWorld().getWidth() * Tile.TILE_WIDTH - gameEngine.getWidth();

        if (yOffset < 0)
            yOffset = 0;
        else if (yOffset > gameEngine.getWorld().getHeight() * Tile.TILE_HEIGHT - gameEngine.getHeight())
            yOffset = gameEngine.getWorld().getHeight() * Tile.TILE_HEIGHT - gameEngine.getHeight();
    }

    public void centerOnEntity(Entity e) {
        xOffset = e.getX() - gameEngine.getWidth() / 2f + e.getWidth() / 2f;
        yOffset = e.getY() - gameEngine.getHeight() / 2f + e.getHeight() / 2f;
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
