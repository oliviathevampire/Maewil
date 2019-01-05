package coffeecatteam.theultimatetile.levelcreator.grid;

import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.game.tile.Tiles;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.levelcreator.CreatorEngine;
import coffeecatteam.theultimatetile.levelcreator.LevelRenderer;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

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
    public void update(GameContainer container, int delta) {
        mouseX = creatorEngine.getMouseX();
        mouseY = creatorEngine.getMouseY();

        for (int x = 0; x < xWorldSize; x++) {
            for (int y = 0; y < yWorldSize; y++) {
                if (grid[x][y] != null) {
                    grid[x][y].update(container, delta);

                    if (grid[x][y].getBounds().contains(mouseX, mouseY)) {
                        if (creatorEngine.isLeftPressed()) {
                            selected = grid[x][y];
                            LevelRenderer.setSelectedTile(selected.getTile());
                        }
                    }
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        Assets.MG_OVERLAY_INNER_MID_RIGHT.draw(0, 0, creatorEngine.getWidth(), creatorEngine.getHeight());
        super.render(g);

        int w = (ogX * 2) / gridSize, h = (ogY * 2) / gridSize;
        for (int x = 0; x < xWorldSize; x++) {
            for (int y = 0; y < yWorldSize; y++) {
                int gx = (int) (this.position.x + w * x), gy = (int) (this.position.y + h * y);
                if (grid[x][y] != null) {
                    grid[x][y].getTile().getTexture().draw(gx, gy, w, h);

                    if (selected == grid[x][y])
                        Assets.SELECTED_TILE.draw(gx, gy, w, h);
                    if (grid[x][y].getBounds().contains(mouseX, mouseY))
                        Assets.SELECTED_TILE.draw(gx, gy, w, h);
                }
            }
        }
    }
}
