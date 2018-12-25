package coffeecatteam.theultimatetile.levelcreator.grid;

import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.game.tile.Tiles;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.levelcreator.CreatorEngine;
import coffeecatteam.theultimatetile.levelcreator.LevelRenderer;

import java.awt.*;

public class GridTileSelect extends Grid {

    private GridTile[][] grid;
    private GridTile selected;
    private int mouseX, mouseY;

    public GridTileSelect(CreatorEngine creatorEngine, int ogX, int ogY, int gridSize, Vector2D position, int xWorldSize, int yWorldSize) {
        super(creatorEngine, ogX, ogY, gridSize, position, xWorldSize, yWorldSize);
        grid = new GridTile[xWorldSize][yWorldSize];
        initGrid();
    }

    private void initGrid() {
        int ti = 0;
        int w = (ogX * 2) / gridSize, h = (ogY * 2) / gridSize;
        for (int y = 0; y < yWorldSize; y++) {
            for (int x = 0; x < xWorldSize; x++) {
                grid[x][y] = new GridTile(new Vector2D(this.position.x + w * x, this.position.y + h * y), w, h).setTile(Tiles.TILES.get(ti));
                if (ti < Tiles.TILES.size() - 1)
                    ti++;
                else break;
            }
            if (ti > Tiles.TILES.size() - 2) break;
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
                int gx = (int) (this.position.x + w * x), gy = (int) (this.position.y + h * y);
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
