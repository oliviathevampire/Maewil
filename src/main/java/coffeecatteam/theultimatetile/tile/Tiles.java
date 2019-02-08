package coffeecatteam.theultimatetile.tile;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.tile.tiles.*;
import coffeecatteam.theultimatetile.world.colormap.WorldColors;
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

    public static void init(TutEngine tutEngine) {
        register(GRASS = (TileGrass) getTile(tutEngine, "grass").setMapColor(WorldColors.GRASS));
        register(DIRT = (TileDirt) getTile(tutEngine, "dirt").setMapColor(WorldColors.DIRT));
        register(STONE = (TileStone) getTile(tutEngine, "stone").setMapColor(WorldColors.STONE));
        register(SAND = (TileSand) getTile(tutEngine, "sand").setMapColor(WorldColors.SAND));
        register(ANDESITE = (TileStone) getTile(tutEngine, "andesite").setMapColor(WorldColors.STONE));
        register(DIORITE = (TileStone) getTile(tutEngine, "diorite").setMapColor(WorldColors.STONE));

        register(COAL_ORE = (TileOre) getTile(tutEngine, "coal_ore").setMapColor(WorldColors.STONE));
        register(IRON_ORE = (TileOre) getTile(tutEngine, "iron_ore").setMapColor(WorldColors.STONE));
        register(GOLD_ORE = (TileOre) getTile(tutEngine, "gold_ore").setMapColor(WorldColors.STONE));
        register(DIAMOND_ORE = (TileOre) getTile(tutEngine, "diamond_ore").setMapColor(WorldColors.STONE));

        register(OBSIDIAN = (TileStone) getTile(tutEngine, "obsidian").setMapColor(WorldColors.STONE));

        register(WATER = (TileWater) getTile(tutEngine, "water").setMapColor(WorldColors.OCEAN));
        register(LAVA = (TileLava) getTile(tutEngine, "lava").setMapColor(WorldColors.LAVA));

        register(PLANKS = (TileWood) getTile(tutEngine, "planks").setMapColor(WorldColors.WOOD));
        register(BROKEN_STONE = (TileBrokenStone) getTile(tutEngine, "broken_stone").setMapColor(WorldColors.STONE));

        register(AIR = (TileAir) getTile(tutEngine, "air").setMapColor(WorldColors.AIR));

        register(BOOKSHELF = (TileWood) getTile(tutEngine, "bookshelf").setMapColor(WorldColors.WOOD));
        register(GLITCH = (TileGlitch) getTile(tutEngine, "glitch").setMapColor(WorldColors.GRASS));
    }

    private static void register(Tile tile) {
        TILES.add(tile);
    }

    public static Tile getTile(TutEngine tutEngine, String id) {
        switch (id) {
            case "grass":
                return new TileGrass(tutEngine, id);
            case "dirt":
                return new TileDirt(tutEngine, id);

            case "stone":
                return new TileStoneAlt(tutEngine, id);
            case "sand":
                return new TileSand(tutEngine, id);
            case "andesite":
                return new TileAndesite(tutEngine, id);
            case "diorite":
                return new TileDiorite(tutEngine, id);

            case "coal_ore":
                return new TileOre(tutEngine, Assets.COAL_ORE, id, ItemManager.COAL);
            case "iron_ore":
                return new TileOre(tutEngine, Assets.IRON_ORE, id, ItemManager.IRON_INGOT);
            case "gold_ore":
                return new TileOre(tutEngine, Assets.GOLD_ORE, id, ItemManager.GOLD_INGOT);
            case "diamond_ore":
                return new TileOre(tutEngine, Assets.DIAMOND_ORE, id, ItemManager.DIAMOND);

            case "obsidian":
                return new TileObsidian(tutEngine, id);

            case "water":
                return new TileWater(tutEngine, id);
            case "lava":
                return new TileLava(tutEngine, id);

            case "planks":
                return new TileWood(tutEngine, Assets.PLANKS, id);
            case "broken_stone":
                return new TileBrokenStone(tutEngine, id);

            default:
            case "air":
                return new TileAir(tutEngine);

            case "bookshelf":
                return new TileWood(tutEngine, Assets.BOOKSHELF, id);
            case "glitch":
                return new TileGlitch(tutEngine, id);
        }
    }
}
