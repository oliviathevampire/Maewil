package coffeecatteam.theultimatetile.levelcreator.grid;

import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.levelcreator.CreatorEngine;

import java.awt.*;

public abstract class Grid {

    protected CreatorEngine creatorEngine;
    protected Vector2D position;
    protected int ogX, ogY;
    protected int xWorldSize, yWorldSize, gridSize;
    protected int xOff = 0, yOff = 0;

    public Grid(CreatorEngine creatorEngine, int ogX, int ogY, int gridSize, Vector2D position, int worldSize) {
        this(creatorEngine, ogX, ogY, gridSize, position, worldSize, worldSize);
    }

    public Grid(CreatorEngine creatorEngine, int ogX, int ogY, int gridSize, Vector2D position, int xWorldSize, int yWorldSize) {
        this.creatorEngine = creatorEngine;
        this.position = position;
        this.xWorldSize = xWorldSize;
        this.yWorldSize = yWorldSize;
        this.ogX = ogX;
        this.ogY = ogY;
        this.gridSize = gridSize;
    }

    public abstract void tick();

    public void render(Graphics2D g) {
        for (int xs = 0; xs < xWorldSize; xs++) {
            for (int ys = 0; ys < yWorldSize; ys++) {
                int w = (ogX * 2) / gridSize, h = (ogY * 2) / gridSize;
                g.drawImage(Assets.EDIT_GRID_TILE, ((int) position.x + xOff) + w * xs, ((int) position.y + yOff) + h * ys, w, h, null);
            }
        }
    }

    public void setxOff(int xOff) {
        this.xOff = xOff;
    }

    public void setyOff(int yOff) {
        this.yOff = yOff;
    }

    public int getGridSize() {
        return gridSize;
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    public void setSize(int xWorldSize, int yWorldSize) {
        this.xWorldSize = xWorldSize;
        this.yWorldSize = yWorldSize;
    }
}
