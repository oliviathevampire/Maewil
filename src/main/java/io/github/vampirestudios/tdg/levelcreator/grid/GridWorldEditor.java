package io.github.vampirestudios.tdg.levelcreator.grid;

import coffeecatteam.coffeecatutils.position.AABB;
import coffeecatteam.theultimatetile.game.tiles.Tile;
import coffeecatteam.theultimatetile.game.tiles.Tiles;
import coffeecatteam.theultimatetile.levelcreator.CreatorEngine;
import coffeecatteam.theultimatetile.levelcreator.LevelRenderer;

import java.awt.*;

public class GridWorldEditor extends Grid {

    private GridTile[][] grid;
    private AABB gridBounds;
    private int mouseX, mouseY;
    private Tile SELECTED_TILE;

    private boolean beingUsed, showRendered = true;

    public GridWorldEditor(CreatorEngine creatorEngine, int ogX, int ogY, int gridSize, int x, int y, int xWorldSize, int yWorldSize, boolean beingUsed) {
        super(creatorEngine, ogX, ogY, gridSize, x, y, xWorldSize, yWorldSize);
        initGrid();

        this.beingUsed = beingUsed;
    }

    private void initGrid() {
        grid = new GridTile[xWorldSize][yWorldSize];

        int w = (ogX * 2) / gridSize, h = (ogY * 2) / gridSize;
        for (int gx = 0; gx < xWorldSize; gx++) {
            for (int gy = 0; gy < yWorldSize; gy++) {
                grid[gx][gy] = new GridTile(ogX + w * gx, ogY + h * gy, w, h);
            }
        }
    }

    public void updateVars(AABB gridBounds, int mouseX, int mouseY, Tile SELECTED_TILE) {
        this.gridBounds = gridBounds;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.SELECTED_TILE = SELECTED_TILE;
    }

    @Override
    public void tick() {
        int offAmt = 3;
        if (creatorEngine.getKeyManager().moveUp)
            updateYOffset(offAmt);
        if (creatorEngine.getKeyManager().moveDown)
            updateYOffset(-offAmt);
        if (creatorEngine.getKeyManager().moveLeft)
            updateXOffset(offAmt);
        if (creatorEngine.getKeyManager().moveRight)
            updateXOffset(-offAmt);
        updateSizePosCheck();

        int w = (ogX * 2) / gridSize, h = (ogY * 2) / gridSize;
        for (int x = 0; x < xWorldSize; x++) {
            for (int y = 0; y < yWorldSize; y++) {
                GridTile tile = grid[x][y];
                tile.setX(ogX + w * x);
                tile.setY(ogY + h * y);
                tile.setWidth(w);
                tile.setHeight(h);
            }
        }

        if (beingUsed) {
            for (int x = 0; x < xWorldSize; x++) {
                for (int y = 0; y < yWorldSize; y++) {
                    GridTile tile = grid[x][y];
                    tile.tick();

                    if (tile.getBounds().contains(mouseX, mouseY)) {
                        if (gridBounds.contains(mouseX, mouseY)) {
                            if (creatorEngine.getMouseManager().isLeftDown() || creatorEngine.getMouseManager().isLeftPressed()) {
                                if (tile.getTile() != SELECTED_TILE)
                                    tile.setTile(LevelRenderer.getSelectedTile());
                            } else if (creatorEngine.getMouseManager().isRightDown() || creatorEngine.getMouseManager().isRightPressed())
                                tile.setTile(Tiles.AIR);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        if (showRendered) {
            super.render(g);
            for (int x = 0; x < xWorldSize; x++) {
                for (int y = 0; y < yWorldSize; y++) {
                    GridTile tile = grid[x][y];

                    tile.render(g);
                }
            }
        }
    }

    private void updateSizePosCheck() {
        updateXOffset(0);
        updateYOffset(0);
    }

    private void updateXOffset(int amt) {
        xOff += amt;
        int maxX = 32;
        int w = (ogX * 2) / gridSize;
        int minX = -((w * xWorldSize) - creatorEngine.getWidth() / 2 + maxX);

        if (xOff > maxX)
            xOff = maxX;
        if (xOff < minX)
            xOff = minX;

        for (int x = 0; x < xWorldSize; x++) {
            for (int y = 0; y < yWorldSize; y++) {
                grid[x][y].setxOff(xOff);
            }
        }
    }

    private void updateYOffset(int amt) {
        yOff += amt;
        int maxY = 32;
        int h = (ogY * 2) / gridSize;
        int minY = -((h * yWorldSize) - creatorEngine.getHeight() / 2 + maxY);

        if (yOff > maxY)
            yOff = maxY;
        if (yOff < minY)
            yOff = minY;

        for (int x = 0; x < xWorldSize; x++) {
            for (int y = 0; y < yWorldSize; y++) {
                grid[x][y].setyOff(yOff);
            }
        }
    }

    public void setBeingUsed(boolean beingUsed) {
        this.beingUsed = beingUsed;
    }

    public void setShowRendered(boolean showRendered) {
        this.showRendered = showRendered;
    }

    public Tile[][] convertGridToArray() {
        Tile[][] tiles = new Tile[xWorldSize][yWorldSize];

        for (int x = 0; x < xWorldSize; x++) {
            for (int y = 0; y < yWorldSize; y++) {
                tiles[x][y] = grid[x][y].getTile();
            }
        }

        return tiles.clone();
    }

    public void setGridFromArray(Tile[][] tilesArray, int xWorldSize, int yWorldSize) {
        this.setSize(xWorldSize, yWorldSize);
        initGrid();

        for (int x = 0; x < xWorldSize; x++) {
            for (int y = 0; y < yWorldSize; y++) {
                grid[x][y].setTile(tilesArray[x][y]);
            }
        }
    }
}
