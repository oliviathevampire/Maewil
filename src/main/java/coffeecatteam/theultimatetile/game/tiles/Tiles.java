package coffeecatteam.theultimatetile.game.tiles;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.manager.ItemManager;

import java.util.ArrayList;
import java.util.List;

public class Tiles {

    public static final List<Tile> TILES = new ArrayList<>();

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

    public static void init(Engine engine) {
        register(GRASS = getTile(engine, "grass"));
        register(DIRT = getTile(engine, "dirt"));
        register(STONE = getTile(engine, "stone"));
        register(SAND = getTile(engine, "sand"));
        register(ANDESITE = getTile(engine, "andesite"));
        register(DIORITE = getTile(engine, "diorite"));

        register(COAL_ORE = getTile(engine, "coal_ore"));
        register(IRON_ORE = getTile(engine, "iron_ore"));
        register(GOLD_ORE = getTile(engine, "gold_ore"));
        register(DIAMOND_ORE = getTile(engine, "diamond_ore"));

        register(OBSIDIAN = getTile(engine, "obsidian"));

        register(WATER = getTile(engine, "water"));
        register(LAVA = getTile(engine, "lava"));

        register(PLANKS = getTile(engine, "planks"));
        register(BROKEN_STONE = getTile(engine, "broken_stone"));

        AIR = getTile(engine, "air");

        register(BOOKSHELF = getTile(engine, "bookshelf"));
    }

    private static void register(Tile tile) {
        TILES.add(tile);
    }

    public static Tile getTile(Engine engine, String id) {
        switch (id) {
            case "grass":
                return new Tile(engine, Assets.GRASS, id, false, Tile.TileType.GROUND);
            case "dirt":
                return new Tile(engine, Assets.DIRT, id, false, Tile.TileType.GROUND);
            case "stone":
                return new TileBreakable(engine, Assets.STONE, id, ItemManager.ROCK, Tile.TileType.STONE);
            case "sand":
                return new Tile(engine, Assets.SAND, id, false, Tile.TileType.GROUND);
            case "andesite":
                return new Tile(engine, Assets.ANDESITE, id, true, Tile.TileType.STONE);
            case "diorite":
                return new Tile(engine, Assets.DIORITE, id, true, Tile.TileType.STONE);

            case "coal_ore":
                return new TileBreakable(engine, Assets.COAL_ORE, id, ItemManager.COAL, Tile.TileType.STONE);
            case "iron_ore":
                return new TileBreakable(engine, Assets.IRON_ORE, id, ItemManager.IRON_INGOT, Tile.TileType.STONE);
            case "gold_ore":
                return new TileBreakable(engine, Assets.GOLD_ORE, id, ItemManager.GOLD_INGOT, Tile.TileType.STONE);
            case "diamond_ore":
                return new TileBreakable(engine, Assets.DIAMOND_ORE, id, ItemManager.DIAMOND, Tile.TileType.STONE);

            case "obsidian":
                return new Tile(engine, Assets.OBSIDIAN, id, true, Tile.TileType.STONE);

            case "water":
                return new TileAnimated(engine, new Animation(50, Assets.WATER), id, false, Tile.TileType.FLUID);
            case "lava":
                return new TileAnimated(engine, new Animation(50, Assets.LAVA), id, false, Tile.TileType.FLUID);

            case "planks":
                return new Tile(engine, Assets.PLANKS, id, true, Tile.TileType.WOOD);
            case "broken_stone":
                return new Tile(engine, Assets.BROKEN_STONE, id, true, Tile.TileType.STONE);

            case "air":
                return new Tile(engine, Assets.AIR, id, false, Tile.TileType.AIR);

            case "bookshelf":
                return new Tile(engine, Assets.BOOKSHELF, id, true, Tile.TileType.WOOD);
        }
        return getTile(engine, "air");
    }
}
