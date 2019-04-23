package coffeecatteam.theultimatetile.objs.tiles;

import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.start.TutLauncher;
import coffeecatteam.theultimatetile.objs.TileDataParser;
import coffeecatteam.theultimatetile.objs.tiles.stone.*;
import coffeecatteam.theultimatetile.objs.tiles.stone.ore.TileCoalOre;
import coffeecatteam.theultimatetile.objs.tiles.stone.ore.TileDiamondOre;
import coffeecatteam.theultimatetile.objs.tiles.stone.ore.TileGoldOre;
import coffeecatteam.theultimatetile.objs.tiles.stone.ore.TileIronOre;
import coffeecatteam.theultimatetile.objs.tiles.wood.TileBookshelf;
import coffeecatteam.theultimatetile.objs.tiles.wood.TileChest;
import coffeecatteam.theultimatetile.objs.tiles.wood.TileLog;
import coffeecatteam.theultimatetile.objs.tiles.wood.TilePlanks;
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
    public static Tile GRASS, DIRT;
    public static Tile SAND;

    /*
     * Stone
     */
    public static Tile STONE, BROKEN_STONE;
    public static Tile ANDESITE, DIORITE;
    public static Tile OBSIDIAN;

    public static Tile COAL_ORE, IRON_ORE, GOLD_ORE, DIAMOND_ORE;

    /*
     * Fluid
     */
    public static Tile WATER;
    private static Tile LAVA;

    /*
     * Wood
     */
    public static Tile PLANKS, LOG, CHEST;
    public static Tile BOOKSHELF;

    /*
     * Other
     */
    public static Tile AIR;
    public static Tile GLITCH;


    public static void init(TutEngine tutEngine) throws IOException, ParseException {
        TileDataParser parser = new TileDataParser();

        /*
         * Ground
         */
        register(GRASS = parser.loadData(new TileGrass(tutEngine)));
        register(DIRT = parser.loadData(new TileDirt(tutEngine)));
        register(SAND = parser.loadData(new TileSand(tutEngine)));

        /*
         * Stone
         */
        register(STONE = parser.loadData(new TileStone(tutEngine)));
        register(BROKEN_STONE = parser.loadData(new TileBrokenStone(tutEngine)));
        register(ANDESITE = parser.loadData(new TileAndesite(tutEngine)));
        register(DIORITE = parser.loadData(new TileDiorite(tutEngine)));
        register(OBSIDIAN = parser.loadData(new TileObsidian(tutEngine)));

        register(COAL_ORE = parser.loadData(new TileCoalOre(tutEngine)));
        register(IRON_ORE = parser.loadData(new TileIronOre(tutEngine)));
        register(GOLD_ORE = parser.loadData(new TileGoldOre(tutEngine)));
        register(DIAMOND_ORE = parser.loadData(new TileDiamondOre(tutEngine)));

        /*
         * Fluid
         */
        register(WATER = parser.loadData(new TileWater(tutEngine)));
        register(LAVA = parser.loadData(new TileLava(tutEngine)));

        /*
         * Wood
         */
        register(PLANKS = parser.loadData(new TilePlanks(tutEngine)));
        register(LOG = parser.loadData(new TileLog(tutEngine)));
        register(BOOKSHELF = parser.loadData(new TileBookshelf(tutEngine)));
        register(CHEST = parser.loadData(new TileChest(tutEngine)));

        /*
         * Other
         */
        register(AIR = parser.loadData(new TileAir(tutEngine)));
        register(GLITCH = parser.loadData(new TileGlitch(tutEngine)));

        TutLauncher.LOGGER.info("Tiles registered!");
    }

    private static void register(Tile tile) {
        TILES.put(tile.getId(), tile);
    }

    public static Tile getTileById(String id) {
        return TILES.get(id).newCopy();
    }

    public static List<Tile> getTiles() {
        return new ArrayList<>(TILES.values());
    }
}
