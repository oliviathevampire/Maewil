package io.github.vampirestudios.tdg.objs.tiles;

import io.github.vampirestudios.tdg.objs.TileDataParser;
import io.github.vampirestudios.tdg.objs.tiles.stone.*;
import io.github.vampirestudios.tdg.objs.tiles.stone.ore.CoalOreTile;
import io.github.vampirestudios.tdg.objs.tiles.stone.ore.DiamondOreTile;
import io.github.vampirestudios.tdg.objs.tiles.stone.ore.GoldOreTile;
import io.github.vampirestudios.tdg.objs.tiles.stone.ore.IronOreTile;
import io.github.vampirestudios.tdg.objs.tiles.wood.BookshelfTile;
import io.github.vampirestudios.tdg.objs.tiles.wood.ChestTile;
import io.github.vampirestudios.tdg.objs.tiles.wood.LogTile;
import io.github.vampirestudios.tdg.objs.tiles.wood.PlanksTile;
import io.github.vampirestudios.tdg.start.TutEngine;
import io.github.vampirestudios.tdg.start.TutLauncher;
import io.github.vampirestudios.tdg.utils.Identifier;
import io.github.vampirestudios.tdg.utils.registry.Registries;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Tiles {

    /*
     * Ground
     */
    public static Tile GRASS, DIRT;
    public static Tile SAND, RED_SAND;

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
        register(GRASS = parser.loadData(new GrassTile(tutEngine)));
        register(DIRT = parser.loadData(new DirtTile(tutEngine)));
        register(SAND = parser.loadData(new SandTile(tutEngine)));
        register(RED_SAND = parser.loadData(new RedSandTile(tutEngine)));

        /*
         * Stone
         */
        register(STONE = parser.loadData(new StoneTile(tutEngine)));
        register(BROKEN_STONE = parser.loadData(new BrokenStoneTile(tutEngine)));
        register(ANDESITE = parser.loadData(new AndesiteTile(tutEngine)));
        register(DIORITE = parser.loadData(new DioriteTile(tutEngine)));
        register(OBSIDIAN = parser.loadData(new ObsidianTile(tutEngine)));

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
        Registries.TILES.register(new Identifier(tile.getId()), tile);
    }

    public static Tile getTileById(String id) {
        return Registries.TILES.get(new Identifier(id)).newCopy();
    }

}
