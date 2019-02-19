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

    public static void init(TutEngine tutEngineIn) {
        tutEngine = tutEngineIn;

        register(GRASS = (TileGrass) TileDataParser.loadTileData(tutEngineIn, "grass"));
        register(DIRT = (TileDirt) TileDataParser.loadTileData(tutEngineIn, "dirt"));

        register(STONE = (TileStone) TileDataParser.loadTileData(tutEngineIn, "stone"));
        register(SAND = (TileSand) TileDataParser.loadTileData(tutEngineIn, "sand"));
        register(ANDESITE = (TileAndesite) TileDataParser.loadTileData(tutEngineIn, "andesite"));
        register(DIORITE = (TileDiorite) TileDataParser.loadTileData(tutEngineIn, "diorite"));

        register(COAL_ORE = (TileOre) TileDataParser.loadTileData(tutEngineIn, "coal_ore"));
        register(IRON_ORE = (TileOre) TileDataParser.loadTileData(tutEngineIn, "iron_ore"));
        register(GOLD_ORE = (TileOre) TileDataParser.loadTileData(tutEngineIn, "gold_ore"));
        register(DIAMOND_ORE = (TileOre) TileDataParser.loadTileData(tutEngineIn, "diamond_ore"));

        register(OBSIDIAN = (TileObsidian) TileDataParser.loadTileData(tutEngineIn, "obsidian"));

        register(WATER = (TileWater) TileDataParser.loadTileData(tutEngineIn, "water"));
        register(LAVA = (TileLava) TileDataParser.loadTileData(tutEngineIn, "lava"));

        register(PLANKS = (TileWood) TileDataParser.loadTileData(tutEngineIn, "planks"));
        register(BROKEN_STONE = (TileBrokenStone) TileDataParser.loadTileData(tutEngineIn, "broken_stone"));

        register(AIR = (TileAir) TileDataParser.loadTileData(tutEngineIn, "air"));

        register(BOOKSHELF = (TileWood) TileDataParser.loadTileData(tutEngineIn, "bookshelf"));
        register(GLITCH = (TileGlitch) TileDataParser.loadTileData(tutEngineIn, "glitch"));
    }

    private static void register(Tile tile) {
        TILES.put(tile.getId(), tile);
    }

    public static Tile getTile(String id) {
        return (Tile) getTile(id, false);
    }

    public static Class<? extends Tile> getTileClass(String id) {
        return (Class<? extends Tile>) getTile(id, true);
    }

    private static Object getTile(String id, boolean clazz) {
        switch (id) {
            case "grass":
                return clazz ? TileGrass.class : new TileGrass(tutEngine);
            case "dirt":
                return clazz ? TileDirt.class : new TileDirt(tutEngine);

            case "stone":
                return clazz ? TileStone.class : new TileStone(tutEngine);
            case "sand":
                return clazz ? TileSand.class : new TileSand(tutEngine);
            case "andesite":
                return clazz ? TileAndesite.class : new TileAndesite(tutEngine);
            case "diorite":
                return clazz ? TileDiorite.class : new TileDiorite(tutEngine);

            case "coal_ore":
                return clazz ? TileOre.class : new TileOre(tutEngine, id, ItemManager.COAL);
            case "iron_ore":
                return clazz ? TileOre.class : new TileOre(tutEngine, id, ItemManager.IRON_INGOT);
            case "gold_ore":
                return clazz ? TileOre.class : new TileOre(tutEngine, id, ItemManager.GOLD_INGOT);
            case "diamond_ore":
                return clazz ? TileOre.class : new TileOre(tutEngine, id, ItemManager.DIAMOND);

            case "obsidian":
                return clazz ? TileObsidian.class : new TileObsidian(tutEngine);

            case "water":
                return clazz ? TileWater.class : new TileWater(tutEngine);
            case "lava":
                return clazz ? TileLava.class : new TileLava(tutEngine);

            case "planks":
                return clazz ? TileWood.class : new TileWood(tutEngine, id);
            case "broken_stone":
                return clazz ? TileBrokenStone.class : new TileBrokenStone(tutEngine);

            case "air":
                return clazz ? TileAir.class : new TileAir(tutEngine);

            case "bookshelf":
                return clazz ? TileWood.class : new TileWood(tutEngine, id);
            default:
            case "glitch":
                return clazz ? TileGlitch.class : new TileGlitch(tutEngine);
        }
    }
}
