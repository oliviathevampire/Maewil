package coffeecatteam.theultimatetile.levelcreator.grid;

import coffeecatteam.coffeecatutils.logger.CatLogger;
import coffeecatteam.coffeecatutils.position.AABB;
import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.game.tile.Tile;
import coffeecatteam.theultimatetile.game.tile.Tiles;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.levelcreator.CreatorEngine;
import coffeecatteam.theultimatetile.levelcreator.LevelRenderer;
import coffeecatteam.theultimatetile.levelcreator.WorldLayerUpdater;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class GridWorldEditor extends Grid {

    private GridTile[][] grid;
    private AABB gridBounds;
    private int mouseX, mouseY, tileWidth, tileHeight;
    private Tile SELECTED_TILE;

    private boolean beingUsed, showRendered = true;
    private WorldLayerUpdater updater;

    public GridWorldEditor(CreatorEngine creatorEngine, int ogX, int ogY, int gridSize, Vector2D position, int xWorldSize, int yWorldSize, boolean beingUsed) {
        super(creatorEngine, ogX, ogY, gridSize, position, xWorldSize, yWorldSize);
        initGrid();

        this.beingUsed = beingUsed;
    }

    private void initGrid() {
        grid = new GridTile[xWorldSize][yWorldSize];

        int w = (ogX * 2) / gridSize, h = (ogY * 2) / gridSize;
        for (int gx = 0; gx < xWorldSize; gx++) {
            for (int gy = 0; gy < yWorldSize; gy++) {
                grid[gx][gy] = new GridTile(new Vector2D(ogX + w * gx, ogY + h * gy), w, h);
            }
        }
        startWorldLayerUpdate();
    }

    public synchronized void startWorldLayerUpdate() {
        updater = new WorldLayerUpdater(creatorEngine, this);
        Thread wlu = new Thread(updater, "World Layer Updater");
        wlu.start();
        new CatLogger(wlu).print("'World Layer Updater' started!");
    }

    public void updateVars(AABB gridBounds, int mouseX, int mouseY, Tile SELECTED_TILE) {
        this.gridBounds = gridBounds;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.SELECTED_TILE = SELECTED_TILE;
    }

    @Override
    public void update(GameContainer container, int delta) {
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

        tileWidth = (ogX * 2) / gridSize;
        tileHeight = (ogY * 2) / gridSize;
        for (int x = 0; x < xWorldSize; x++) {
            for (int y = 0; y < yWorldSize; y++) {
                GridTile tile = grid[x][y];
                tile.getPosition().x = ogX + tileWidth * x;
                tile.getPosition().y = ogY + tileHeight * y;
                tile.setWidth(tileWidth);
                tile.setHeight(tileHeight);

                if (beingUsed) {
                    tile.update(container, delta);

                    if (tile.getBounds().contains(mouseX, mouseY)) {
                        if (gridBounds.contains(mouseX, mouseY)) {
                            if (creatorEngine.isLeftDown() || creatorEngine.isLeftPressed()) {
                                if (tile.getTile() != SELECTED_TILE)
                                    tile.setTile(LevelRenderer.getSelectedTile().newTile());
                            } else if (creatorEngine.isRightDown() || creatorEngine.isRightPressed())
                                tile.setTile(Tiles.AIR);
                        }
                    }
                }
            }
        }

        updater.setxWorldSize(xWorldSize);
        updater.setyWorldSize(yWorldSize);
    }

    public void updateWorldLayer() {
        for (int x = 0; x < xWorldSize; x++) {
            for (int y = 0; y < yWorldSize; y++) {
                grid[x][y].setWorldLayer(grid, xWorldSize, yWorldSize);
            }
        }
    }

    @Override
    public void render(Graphics g) {
        if (showRendered) {
            super.render(g);
            for (int x = 0; x < xWorldSize; x++) {
                for (int y = 0; y < yWorldSize; y++) {
                    GridTile tile = grid[x][y];
                    if (tile != null)
                        tile.render(g);
                    else
                        Assets.MISSING_TEXTURE.draw((int) this.position.x + tileWidth * x + xOff, (int) this.position.y + tileHeight * y + yOff, tileWidth, tileHeight);
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
        int minX = -((tileWidth * xWorldSize) - creatorEngine.getWidth() / 2 + maxX);

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
        int minY = -((tileHeight * yWorldSize) - creatorEngine.getHeight() / 2 + maxY);

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

    public void setGridFromArray(Tile[][] tilesArray, int xWorldSize, int yWorldSize) {
        this.setSize(xWorldSize, yWorldSize);
        initGrid();

        for (int x = 0; x < xWorldSize; x++) {
            for (int y = 0; y < yWorldSize; y++) {
                grid[x][y].setTile(tilesArray[x][y]);
            }
        }
    }

    public GridTile[][] getGrid() {
        return grid;
    }
}
