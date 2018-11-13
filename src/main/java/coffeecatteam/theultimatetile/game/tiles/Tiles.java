package coffeecatteam.theultimatetile.game.tiles;

import coffeecatteam.theultimatetile.game.GameEngine;
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

    public static void init(GameEngine gameEngine) {
        register(GRASS = getTile(gameEngine, "grass"));
        register(DIRT = getTile(gameEngine, "dirt"));
        register(STONE = getTile(gameEngine, "stone"));
        register(SAND = getTile(gameEngine, "sand"));
        register(ANDESITE = getTile(gameEngine, "andesite"));
        register(DIORITE = getTile(gameEngine, "diorite"));

        register(COAL_ORE = getTile(gameEngine, "coal_ore"));
        register(IRON_ORE = getTile(gameEngine, "iron_ore"));
        register(GOLD_ORE = getTile(gameEngine, "gold_ore"));
        register(DIAMOND_ORE = getTile(gameEngine, "diamond_ore"));

        register(OBSIDIAN = getTile(gameEngine, "obsidian"));

        register(WATER = getTile(gameEngine, "water"));
        register(LAVA = getTile(gameEngine, "lava"));

        register(PLANKS = getTile(gameEngine, "planks"));
        register(BROKEN_STONE = getTile(gameEngine, "broken_stone"));

        AIR = getTile(gameEngine, "air");

        register(BOOKSHELF = getTile(gameEngine, "bookshelf"));
    }

    private static void register(Tile tile) {
        TILES.add(tile);
    }

    public static Tile getTile(GameEngine gameEngine, String id) {
        switch (id) {
            case "grass":
                return new Tile(gameEngine, Assets.GRASS, id, false, Tile.TileType.GROUND);
            case "dirt":
                return new Tile(gameEngine, Assets.DIRT, id, false, Tile.TileType.GROUND);
            case "stone":
                return new TileBreakable(gameEngine, Assets.STONE, id, ItemManager.ROCK, Tile.TileType.STONE);
            case "sand":
                return new Tile(gameEngine, Assets.SAND, id, false, Tile.TileType.GROUND);
            case "andesite":
                return new Tile(gameEngine, Assets.ANDESITE, id, true, Tile.TileType.STONE);
            case "diorite":
                return new Tile(gameEngine, Assets.DIORITE, id, true, Tile.TileType.STONE);

            case "coal_ore":
                return new TileBreakable(gameEngine, Assets.COAL_ORE, id, ItemManager.COAL, Tile.TileType.STONE);
            case "iron_ore":
                return new TileBreakable(gameEngine, Assets.IRON_ORE, id, ItemManager.IRON_INGOT, Tile.TileType.STONE);
            case "gold_ore":
                return new TileBreakable(gameEngine, Assets.GOLD_ORE, id, ItemManager.GOLD_INGOT, Tile.TileType.STONE);
            case "diamond_ore":
                return new TileBreakable(gameEngine, Assets.DIAMOND_ORE, id, ItemManager.DIAMOND, Tile.TileType.STONE);

            case "obsidian":
                return new Tile(gameEngine, Assets.OBSIDIAN, id, true, Tile.TileType.STONE);

            case "water":
                return new TileAnimated(gameEngine, new Animation(50, Assets.WATER), id, false, Tile.TileType.FLUID);
            case "lava":
                return new TileAnimated(gameEngine, new Animation(50, Assets.LAVA), id, false, Tile.TileType.FLUID);

            case "planks":
                return new Tile(gameEngine, Assets.PLANKS, id, true, Tile.TileType.WOOD);
            case "broken_stone":
                return new Tile(gameEngine, Assets.BROKEN_STONE, id, true, Tile.TileType.STONE);

            case "air":
                return new Tile(gameEngine, Assets.AIR, id, false, Tile.TileType.AIR);

            case "bookshelf":
                return new Tile(gameEngine, Assets.BOOKSHELF, id, true, Tile.TileType.WOOD);
        }
        return getTile(gameEngine, "air");
    }
}
