package coffeecatteam.theultimatetile.tiles;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.inventory.items.Items;

public class Tiles {

    public static Tile GRASS;
    public static Tile DIRT;
    public static Tile STONE;
    public static Tile SAND;
    public static Tile ANDESITE;
    public static Tile DIORITE;

    public static Tile COAL_ORE;
    public static Tile IRON_ORE;
    public static Tile GOLD_ORE;
    public static Tile DIAMOND_ORE;

    public static Tile OBSIDIAN;

    public static Tile WATER;
    public static Tile LAVA;

    public static Tile PLANKS;
    public static Tile BROKEN_STONE;

    public static Tile AIR; // USES EMPPTY SPACE IN TILES SHEET

    public static Tile BOOKSHELF;

    public static void init(TheUltimateTile theUltimateTile) {
        GRASS = new Tile(theUltimateTile, Assets.GRASS, 0, false);
        DIRT = new Tile(theUltimateTile, Assets.DIRT, 1, false);
        STONE = new TileBreakable(theUltimateTile, Assets.STONE, 2, Items.ROCK);
        SAND = new Tile(theUltimateTile, Assets.SAND, 3, false);
        ANDESITE = new Tile(theUltimateTile, Assets.ANDESITE, 4, true);
        DIORITE = new Tile(theUltimateTile, Assets.DIORITE, 5, true);

        COAL_ORE = new TileBreakable(theUltimateTile, Assets.COAL_ORE, 6, Items.COAL);
        IRON_ORE = new TileBreakable(theUltimateTile, Assets.IRON_ORE, 7, Items.IRON_INGOT);
        GOLD_ORE = new TileBreakable(theUltimateTile, Assets.GOLD_ORE, 8, Items.GOLD_INGOT);
        DIAMOND_ORE = new TileBreakable(theUltimateTile, Assets.DIAMOND_ORE, 9, Items.DIAMOND);

        OBSIDIAN = new Tile(theUltimateTile, Assets.OBSIDIAN, 10, true);

        WATER = new TileAnimated(theUltimateTile, new Animation(50, Assets.WATER), 11, false);
        LAVA = new TileAnimated(theUltimateTile, new Animation(50, Assets.LAVA), 12, false);

        PLANKS = new Tile(theUltimateTile, Assets.PLANKS, 13, true);
        BROKEN_STONE = new Tile(theUltimateTile, Assets.BROKEN_STONE, 14, true);

        AIR = new Tile(theUltimateTile, Assets.AIR, 15, false); // USES EMPPTY SPACE IN TILES SHEET

        BOOKSHELF = new Tile(theUltimateTile, Assets.BOOKSHELF, 16, true);
    }

    public static Tile getTile(TheUltimateTile theUltimateTile, int id) {
        switch (id) {
            case 0:
                return new Tile(theUltimateTile, Assets.GRASS, id, false);
            case 1:
                return new Tile(theUltimateTile, Assets.DIRT, id, false);
            case 2:
                return new TileBreakable(theUltimateTile, Assets.STONE, id, Items.ROCK);
            case 3:
                return new Tile(theUltimateTile, Assets.SAND, id, false);
            case 4:
                return new Tile(theUltimateTile, Assets.ANDESITE, id, true);
            case 5:
                return new Tile(theUltimateTile, Assets.DIORITE, id, true);

            case 6:
                return new TileBreakable(theUltimateTile, Assets.COAL_ORE, id, Items.COAL);
            case 7:
                return new TileBreakable(theUltimateTile, Assets.IRON_ORE, id, Items.IRON_INGOT);
            case 8:
                return new TileBreakable(theUltimateTile, Assets.GOLD_ORE, id, Items.GOLD_INGOT);
            case 9:
                return new TileBreakable(theUltimateTile, Assets.DIAMOND_ORE, id, Items.DIAMOND);

            case 10:
                return new Tile(theUltimateTile, Assets.OBSIDIAN, id, true);

            case 11:
                return new TileAnimated(theUltimateTile, new Animation(50, Assets.WATER), id, false);
            case 12:
                return new TileAnimated(theUltimateTile, new Animation(50, Assets.LAVA), id, false);

            case 13:
                return new Tile(theUltimateTile, Assets.PLANKS, id, true);
            case 14:
                return new Tile(theUltimateTile, Assets.BROKEN_STONE, id, true);

            case 15:
                return new Tile(theUltimateTile, Assets.AIR, id, false);

            case 16:
                return new Tile(theUltimateTile, Assets.BOOKSHELF, id, true);
        }
        return new Tile(theUltimateTile, Assets.AIR, id, false);
    }
}
