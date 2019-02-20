package coffeecatteam.theultimatetile.tile;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.manager.ItemManager;
import coffeecatteam.theultimatetile.tile.tiles.*;

import java.util.HashMap;

public class Tiles {

    private static TutEngine tutEngine;
    public static final HashMap<String, Tile> TILES = new HashMap<>();

    public static TileGrass GRASS;
    public static TileDirt DIRT;

    public static TileStone STONE;
    public static TileSand SAND;
    public static TileAndesite ANDESITE;
    public static TileDiorite DIORITE;

    public static TileOre COAL_ORE, IRON_ORE, GOLD_ORE, DIAMOND_ORE;

    public static TileObsidian OBSIDIAN;

    public static TileWater WATER;
    private static TileLava LAVA;

    public static TileWood PLANKS;
    public static TileBrokenStone BROKEN_STONE;

    public static TileAir AIR; // USES EMPTY SPACE IN TILES SHEET

    public static TileWood BOOKSHELF;
    public static TileGlitch GLITCH;

    public static TileWood CHEST;

    public static void init(TutEngine tutEngineIn) {
        tutEngine = tutEngineIn;

        register(GRASS = (TileGrass) TileDataParser.loadTileData("grass"));
        register(DIRT = (TileDirt) TileDataParser.loadTileData("dirt"));

        register(STONE = (TileStone) TileDataParser.loadTileData("stone"));
        register(SAND = (TileSand) TileDataParser.loadTileData("sand"));
        register(ANDESITE = (TileAndesite) TileDataParser.loadTileData("andesite"));
        register(DIORITE = (TileDiorite) TileDataParser.loadTileData("diorite"));

        register(COAL_ORE = (TileOre) TileDataParser.loadTileData("coal_ore"));
        register(IRON_ORE = (TileOre) TileDataParser.loadTileData("iron_ore"));
        register(GOLD_ORE = (TileOre) TileDataParser.loadTileData("gold_ore"));
        register(DIAMOND_ORE = (TileOre) TileDataParser.loadTileData("diamond_ore"));

        register(OBSIDIAN = (TileObsidian) TileDataParser.loadTileData("obsidian"));

        register(WATER = (TileWater) TileDataParser.loadTileData("water"));
        register(LAVA = (TileLava) TileDataParser.loadTileData("lava"));

        register(PLANKS = (TileWood) TileDataParser.loadTileData("planks"));
        register(BROKEN_STONE = (TileBrokenStone) TileDataParser.loadTileData("broken_stone"));

        register(AIR = (TileAir) TileDataParser.loadTileData("air"));

        register(BOOKSHELF = (TileWood) TileDataParser.loadTileData("bookshelf"));
        register(GLITCH = (TileGlitch) TileDataParser.loadTileData("glitch"));

        register(CHEST = (TileWood) TileDataParser.loadTileData("chest"));
    }

    private static void register(Tile tile) {
        TILES.put(tile.getId(), tile);
    }

    public static Tile getTile(String id) {
        switch (id) {
            case "grass":
                return new TileGrass(tutEngine);
            case "dirt":
                return new TileDirt(tutEngine);

            case "stone":
                return new TileStone(tutEngine);
            case "sand":
                return new TileSand(tutEngine);
            case "andesite":
                return new TileAndesite(tutEngine);
            case "diorite":
                return new TileDiorite(tutEngine);

            case "coal_ore":
                return new TileOre(tutEngine, id, ItemManager.COAL);
            case "iron_ore":
                return new TileOre(tutEngine, id, ItemManager.IRON_INGOT);
            case "gold_ore":
                return new TileOre(tutEngine, id, ItemManager.GOLD_INGOT);
            case "diamond_ore":
                return new TileOre(tutEngine, id, ItemManager.DIAMOND);

            case "obsidian":
                return new TileObsidian(tutEngine);

            case "water":
                return new TileWater(tutEngine);
            case "lava":
                return new TileLava(tutEngine);

            case "planks":
                return new TileWood(tutEngine, id);
            case "broken_stone":
                return new TileBrokenStone(tutEngine);

            case "air":
                return new TileAir(tutEngine);

            case "bookshelf":
                return new TileWood(tutEngine, id);
            default:
            case "glitch":
                return new TileGlitch(tutEngine);

            case "chest":
                return new TileWood(tutEngine, id);
        }
    }
}
