package coffeecatteam.theultimatetile.levelcreator;

import coffeecatteam.theultimatetile.game.tiles.Tile;
import coffeecatteam.theultimatetile.game.tiles.Tiles;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.ui.UISlider;
import coffeecatteam.theultimatetile.levelcreator.grid.Grid;
import coffeecatteam.theultimatetile.levelcreator.grid.GridTile;
import coffeecatteam.theultimatetile.levelcreator.grid.GridTileSelect;
import coffeecatteam.theultimatetile.manager.UIManager;
import coffeecatteam.theultimatetile.utils.Logger;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LevelRenderer {

    private static Tile SELECTED_TILE;

    private CreatorEngine creatorEngine;
    private int xWorldSize, yWorldSize, mouseX, mouseY;

    private GridTile[][] grid;
    private Rectangle gridBounds;
    private int ogX, ogY, ogWorldGridSize = 10, worldGridSize = ogWorldGridSize, selectGridSize = 10;

    private List<Grid> grids = new ArrayList<>();
    private UIManager uiManager;

    public LevelRenderer(CreatorEngine creatorEngine, int xWorldSize, int yWorldSize) {
        this.creatorEngine = creatorEngine;
        this.xWorldSize = xWorldSize;
        this.yWorldSize = yWorldSize;

        Tiles.init(creatorEngine);
        SELECTED_TILE = Tiles.GRASS;

        grid = new GridTile[xWorldSize][yWorldSize];
        ogX = creatorEngine.getWidth() / 4;
        ogY = creatorEngine.getHeight() / 4;
        gridBounds = new Rectangle(ogX, ogY, creatorEngine.getWidth() / 2, creatorEngine.getHeight() / 2);
        initGrids();
        initUI();
    }

    Grid worldEditorGrid;
    private void initGrids() {
        int w = (ogX * 2) / worldGridSize, h = (ogY * 2) / worldGridSize;
        for (int x = 0; x < xWorldSize; x++) {
            for (int y = 0; y < yWorldSize; y++) {
                grid[x][y] = new GridTile(ogX + w * x, ogY + h * y, w, h);
            }
        }

        worldEditorGrid = new Grid(creatorEngine, ogX, ogY, worldGridSize, ogX, ogY, xWorldSize, yWorldSize) {
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

                for (int x = 0; x < xWorldSize; x++) {
                    for (int y = 0; y < yWorldSize; y++) {
                        GridTile tile = grid[x][y];

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

            @Override
            public void render(Graphics g) {
                super.render(g);
                for (int x = 0; x < xWorldSize; x++) {
                    for (int y = 0; y < yWorldSize; y++) {
                        GridTile tile = grid[x][y];

                        tile.render(g);
                    }
                }
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
        };
        grids.add(worldEditorGrid);

        grids.add(new GridTileSelect(creatorEngine, ogX, ogY, selectGridSize, (int) (ogX + ((ogX * 2) / selectGridSize) * 10.5f), ogY, 4, selectGridSize));
    }

    UISlider zoomSlider;
    private void initUI() {
        uiManager = new UIManager(creatorEngine);

        zoomSlider = new UISlider(creatorEngine, 10, 50, 200);
        uiManager.addObject(zoomSlider);
    }

    public void tick() {
        mouseX = creatorEngine.getMouseManager().getMouseX();
        mouseY = creatorEngine.getMouseManager().getMouseY();

        uiManager.tick();
        worldGridSize = ogWorldGridSize + zoomSlider.getValue();
        worldEditorGrid.setGridSize(worldGridSize);

        int w = (ogX * 2) / worldGridSize, h = (ogY * 2) / worldGridSize;
        for (int x = 0; x < xWorldSize; x++) {
            for (int y = 0; y < yWorldSize; y++) {
                grid[x][y].setX(ogX + w * x);
                grid[x][y].setY(ogY + h * y);
                grid[x][y].setWidth(w);
                grid[x][y].setHeight(h);
            }
        }

        grids.forEach(Grid::tick);
    }

    public void render(Graphics g) {
        g.drawImage(Assets.MG_OVERLAY_INNER_MID, 0, 0, creatorEngine.getWidth(), creatorEngine.getHeight(), null);
        grids.forEach(grid -> grid.render(g));
    }

    public void postRender(Graphics g) {
        uiManager.render(g);
        Font font = Assets.FONT_20;
        Text.drawString(g, "Zoom: x" + zoomSlider.getValue(), (int) zoomSlider.getX() + zoomSlider.getWidth() + 25, (int) zoomSlider.getY() + Text.getHeight(g, font), false, false, Color.white, font);
    }

    public static Tile getSelectedTile() {
        return SELECTED_TILE;
    }

    public static void setSelectedTile(Tile selectedTile) {
        SELECTED_TILE = selectedTile;
    }
}
