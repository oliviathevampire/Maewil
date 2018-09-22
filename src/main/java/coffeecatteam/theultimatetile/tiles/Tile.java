package coffeecatteam.theultimatetile.tiles;

import coffeecatteam.theultimatetile.TheUltimateTile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {

    public static final int TILE_WIDTH = 64, TILE_HEIGHT = 64;

    protected TheUltimateTile theUltimateTile;
    protected BufferedImage texture;
    protected final int id;

    protected Rectangle bounds;

    protected int x, y;

    private boolean isSolid;

    public Tile(TheUltimateTile theUltimateTile, BufferedImage texture, int id, boolean isSolid) {
        this.theUltimateTile = theUltimateTile;
        this.texture = texture;
        this.id = id;

        bounds = new Rectangle(0, 0, TILE_WIDTH, TILE_HEIGHT);
        this.isSolid = isSolid;
    }

    public void updateBounds() {
        bounds = new Rectangle((int) (x * Tile.TILE_WIDTH - theUltimateTile.getCamera().getxOffset()), (int) (y * Tile.TILE_HEIGHT - theUltimateTile.getCamera().getyOffset()), TILE_WIDTH, TILE_HEIGHT);
    }

    public void tick() {
        updateBounds();
    }

    public void render(Graphics g) {
        render(g, new Color(255, 255, 255, 0));
    }

    public void render(Graphics g, Color tint) {
        g.drawImage(texture, (int) (x * Tile.TILE_WIDTH - theUltimateTile.getCamera().getxOffset()), (int) (y * Tile.TILE_HEIGHT - theUltimateTile.getCamera().getyOffset()), TILE_WIDTH, TILE_HEIGHT, null);
        g.setColor(tint);
        g.fillRect((int) (x * Tile.TILE_WIDTH - theUltimateTile.getCamera().getxOffset()), (int) (y * Tile.TILE_HEIGHT - theUltimateTile.getCamera().getyOffset()), TILE_WIDTH, TILE_HEIGHT);
    }

    public Tile setPos(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public boolean isSolid() {
        return isSolid;
    }

    public Tile setSolid(boolean solid) {
        isSolid = solid;
        return this;
    }

    public int getId() {
        return id;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
