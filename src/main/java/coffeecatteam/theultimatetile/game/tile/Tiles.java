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
    public static TileStone ANDESITE;
    public static TileStone DIORITE;

    public static TileStone COAL_ORE, IRON_ORE, GOLD_ORE, DIAMOND_ORE;

    public static TileStone OBSIDIAN;

    public static TileWater WATER;
    private static TileLava LAVA;

    public static TileWood PLANKS;
    public static TileStone BROKEN_STONE;

    public static TileAir AIR; // USES EMPTY SPACE IN TILES SHEET

    public static TileWood BOOKSHELF;

    public static void init(Engine engine) {
        register(GRASS = (TileGrass) getTile(engine, "grass"));
        register(DIRT = (TileDirt) getTile(engine, "dirt"));
        register(STONE = (TileStone) getTile(engine, "stone"));
        register(SAND = (TileSand) getTile(engine, "sand"));
        register(ANDESITE = (TileStone) getTile(engine, "andesite"));
        register(DIORITE = (TileStone) getTile(engine, "diorite"));

        register(COAL_ORE = (TileStone) getTile(engine, "coal_ore"));
        register(IRON_ORE = (TileStone) getTile(engine, "iron_ore"));
        register(GOLD_ORE = (TileStone) getTile(engine, "gold_ore"));
        register(DIAMOND_ORE = (TileStone) getTile(engine, "diamond_ore"));

        register(OBSIDIAN = (TileStone) getTile(engine, "obsidian"));

        register(WATER = (TileWater) getTile(engine, "water"));
        register(LAVA = (TileLava) getTile(engine, "lava"));

        register(PLANKS = (TileWood) getTile(engine, "planks"));
        register(BROKEN_STONE = (TileStone) getTile(engine, "broken_stone"));

        AIR = (TileAir) getTile(engine, "air");

        register(BOOKSHELF = (TileWood) getTile(engine, "bookshelf"));
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
                return new TileStone(engine, Assets.STONE, id, ItemManager.ROCK);
            case "sand":
                return new TileSand(engine, id);
            case "andesite":
                return new TileStone(engine, Assets.ANDESITE, id);
            case "diorite":
                return new TileStone(engine, Assets.DIORITE, id);

            case "coal_ore":
                return new TileStone(engine, Assets.COAL_ORE, id, ItemManager.COAL);
            case "iron_ore":
                return new TileStone(engine, Assets.IRON_ORE, id, ItemManager.IRON_INGOT);
            case "gold_ore":
                return new TileStone(engine, Assets.GOLD_ORE, id, ItemManager.GOLD_INGOT);
            case "diamond_ore":
                return new TileStone(engine, Assets.DIAMOND_ORE, id, ItemManager.DIAMOND);

            case "obsidian":
                return new TileStone(engine, Assets.OBSIDIAN, id);

            case "water":
                return new TileWater(engine, id, false, Tile.TileType.FLUID);
            case "lava":
                return new TileLava(engine, new Animation(50, Assets.LAVA), id);

            case "planks":
                return new TileWood(engine, Assets.PLANKS, id);
            case "broken_stone":
                return new TileStone(engine, Assets.BROKEN_STONE, id, ItemManager.ROCK);

            case "air":
                return new TileAir(engine);

            case "bookshelf":
                return new TileWood(engine, Assets.BOOKSHELF, id);
        }
        return getTile(engine, "air");
    }
}
