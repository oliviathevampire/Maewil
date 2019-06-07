package coffeecatteam.theultimatetile.objs.tiles;

import coffeecatteam.theultimatetile.objs.TileDataParser;
import coffeecatteam.theultimatetile.objs.tiles.stone.*;
import coffeecatteam.theultimatetile.objs.tiles.stone.ore.CoalOreTile;
import coffeecatteam.theultimatetile.objs.tiles.stone.ore.DiamondOreTile;
import coffeecatteam.theultimatetile.objs.tiles.stone.ore.GoldOreTile;
import coffeecatteam.theultimatetile.objs.tiles.stone.ore.IronOreTile;
import coffeecatteam.theultimatetile.objs.tiles.wood.BookshelfTile;
import coffeecatteam.theultimatetile.objs.tiles.wood.ChestTile;
import coffeecatteam.theultimatetile.objs.tiles.wood.LogTile;
import coffeecatteam.theultimatetile.objs.tiles.wood.PlanksTile;
import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.start.TutLauncher;
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
    public static Tile STONE_BRICKS, STONE_TILE;

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
        register(GRASS = parser.loadData(new GrassTile(tutEngine)));
        register(DIRT = parser.loadData(new DirtTile(tutEngine)));
        register(SAND = parser.loadData(new SandTile(tutEngine)));

        /*
         * Stone
         */
        register(STONE = parser.loadData(new StoneTile(tutEngine)));
        register(BROKEN_STONE = parser.loadData(new BrokenStoneTile(tutEngine)));
        register(ANDESITE = parser.loadData(new AndesiteTile(tutEngine)));
        register(DIORITE = parser.loadData(new DioriteTile(tutEngine)));
        register(OBSIDIAN = parser.loadData(new ObsidianTile(tutEngine)));
        register(STONE_BRICKS = parser.loadData(new StoneBricksTile(tutEngine)));
        register(STONE_TILE = parser.loadData(new StoneTilesTile(tutEngine)));

        register(COAL_ORE = parser.loadData(new CoalOreTile(tutEngine)));
        register(IRON_ORE = parser.loadData(new IronOreTile(tutEngine)));
        register(GOLD_ORE = parser.loadData(new GoldOreTile(tutEngine)));
        register(DIAMOND_ORE = parser.loadData(new DiamondOreTile(tutEngine)));

        /*
         * Fluid
         */
        register(WATER = parser.loadData(new WaterTile(tutEngine)));
        register(LAVA = parser.loadData(new LavaTile(tutEngine)));

        /*
         * Wood
         */
        register(PLANKS = parser.loadData(new PlanksTile(tutEngine)));
        register(LOG = parser.loadData(new LogTile(tutEngine)));
        register(BOOKSHELF = parser.loadData(new BookshelfTile(tutEngine)));
        register(CHEST = parser.loadData(new ChestTile(tutEngine)));

        /*
         * Other
         */
        register(AIR = parser.loadData(new AirTile(tutEngine)));
        register(GLITCH = parser.loadData(new GlitchTile(tutEngine)));

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
