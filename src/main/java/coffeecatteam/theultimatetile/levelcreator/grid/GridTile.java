package coffeecatteam.theultimatetile.levelcreator.grid;

import coffeecatteam.theultimatetile.game.tiles.Tile;
import coffeecatteam.theultimatetile.game.tiles.Tiles;

import java.awt.*;

public class GridTile {

    private int x, y, xOff = 0, yOff = 0;
    private int width, height;

    private Rectangle bounds;
    private Tile tile = Tiles.AIR;

    public GridTile(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        bounds = new Rectangle(x, y, width, height);
    }

    public void render(Graphics g) {
        bounds = new Rectangle(x + xOff, y + yOff, width, height);

        g.drawImage(tile.getTexture(), x + xOff, y + yOff, width, height, null);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
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

    public Rectangle getBounds() {
        return bounds;
    }

    public Tile getTile() {
        return tile;
    }

    public GridTile setTile(Tile tile) {
        this.tile = tile;
        return this;
    }
}
