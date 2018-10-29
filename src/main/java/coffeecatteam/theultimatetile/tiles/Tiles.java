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
        GRASS = getTile(theUltimateTile, "grass");
        DIRT = getTile(theUltimateTile, "dirt");
        STONE = getTile(theUltimateTile, "stone");
        SAND = getTile(theUltimateTile, "sand");
        ANDESITE = getTile(theUltimateTile, "andesite");
        DIORITE = getTile(theUltimateTile, "diorite");

        COAL_ORE = getTile(theUltimateTile, "coal_ore");
        IRON_ORE = getTile(theUltimateTile, "iron_ore");
        GOLD_ORE = getTile(theUltimateTile, "gold_ore");
        DIAMOND_ORE = getTile(theUltimateTile, "diamond_ore");

        OBSIDIAN = getTile(theUltimateTile, "obsidian");

        WATER = getTile(theUltimateTile, "water");
        LAVA = getTile(theUltimateTile, "lava");

        PLANKS = getTile(theUltimateTile, "planks");
        BROKEN_STONE = getTile(theUltimateTile, "broken_stone");

        AIR = getTile(theUltimateTile, "air");

        BOOKSHELF = getTile(theUltimateTile, "bookshelf");
    }

    public static Tile getTile(TheUltimateTile theUltimateTile, String id) {
        switch (id) {
            case "grass":
                return new Tile(theUltimateTile, Assets.GRASS, id, false, Tile.TileType.GROUND);
            case "dirt":
                return new Tile(theUltimateTile, Assets.DIRT, id, false, Tile.TileType.GROUND);
            case "stone":
                return new TileBreakable(theUltimateTile, Assets.STONE, id, ItemManager.ROCK, Tile.TileType.STONE);
            case "sand":
                return new Tile(theUltimateTile, Assets.SAND, id, false, Tile.TileType.GROUND);
            case "andesite":
                return new Tile(theUltimateTile, Assets.ANDESITE, id, true, Tile.TileType.STONE);
            case "diorite":
                return new Tile(theUltimateTile, Assets.DIORITE, id, true, Tile.TileType.STONE);

            case "coal_ore":
                return new TileBreakable(theUltimateTile, Assets.COAL_ORE, id, ItemManager.COAL, Tile.TileType.STONE);
            case "iron_ore":
                return new TileBreakable(theUltimateTile, Assets.IRON_ORE, id, ItemManager.IRON_INGOT, Tile.TileType.STONE);
            case "gold_ore":
                return new TileBreakable(theUltimateTile, Assets.GOLD_ORE, id, ItemManager.GOLD_INGOT, Tile.TileType.STONE);
            case "diamond_ore":
                return new TileBreakable(theUltimateTile, Assets.DIAMOND_ORE, id, ItemManager.DIAMOND, Tile.TileType.STONE);

            case "obsidian":
                return new Tile(theUltimateTile, Assets.OBSIDIAN, id, true, Tile.TileType.STONE);

            case "water":
                return new TileAnimated(theUltimateTile, new Animation(50, Assets.WATER), id, false, Tile.TileType.FLUID);
            case "lava":
                return new TileAnimated(theUltimateTile, new Animation(50, Assets.LAVA), id, false, Tile.TileType.FLUID);

            case "planks":
                return new Tile(theUltimateTile, Assets.PLANKS, id, true, Tile.TileType.WOOD);
            case "broken_stone":
                return new Tile(theUltimateTile, Assets.BROKEN_STONE, id, true, Tile.TileType.STONE);

            case "air":
                return new Tile(theUltimateTile, Assets.AIR, id, false, Tile.TileType.AIR);

            case "bookshelf":
                return new Tile(theUltimateTile, Assets.BOOKSHELF, id, true, Tile.TileType.WOOD);
        }
        return getTile(theUltimateTile, "air");
    }
}
