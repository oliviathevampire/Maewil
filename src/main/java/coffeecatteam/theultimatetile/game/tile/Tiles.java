package coffeecatteam.theultimatetile.game.tile;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.tile.tiles.*;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.manager.ItemManager;

import java.util.ArrayList;
import java.util.List;

public class Tiles {

    public static final List<Tile> TILES = new ArrayList<>();

    public static TileGrass GRASS;
    public static TileDirt DIRT;
    public static TileStone STONE;
    public static TileSand SAND;
    public static Tile ANDESITE;
    public static Tile DIORITE;

    public static TileStone COAL_ORE, IRON_ORE, GOLD_ORE, DIAMOND_ORE;

    public static Tile OBSIDIAN;

    public static TileWater WATER;
    private static TileAnimated LAVA;

    public static Tile PLANKS;
    public static Tile BROKEN_STONE;

    public static TileAir AIR; // USES EMPTY SPACE IN TILES SHEET

    public static Tile BOOKSHELF;

    public static void init(Engine engine) {
        register(GRASS = (TileGrass) getTile(engine, "grass"));
        register(DIRT = (TileDirt) getTile(engine, "dirt"));
        register(STONE = (TileStone) getTile(engine, "stone"));
        register(SAND = (TileSand) getTile(engine, "sand"));
        register(ANDESITE = getTile(engine, "andesite"));
        register(DIORITE = getTile(engine, "diorite"));

        register(COAL_ORE = (TileStone) getTile(engine, "coal_ore"));
        register(IRON_ORE = (TileStone) getTile(engine, "iron_ore"));
        register(GOLD_ORE = (TileStone) getTile(engine, "gold_ore"));
        register(DIAMOND_ORE = (TileStone) getTile(engine, "diamond_ore"));

        register(OBSIDIAN = getTile(engine, "obsidian"));

        register(WATER = (TileWater) getTile(engine, "water"));
        register(LAVA = (TileAnimated) getTile(engine, "lava"));

        register(PLANKS = getTile(engine, "planks"));
        register(BROKEN_STONE = getTile(engine, "broken_stone"));

        AIR = (TileAir) getTile(engine, "air");

        register(BOOKSHELF = getTile(engine, "bookshelf"));
    }

    private static void register(Tile tile) {
        TILES.add(tile);
    }

    public static Tile getTile(Engine engine, String id) {
        switch (id) {
            case "grass":
                return new TileGrass(engine, id);
            case "dirt":
                return new TileDirt(engine, id, false, Tile.TileType.GROUND);
            case "stone":
                return new TileStone(engine, Assets.STONE, id, ItemManager.ROCK, Tile.TileType.STONE);
            case "sand":
                return new TileSand(engine, id);
            case "andesite":
                return new Tile(engine, Assets.ANDESITE, id, true, Tile.TileType.STONE);
            case "diorite":
                return new Tile(engine, Assets.DIORITE, id, true, Tile.TileType.STONE);

            case "coal_ore":
                return new TileStone(engine, Assets.COAL_ORE, id, ItemManager.COAL, Tile.TileType.STONE);
            case "iron_ore":
                return new TileStone(engine, Assets.IRON_ORE, id, ItemManager.IRON_INGOT, Tile.TileType.STONE);
            case "gold_ore":
                return new TileStone(engine, Assets.GOLD_ORE, id, ItemManager.GOLD_INGOT, Tile.TileType.STONE);
            case "diamond_ore":
                return new TileStone(engine, Assets.DIAMOND_ORE, id, ItemManager.DIAMOND, Tile.TileType.STONE);

            case "obsidian":
                return new Tile(engine, Assets.OBSIDIAN, id, true, Tile.TileType.STONE);

            case "water":
                return new TileWater(engine, id, false, Tile.TileType.FLUID);
            case "lava":
                return new TileAnimated(engine, new Animation(50, Assets.LAVA), id, false, Tile.TileType.FLUID);

            case "planks":
                return new Tile(engine, Assets.PLANKS, id, true, Tile.TileType.WOOD);
            case "broken_stone":
                return new Tile(engine, Assets.BROKEN_STONE, id, true, Tile.TileType.STONE);

            case "air":
                return new TileAir(engine, Assets.AIR, id, false, Tile.TileType.AIR);

            case "bookshelf":
                return new Tile(engine, Assets.BOOKSHELF, id, true, Tile.TileType.WOOD);
        }
        return getTile(engine, "air");
    }
}
