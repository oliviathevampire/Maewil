package coffeecatteam.theultimatetile.tiles;

import coffeecatteam.theultimatetile.GameEngine;
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

    public static void init(GameEngine gameEngine) {
        GRASS = getTile(gameEngine, "grass");
        DIRT = getTile(gameEngine, "dirt");
        STONE = getTile(gameEngine, "stone");
        SAND = getTile(gameEngine, "sand");
        ANDESITE = getTile(gameEngine, "andesite");
        DIORITE = getTile(gameEngine, "diorite");

        COAL_ORE = getTile(gameEngine, "coal_ore");
        IRON_ORE = getTile(gameEngine, "iron_ore");
        GOLD_ORE = getTile(gameEngine, "gold_ore");
        DIAMOND_ORE = getTile(gameEngine, "diamond_ore");

        OBSIDIAN = getTile(gameEngine, "obsidian");

        WATER = getTile(gameEngine, "water");
        LAVA = getTile(gameEngine, "lava");

        PLANKS = getTile(gameEngine, "planks");
        BROKEN_STONE = getTile(gameEngine, "broken_stone");

        AIR = getTile(gameEngine, "air");

        BOOKSHELF = getTile(gameEngine, "bookshelf");
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
