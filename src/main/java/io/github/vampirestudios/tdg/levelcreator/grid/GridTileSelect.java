package io.github.vampirestudios.tdg.levelcreator.grid;

import coffeecatteam.theultimatetile.game.tiles.Tiles;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.levelcreator.LevelRenderer;
import io.github.vampirestudios.tdg.levelcreator.CreatorEngine;
import io.github.vampirestudios.tdg.objs.tiles.Tile;
import io.github.vampirestudios.tdg.utils.registry.Registries;

import java.awt.*;

public class GridTileSelect extends Grid {

    private GridTile[][] grid;
    private GridTile selected;
    private int mouseX, mouseY;

    public GridTileSelect(CreatorEngine creatorEngine, int ogX, int ogY, int gridSize, int x, int y, int xWorldSize, int yWorldSize) {
        super(creatorEngine, ogX, ogY, gridSize, x, y, xWorldSize, yWorldSize);
        grid = new GridTile[xWorldSize][yWorldSize];
        initGrid();
    }

    private void initGrid() {
        int ti = 0;
        int w = (ogX * 2) / gridSize, h = (ogY * 2) / gridSize;
        for (int y = 0; y < yWorldSize; y++) {
            for (int x = 0; x < xWorldSize; x++) {
                grid[x][y] = new GridTile(this.x + w * x, this.y + h * y, w, h).setTile((Tile) Registries.TILES.values().toArray()[ti]);
                if (ti < Registries.TILES.getSize() - 1)
                    ti++;
                else break;
            }
            if (ti > Registries.TILES.getSize() - 2) break;
        }
        selected = grid[0][0];
    }

    @Override
    public void tick() {
        mouseX = creatorEngine.getMouseManager().getMouseX();
        mouseY = creatorEngine.getMouseManager().getMouseY();

        int w = (ogX * 2) / gridSize, h = (ogY * 2) / gridSize;
        for (int x = 0; x < xWorldSize; x++) {
            for (int y = 0; y < yWorldSize; y++) {
                if (grid[x][y] != null) {
                    grid[x][y].tick();

                    if (grid[x][y].getBounds().contains(mouseX, mouseY)) {
                        if (creatorEngine.getMouseManager().isLeftPressed()) {
                            selected = grid[x][y];
                            LevelRenderer.setSelectedTile(selected.getTile());
                        }
                    }
                }
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(Assets.MG_OVERLAY_INNER_MID_RIGHT, 0, 0, creatorEngine.getWidth(), creatorEngine.getHeight(), null);
        super.render(g);

        int w = (ogX * 2) / gridSize, h = (ogY * 2) / gridSize;
        for (int x = 0; x < xWorldSize; x++) {
            for (int y = 0; y < yWorldSize; y++) {
                int gx = this.x + w * x, gy = this.y + h * y;
                if (grid[x][y] != null) {
                    grid[x][y].render(g);

                    if (selected == grid[x][y])
                        g.drawImage(Assets.SELECTED_TILE, gx, gy, w, h, null);
                    if (grid[x][y].getBounds().contains(mouseX, mouseY))
                        g.drawImage(Assets.SELECTED_TILE, gx, gy, w, h, null);
                }
            }
        }
    }
}
