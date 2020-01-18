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
import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.start.MaewilLauncher;
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


    public static void init(MaewilEngine maewilEngine) throws IOException, ParseException {
        TileDataParser parser = new TileDataParser();

        /*
         * Ground
         */
        register(GRASS = parser.loadData(new GrassTile(maewilEngine)));
        register(DIRT = parser.loadData(new DirtTile(maewilEngine)));
        register(SAND = parser.loadData(new SandTile(maewilEngine)));
        register(RED_SAND = parser.loadData(new RedSandTile(maewilEngine)));

        /*
         * Stone
         */
        register(STONE = parser.loadData(new StoneTile(maewilEngine)));
        register(BROKEN_STONE = parser.loadData(new BrokenStoneTile(maewilEngine)));
        register(ANDESITE = parser.loadData(new AndesiteTile(maewilEngine)));
        register(DIORITE = parser.loadData(new DioriteTile(maewilEngine)));
        register(OBSIDIAN = parser.loadData(new ObsidianTile(maewilEngine)));

        register(COAL_ORE = parser.loadData(new CoalOreTile(maewilEngine)));
        register(IRON_ORE = parser.loadData(new IronOreTile(maewilEngine)));
        register(GOLD_ORE = parser.loadData(new GoldOreTile(maewilEngine)));
        register(DIAMOND_ORE = parser.loadData(new DiamondOreTile(maewilEngine)));

        /*
         * Fluid
         */
        register(WATER = parser.loadData(new WaterTile(maewilEngine)));
        register(LAVA = parser.loadData(new LavaTile(maewilEngine)));

        /*
         * Wood
         */
        register(PLANKS = parser.loadData(new PlanksTile(maewilEngine)));
        register(LOG = parser.loadData(new LogTile(maewilEngine)));
        register(BOOKSHELF = parser.loadData(new BookshelfTile(maewilEngine)));
        register(CHEST = parser.loadData(new ChestTile(maewilEngine)));

        /*
         * Other
         */
        register(AIR = parser.loadData(new AirTile(tutEngine)));

        MaewilLauncher.LOGGER.info("Tiles registered!");
    }

    private static void register(Tile tile) {
        Registries.TILES.register(new Identifier(tile.getId()), tile);
    }

    public static Tile getTileById(String id) {
        return Registries.TILES.get(new Identifier(id)).newCopy();
    }

}
