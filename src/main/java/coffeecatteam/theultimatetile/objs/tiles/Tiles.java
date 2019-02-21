package coffeecatteam.theultimatetile.objs.tiles;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.objs.items.Items;
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
    public static TileWood PLANKS, CHEST;
    public static TileBookshelf BOOKSHELF;

    /*
     * Other
     */
    public static TileAir AIR;
    public static TileGlitch GLITCH;


    public static void init(TutEngine tutEngine) throws IOException, ParseException {
        /*
         * Ground
         */
        register(GRASS = TileDataParser.loadTileData(new TileGrass(tutEngine)));
        register(DIRT = TileDataParser.loadTileData(new TileDirt(tutEngine)));
        register(SAND = TileDataParser.loadTileData(new TileSand(tutEngine)));

        /*
         * Stone
         */
        register(STONE = TileDataParser.loadTileData(new TileStone(tutEngine)));
        register(BROKEN_STONE = TileDataParser.loadTileData(new TileBrokenStone(tutEngine)));
        register(ANDESITE = TileDataParser.loadTileData(new TileAndesite(tutEngine)));
        register(DIORITE = TileDataParser.loadTileData(new TileDiorite(tutEngine)));
        register(OBSIDIAN = TileDataParser.loadTileData(new TileObsidian(tutEngine)));

        register(COAL_ORE = TileDataParser.loadTileData(new TileOre(tutEngine, "coal_ore", Items.COAL)));
        register(IRON_ORE = TileDataParser.loadTileData(new TileOre(tutEngine, "iron_ore", Items.IRON_INGOT)));
        register(GOLD_ORE = TileDataParser.loadTileData(new TileOre(tutEngine, "gold_ore", Items.GOLD_INGOT)));
        register(DIAMOND_ORE = TileDataParser.loadTileData(new TileOre(tutEngine, "diamond_ore", Items.DIAMOND)));

        /*
         * Fluid
         */
        register(WATER = TileDataParser.loadTileData(new TileWater(tutEngine)));
        register(LAVA = TileDataParser.loadTileData(new TileLava(tutEngine)));

        /*
         * Wood
         */
        register(PLANKS = TileDataParser.loadTileData(new TileWood(tutEngine, "planks")));
        register(BOOKSHELF = TileDataParser.loadTileData(new TileBookshelf(tutEngine)));
        register(CHEST = TileDataParser.loadTileData(new TileWood(tutEngine, "chest")));

        /*
         * Other
         */
        register(AIR = TileDataParser.loadTileData(new TileAir(tutEngine)));
        register(GLITCH = TileDataParser.loadTileData(new TileGlitch(tutEngine)));
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
