package coffeecatteam.tilegame.tiles;

import coffeecatteam.tilegame.gfx.Animation;
import coffeecatteam.tilegame.gfx.Assets;

public class Tiles {

    public static Tile GRASS = new TileBase(Assets.GRASS, 0, false);
    public static Tile DIRT = new TileBase(Assets.DIRT, 1, false);
    public static Tile STONE = new TileBase(Assets.STONE, 2, true);
    public static Tile SAND = new TileBase(Assets.SAND, 3, false);
    public static Tile ANDESITE = new TileBase(Assets.ANDESITE, 4, true);
    public static Tile DIORITE = new TileBase(Assets.DIORITE, 5, true);
    public static Tile COAL_ORE = new TileBase(Assets.COAL_ORE, 6, true);
    public static Tile IRON_ORE = new TileBase(Assets.IRON_ORE, 7, true);
    public static Tile GOLD_ORE = new TileBase(Assets.GOLD_ORE, 8, true);
    public static Tile DIAMOND_ORE = new TileBase(Assets.DIAMOND_ORE, 9, true);
    public static Tile OBSIDIAN = new TileBase(Assets.OBSIDIAN, 10, true);

    public static Tile WATER = new TileAnimated(new Animation(50, Assets.WATER), 11);
    public static Tile LAVA = new TileAnimated(new Animation(50, Assets.LAVA), 12);
}
