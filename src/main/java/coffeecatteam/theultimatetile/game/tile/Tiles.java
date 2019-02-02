package coffeecatteam.theultimatetile.game.tile;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.tile.tiles.*;
import coffeecatteam.theultimatetile.game.world.colormap.WorldColors;
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

    public static TileOre COAL_ORE, IRON_ORE, GOLD_ORE, DIAMOND_ORE;

    public static TileStone OBSIDIAN;

    public static TileWater WATER;
    private static TileLava LAVA;

    public static TileWood PLANKS;
    public static TileBrokenStone BROKEN_STONE;

    public static TileAir AIR; // USES EMPTY SPACE IN TILES SHEET

    public static TileWood BOOKSHELF;
    public static TileGlitch GLITCH;

    public static void init(Engine engine) {
        register(GRASS = (TileGrass) getTile(engine, "grass").setMapColor(WorldColors.GRASS));
        register(DIRT = (TileDirt) getTile(engine, "dirt").setMapColor(WorldColors.DIRT));
        register(STONE = (TileStone) getTile(engine, "stone").setMapColor(WorldColors.STONE));
        register(SAND = (TileSand) getTile(engine, "sand").setMapColor(WorldColors.SAND));
        register(ANDESITE = (TileStone) getTile(engine, "andesite").setMapColor(WorldColors.STONE));
        register(DIORITE = (TileStone) getTile(engine, "diorite").setMapColor(WorldColors.STONE));

        register(COAL_ORE = (TileOre) getTile(engine, "coal_ore").setMapColor(WorldColors.STONE));
        register(IRON_ORE = (TileOre) getTile(engine, "iron_ore").setMapColor(WorldColors.STONE));
        register(GOLD_ORE = (TileOre) getTile(engine, "gold_ore").setMapColor(WorldColors.STONE));
        register(DIAMOND_ORE = (TileOre) getTile(engine, "diamond_ore").setMapColor(WorldColors.STONE));

        register(OBSIDIAN = (TileStone) getTile(engine, "obsidian").setMapColor(WorldColors.STONE));

        register(WATER = (TileWater) getTile(engine, "water").setMapColor(WorldColors.OCEAN));
        register(LAVA = (TileLava) getTile(engine, "lava").setMapColor(WorldColors.LAVA));

        register(PLANKS = (TileWood) getTile(engine, "planks").setMapColor(WorldColors.WOOD));
        register(BROKEN_STONE = (TileBrokenStone) getTile(engine, "broken_stone").setMapColor(WorldColors.STONE));

        register(AIR = (TileAir) getTile(engine, "air").setMapColor(WorldColors.AIR));

        register(BOOKSHELF = (TileWood) getTile(engine, "bookshelf").setMapColor(WorldColors.WOOD));
        register(GLITCH = (TileGlitch) getTile(engine, "glitch").setMapColor(WorldColors.GRASS));
    }

    private static void register(Tile tile) {
        TILES.add(tile);
    }

    public static Tile getTile(Engine engine, String id) {
        switch (id) {
            case "grass":
                return new TileGrass(engine, id);
            case "dirt":
                return new TileDirt(engine, id);

            case "stone":
                return new TileStoneAlt(engine, id);
            case "sand":
                return new TileSand(engine, id);
            case "andesite":
                return new TileAndesite(engine, id);
            case "diorite":
                return new TileDiorite(engine, id);

            case "coal_ore":
                return new TileOre(engine, Assets.COAL_ORE, id, ItemManager.COAL);
            case "iron_ore":
                return new TileOre(engine, Assets.IRON_ORE, id, ItemManager.IRON_INGOT);
            case "gold_ore":
                return new TileOre(engine, Assets.GOLD_ORE, id, ItemManager.GOLD_INGOT);
            case "diamond_ore":
                return new TileOre(engine, Assets.DIAMOND_ORE, id, ItemManager.DIAMOND);

            case "obsidian":
                return new TileObsidian(engine, id);

            case "water":
                return new TileWater(engine, id);
            case "lava":
                return new TileLava(engine, id);

            case "planks":
                return new TileWood(engine, Assets.PLANKS, id);
            case "broken_stone":
                return new TileBrokenStone(engine, id);

            default:
            case "air":
                return new TileAir(engine);

            case "bookshelf":
                return new TileWood(engine, Assets.BOOKSHELF, id);
            case "glitch":
                return new TileGlitch(engine, id);
        }
    }
}
