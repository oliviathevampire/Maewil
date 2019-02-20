package coffeecatteam.theultimatetile.tile;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.manager.ItemManager;
import coffeecatteam.theultimatetile.tile.tiles.*;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Tiles {

    private static final HashMap<String, Tile> TILES = new HashMap<>();

    /*
     * Ground
     */
    public static TileGrass GRASS;
    public static TileDirt DIRT;
    public static TileSand SAND;

    /*
     * Stone
     */
    public static TileStone STONE;
    public static TileBrokenStone BROKEN_STONE;
    public static TileAndesite ANDESITE;
    public static TileDiorite DIORITE;
    public static TileObsidian OBSIDIAN;

    public static TileOre COAL_ORE, IRON_ORE, GOLD_ORE, DIAMOND_ORE;

    /*
     * Fluid
     */
    public static TileWater WATER;
    private static TileLava LAVA;

    /*
     * Wood
     */
    public static TileWood PLANKS, BOOKSHELF, CHEST;

    /*
     * Other
     */
    public static TileAir AIR;
    public static TileGlitch GLITCH;


    public static void init(TutEngine tutEngine) {
        try {
            /*
             * Ground
             */
            register(GRASS = (TileGrass) TileDataParser.loadTileData(new TileGrass(tutEngine)));
            register(DIRT = (TileDirt) TileDataParser.loadTileData(new TileDirt(tutEngine)));
            register(SAND = (TileSand) TileDataParser.loadTileData(new TileSand(tutEngine)));

            /*
             * Stone
             */
            register(STONE = (TileStone) TileDataParser.loadTileData(new TileStone(tutEngine)));
            register(BROKEN_STONE = (TileBrokenStone) TileDataParser.loadTileData(new TileBrokenStone(tutEngine)));
            register(ANDESITE = (TileAndesite) TileDataParser.loadTileData(new TileAndesite(tutEngine)));
            register(DIORITE = (TileDiorite) TileDataParser.loadTileData(new TileDiorite(tutEngine)));
            register(OBSIDIAN = (TileObsidian) TileDataParser.loadTileData(new TileObsidian(tutEngine)));

            register(COAL_ORE = (TileOre) TileDataParser.loadTileData(new TileOre(tutEngine, "coal_ore", ItemManager.COAL)));
            register(IRON_ORE = (TileOre) TileDataParser.loadTileData(new TileOre(tutEngine, "iron_ore", ItemManager.IRON_INGOT)));
            register(GOLD_ORE = (TileOre) TileDataParser.loadTileData(new TileOre(tutEngine, "gold_ore", ItemManager.GOLD_INGOT)));
            register(DIAMOND_ORE = (TileOre) TileDataParser.loadTileData(new TileOre(tutEngine, "diamond_ore", ItemManager.DIAMOND)));

            /*
             * Fluid
             */
            register(WATER = (TileWater) TileDataParser.loadTileData(new TileWater(tutEngine)));
            register(LAVA = (TileLava) TileDataParser.loadTileData(new TileLava(tutEngine)));

            /*
             * Wood
             */
            register(PLANKS = (TileWood) TileDataParser.loadTileData(new TileWood(tutEngine, "planks")));
            register(BOOKSHELF = (TileWood) TileDataParser.loadTileData(new TileWood(tutEngine, "bookshelf")));
            register(CHEST = (TileWood) TileDataParser.loadTileData(new TileWood(tutEngine, "chest")));

            /*
             * Other
             */
            register(AIR = (TileAir) TileDataParser.loadTileData(new TileAir(tutEngine)));
            register(GLITCH = (TileGlitch) TileDataParser.loadTileData(new TileGlitch(tutEngine)));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private static void register(Tile tile) {
        TILES.put(tile.getId(), tile);
    }

    public static Tile getTile(String id) {
        return TILES.get(id).newTile();
    }

    public static List<Tile> getTiles() {
        return new ArrayList<>(TILES.values());
    }
}
