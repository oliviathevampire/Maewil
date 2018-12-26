package coffeecatteam.theultimatetile.levelcreator.grid;

import coffeecatteam.coffeecatutils.position.AABB;
import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.game.tile.Tile;
import coffeecatteam.theultimatetile.game.tile.Tiles;
import coffeecatteam.theultimatetile.game.tile.tiles.TileLava;

import java.awt.*;

public class GridTile {

    private Vector2D position;
    private int xOff = 0, yOff = 0;
    private int width, height;

    private AABB bounds;
    private Tile tile = Tiles.AIR.copy();

    public GridTile(Vector2D position, int width, int height) {
        this.position = position;
        this.width = width;
        this.height = height;

        bounds = new AABB(this.position, width, height);
    }

    public void tick() {
        tile.tick();
        tile.forcedTick();
    }

    public void render(Graphics2D g) {
        bounds = new AABB((int) this.position.x + xOff, (int) this.position.y + yOff, width, height);

        tile.render(g, (int) this.position.x + xOff, (int) this.position.y + yOff, width, height);
    }

    public void setWorldLayer(GridTile[][] worldLayer, int xWorldSize, int yWorldSize) {
        this.tile.setWorldLayer(Grid.convertGridToArray(worldLayer, xWorldSize, yWorldSize));
    }

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public int getxOff() {
        return xOff;
    }

    public void setxOff(int xOff) {
        this.xOff = xOff;
    }

    public int getyOff() {
        return yOff;
    }

    public void setyOff(int yOff) {
        this.yOff = yOff;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public AABB getBounds() {
        return bounds;
    }

    public Tile getTile() {
        return tile;
    }

    public GridTile setTile(Tile tile) {
        this.tile = tile;
        return this;
    }

    public GridTile copy() {
        return new GridTile(position, width, height).setTile(this.tile.copy());
    }
}
