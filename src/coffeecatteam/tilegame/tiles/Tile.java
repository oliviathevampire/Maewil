package coffeecatteam.tilegame.tiles;

import coffeecatteam.tilegame.gfx.Animation;
import coffeecatteam.tilegame.gfx.Assets;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tile {

    public static Tile[] tiles = new Tile[256];
    public static Tile GRASS = new TileBase(Assets.GRASS, 0, false);
    public static Tile DIRT = new TileBase(Assets.DIRT, 1, false);
    public static Tile STONE = new TileBase(Assets.STONE, 2, true);
    public static Tile SAND = new TileBase(Assets.SAND, 3, false);
    public static Tile ANDESITE = new TileBase(Assets.ANDESITE, 4, true);
    public static Tile DIORITE = new TileBase(Assets.DIORITE, 5, true);
    public static Tile COAL_ORE = new TileBase(Assets.COAL_ORE, 6, true);
    public static Tile IRON_COAL = new TileBase(Assets.IRON_COAL, 7, true);
    public static Tile GOLD_ORE = new TileBase(Assets.GOLD_ORE, 8, true);
    public static Tile DIAMOND_ORE = new TileBase(Assets.DIAMOND_ORE, 9, true);
    public static Tile OBSIDIAN = new TileBase(Assets.OBSIDIAN, 10, true);

    public static Tile WATER = new TileAnimated(new Animation(50, Assets.WATER), 11);
    public static Tile LAVA = new TileAnimated(new Animation(50, Assets.LAVA), 12); // P.26

    public static final int TILE_WIDTH = 64, TILE_HEIGHT = 64;

    protected BufferedImage texture;
    protected final int id;

    public Tile(BufferedImage texture, int id) {
        this.texture = texture;
        this.id = id;

        tiles[id] = this;
    }

    public void tick() {

    }

    public void render(Graphics g, int x, int y) {
        g.drawImage(texture, x, y, TILE_WIDTH, TILE_HEIGHT, null);
    }

    public boolean isSolid() {
        return false;
    }

    public int getId() {
        return id;
    }
}
