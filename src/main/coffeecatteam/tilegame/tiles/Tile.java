package coffeecatteam.tilegame.tiles;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {

    public static Tile[] tiles = new Tile[256];

    public static final int TILE_WIDTH = 64, TILE_HEIGHT = 64;

    protected BufferedImage texture;
    protected final int id;

    protected Rectangle bounds;

    protected int x, y;

    public Tile(BufferedImage texture, int id) {
        this.texture = texture;
        this.id = id;

        tiles[id] = this;

        bounds = new Rectangle(0, 0, TILE_WIDTH, TILE_HEIGHT);
    }

    public void tick() {
        bounds = new Rectangle(x * Tile.TILE_WIDTH, y * Tile.TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
    }

    public void render(Graphics g, int x, int y) {
        render(g, x, y, new Color(255, 255, 255, 0));
    }

    public void render(Graphics g, int x, int y, Color tint) {
        g.drawImage(texture, x, y, TILE_WIDTH, TILE_HEIGHT, null);
        g.setColor(tint);
        g.fillRect(x, y, TILE_WIDTH, TILE_HEIGHT);
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isSolid() {
        return false;
    }

    public int getId() {
        return id;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
