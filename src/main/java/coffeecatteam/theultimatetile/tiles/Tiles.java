package coffeecatteam.theultimatetile.tiles;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.manager.ItemManager;

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

    public static Tile AIR; // USES EMPTY SPACE IN TILES SHEET

    public static Tile BOOKSHELF;

    public static void init(TheUltimateTile theUltimateTile) {
        GRASS = getTile(theUltimateTile, 0);
        DIRT = getTile(theUltimateTile, 1);
        STONE = getTile(theUltimateTile, 2);
        SAND = getTile(theUltimateTile, 3);
        ANDESITE = getTile(theUltimateTile, 4);
        DIORITE = getTile(theUltimateTile, 5);

        COAL_ORE = getTile(theUltimateTile, 6);
        IRON_ORE = getTile(theUltimateTile, 7);
        GOLD_ORE = getTile(theUltimateTile, 8);
        DIAMOND_ORE = getTile(theUltimateTile, 9);

        OBSIDIAN = getTile(theUltimateTile, 10);

        WATER = getTile(theUltimateTile, 11);
        LAVA = getTile(theUltimateTile, 12);

        PLANKS = getTile(theUltimateTile, 13);
        BROKEN_STONE = getTile(theUltimateTile, 14);

        AIR = getTile(theUltimateTile, 15);

        BOOKSHELF = getTile(theUltimateTile, 16);
    }

    public static Tile getTile(TheUltimateTile theUltimateTile, int id) {
        switch (id) {
            case 0:
                return new Tile(theUltimateTile, Assets.GRASS, id, false, Tile.TileType.GROUND);
            case 1:
                return new Tile(theUltimateTile, Assets.DIRT, id, false, Tile.TileType.GROUND);
            case 2:
                return new TileBreakable(theUltimateTile, Assets.STONE, id, ItemManager.ROCK, Tile.TileType.STONE);
            case 3:
                return new Tile(theUltimateTile, Assets.SAND, id, false, Tile.TileType.GROUND);
            case 4:
                return new Tile(theUltimateTile, Assets.ANDESITE, id, true, Tile.TileType.STONE);
            case 5:
                return new Tile(theUltimateTile, Assets.DIORITE, id, true, Tile.TileType.STONE);

            case 6:
                return new TileBreakable(theUltimateTile, Assets.COAL_ORE, id, ItemManager.COAL, Tile.TileType.STONE);
            case 7:
                return new TileBreakable(theUltimateTile, Assets.IRON_ORE, id, ItemManager.IRON_INGOT, Tile.TileType.STONE);
            case 8:
                return new TileBreakable(theUltimateTile, Assets.GOLD_ORE, id, ItemManager.GOLD_INGOT, Tile.TileType.STONE);
            case 9:
                return new TileBreakable(theUltimateTile, Assets.DIAMOND_ORE, id, ItemManager.DIAMOND, Tile.TileType.STONE);

            case 10:
                return new Tile(theUltimateTile, Assets.OBSIDIAN, id, true, Tile.TileType.STONE);

            case 11:
                return new TileAnimated(theUltimateTile, new Animation(50, Assets.WATER), id, false, Tile.TileType.FLUID);
            case 12:
                return new TileAnimated(theUltimateTile, new Animation(50, Assets.LAVA), id, false, Tile.TileType.FLUID);

            case 13:
                return new Tile(theUltimateTile, Assets.PLANKS, id, true, Tile.TileType.WOOD);
            case 14:
                return new Tile(theUltimateTile, Assets.BROKEN_STONE, id, true, Tile.TileType.STONE);

            case 15:
                return new Tile(theUltimateTile, Assets.AIR, id, false, Tile.TileType.AIR);

            case 16:
                return new Tile(theUltimateTile, Assets.BOOKSHELF, id, true, Tile.TileType.WOOD);
        }
        return getTile(theUltimateTile, 15);
    }
}
