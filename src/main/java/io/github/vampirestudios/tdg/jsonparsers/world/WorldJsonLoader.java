package io.github.vampirestudios.tdg.jsonparsers.world;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.coffeecatutils.io.FileUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.vampirestudios.tdg.objs.entities.Entities;
import io.github.vampirestudios.tdg.objs.entities.Entity;
import io.github.vampirestudios.tdg.objs.items.Item;
import io.github.vampirestudios.tdg.objs.items.ItemStack;
import io.github.vampirestudios.tdg.objs.tiles.Tile;
import io.github.vampirestudios.tdg.objs.tiles.Tiles;
import io.github.vampirestudios.tdg.screen.game.WorldListScreen;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.start.MaewilLauncher;
import io.github.vampirestudios.tdg.tags.CompoundTag;
import io.github.vampirestudios.tdg.tags.JsonToTag;
import io.github.vampirestudios.tdg.tags.TagException;
import io.github.vampirestudios.tdg.utils.Identifier;
import io.github.vampirestudios.tdg.utils.iinterface.IJSONLoader;
import io.github.vampirestudios.tdg.utils.registry.Registries;
import io.github.vampirestudios.tdg.world.TileList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

public class WorldJsonLoader implements IJSONLoader {

    public static final Map<String, String> BASE_FILES = new HashMap<>();

    static {
        BASE_FILES.put("world", "world_info");
        BASE_FILES.put("player", "player_info");
        BASE_FILES.put("items", "items");
        BASE_FILES.put("entity_s", "entities/statics");
        BASE_FILES.put("entity_c", "entities/creatures");
        BASE_FILES.put("tile_bg", "tiles/background");
        BASE_FILES.put("tile_fg", "tiles/foreground");
    }

    private String path;
    private MaewilEngine maewilEngine;

    // World
    private String name;
    private int width, height;
    private float spawnX;
    private float spawnY;

    private TileList bg_tiles, fg_tiles;

    // Player Info
    private String username;
    private int health;
    private int[] selectedSlots;
    private ItemStack[] inventory, hotbar;

    public WorldJsonLoader(String path, MaewilEngine maewilEngine) {
        this.path = path;
        this.maewilEngine = maewilEngine;
    }

    @Override
    public void load() throws IOException, ParseException {
        loadWorldInfo();
        MaewilLauncher.LOGGER.info("World [" + name + "] info loaded!");

        String bgLoadPath = path + "/" + BASE_FILES.get("tile_bg") + ".json";
        String fgLoadPath = path + "/" + BASE_FILES.get("tile_fg") + ".json";
        loadTiles(width, height, bgLoadPath, fgLoadPath, bg_tiles, fg_tiles);
        MaewilLauncher.LOGGER.info("World [" + name + "] tiles loaded!");

        loadEntities();
        MaewilLauncher.LOGGER.info("World [" + name + "] entities loaded!");

        loadItems();
        MaewilLauncher.LOGGER.info("World [" + name + "] ITEMS loaded!");

        loadPlayerInfo();
        MaewilLauncher.LOGGER.info("World [" + name + "] player info loaded!");
    }

    public void loadWorldInfo() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(FileUtils.loadFileOutSideJar(path + "/" + BASE_FILES.get("world") + ".json"));

        name = (String) jsonObject.get("name");
        MaewilLauncher.LOGGER.info("Loaded world name");

        JSONArray size = (JSONArray) jsonObject.get("size");
        width = NumberUtils.parseInt(size.get(0));
        height = NumberUtils.parseInt(size.get(1));
        MaewilLauncher.LOGGER.info("Loaded world size");

        JSONArray spawn = (JSONArray) jsonObject.get("spawn");
        spawnX = NumberUtils.parseFloat(spawn.get(0));
        spawnY = NumberUtils.parseFloat(spawn.get(1));
        MaewilLauncher.LOGGER.info("Loaded world player spawn");
    }

    public void loadTiles(int width, int height, String bgLoadPath, String fgLoadPath, TileList bg_tiles, TileList fg_tiles) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JsonObject jsonObjectBG = (JsonObject) parser.parse(FileUtils.loadFileOutSideJar(bgLoadPath));
        JsonObject jsonObjectFG = (JsonObject) parser.parse(FileUtils.loadFileOutSideJar(fgLoadPath));

        this.bg_tiles = new TileList(width, height);
        this.fg_tiles = new TileList(width, height);

        JsonObject bgTiles = (JsonObject) jsonObjectBG.get("bg_tile");
        for (int y = 0; y < height; y++) {
            JsonArray chunk = (JsonArray) bgTiles.get("chunk" + y);
            for (int x = 0; x < width; x++) {
                loadTile(chunk, true, x, bg_tiles, fg_tiles);
            }
        }
        MaewilLauncher.LOGGER.info("Loaded world background tiles");

        JsonObject fgTiles = (JsonObject) jsonObjectFG.get("fg_tile");
//        System.out.println("Foreground Tile: " + bgTiles.toJSONString());
        for (int y = 0; y < height; y++) {
            JsonArray chunk = (JsonArray) fgTiles.get("chunk" + y);
            for (int x = 0; x < width; x++) {
                loadTile(chunk, false, x, bg_tiles, fg_tiles);
            }
        }
        MaewilLauncher.LOGGER.info("Loaded world foreground tiles");
    }

    private void loadTile(JsonArray chunk, boolean bg, int x, TileList bg_tiles, TileList fg_tiles) {
        JsonObject tileObj = (JsonObject) chunk.get(x);
        Tile tile = Tiles.getTileById(tileObj.get("id").getAsString());
        int tx = NumberUtils.parseInt(tileObj.get("x"));
        int ty = NumberUtils.parseInt(tileObj.get("y"));

        System.out.println("Id: " + tileObj.get("id"));
        System.out.println("X: " + NumberUtils.parseInt(tileObj.get("x")));
        System.out.println("Y: " + NumberUtils.parseInt(tileObj.get("y")));

        if (bg)
            bg_tiles.setTileAtPos(tx, ty, tile);
        else
            fg_tiles.setTileAtPos(tx, ty, tile);
    }

    public void loadEntities() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JsonObject jsonObjectStatic = (JsonObject) parser.parse(FileUtils.loadFileOutSideJar(path + "/" + BASE_FILES.get("entity_s") + ".json"));
        JsonObject jsonObjectCreature = (JsonObject) parser.parse(FileUtils.loadFileOutSideJar(path + "/" + BASE_FILES.get("entity_c") + ".json"));

        // Static
        if (jsonObjectStatic.has("statics")) {
            JsonArray statics = (JsonArray) jsonObjectStatic.get("statics");
            for (Object aStatic : statics) {
                JsonObject entity = (JsonObject) aStatic;
                loadEntityObj(entity);
                JsonArray pos = (JsonArray) entity.get("pos");
                float x = NumberUtils.parseFloat(pos.get(0));
                float y = NumberUtils.parseFloat(pos.get(1));
                System.out.println(String.format("X: %d, Y: %d", x, y));

                Entity entity1 = Entities.jsonToEntity(entity);
                maewilEngine.getEntityManager().addEntity(entity1, x, y, true);
            }
            MaewilLauncher.LOGGER.info("Loaded world static entities");
        }

        // Creatures
        if (jsonObjectCreature.has("creatures")) {
            JsonArray creatures = (JsonArray) jsonObjectCreature.get("creatures");
            for (Object creature : creatures) {
                JsonObject entity = (JsonObject) creature;
                loadEntityObj(entity);
            }
            MaewilLauncher.LOGGER.info("Loaded world creature entities");
        }
        MaewilLauncher.LOGGER.info("Loaded world entities");
    }

    public void loadItems() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObjectItems = (JSONObject) parser.parse(FileUtils.loadFileOutSideJar(path + "/" + BASE_FILES.get("items") + ".json"));

        if (jsonObjectItems.containsKey("items")) {
            JSONArray items = (JSONArray) jsonObjectItems.get("items");
            for (Object item1 : items) {
                JSONObject itemObj = (JSONObject) item1;
                String id = (String) itemObj.get("id");
                JSONArray pos = (JSONArray) itemObj.get("pos");
                float x = NumberUtils.parseFloat(pos.get(0));
                float y = NumberUtils.parseFloat(pos.get(1));
                int count = 1;
                if (itemObj.containsKey("count"))
                    count = NumberUtils.parseInt(itemObj.get("count"));
                Item item = Registries.ITEMS.get(Identifier.tryParse(id));
                if (!item.isStackable())
                    count = 1;

                maewilEngine.getEntityManager().addItem(new ItemStack(item, count), x * Tile.TILE_SIZE, y * Tile.TILE_SIZE);
            }
            MaewilLauncher.LOGGER.info("Loaded world items");
        }
    }

    public void loadPlayerInfo() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(FileUtils.loadFileOutSideJar(path + "/" + BASE_FILES.get("player") + ".json"));

        if (jsonObject.containsKey("username")) {
            username = (String) jsonObject.get("username");
            maewilEngine.getPlayer().setUsername(username);
            MaewilLauncher.LOGGER.info("loaded player username!");
        }

        health = NumberUtils.parseInt(jsonObject.get("health"));
        maewilEngine.getPlayer().setCurrentHealth(health);
        MaewilLauncher.LOGGER.info("loaded player health!");

        selectedSlots = new int[2];
        JSONArray selected_slotsJ = (JSONArray) jsonObject.get("selected_slots");
        selectedSlots[0] = NumberUtils.parseInt(selected_slotsJ.get(0));
        selectedSlots[1] = NumberUtils.parseInt(selected_slotsJ.get(1));
        maewilEngine.getPlayer().getInventoryPlayer().setInventorySelectedIndex(selectedSlots[0]);
        maewilEngine.getPlayer().getInventoryPlayer().setHotbarSelectedIndex(selectedSlots[1]);

        inventory = new ItemStack[12];
        JSONObject inventoryJ = (JSONObject) jsonObject.get("inventory");
        for (int i = 0; i < inventory.length; i++) {
            JSONObject slot = (JSONObject) inventoryJ.get("slot_" + i);
            String id = (String) slot.get("id");
            if (id.equals("null"))
                inventory[i] = null;
            else {
                int count = 1;
                if (slot.containsKey("count")) {
                    count = NumberUtils.parseInt(slot.get("count"));
                }
                Item item = Registries.ITEMS.get(Identifier.tryParse(id));
                if (!item.isStackable())
                    count = 1;
                inventory[i] = new ItemStack(item, count);
            }
        }
        MaewilLauncher.LOGGER.info("loaded player inventory!");

        hotbar = new ItemStack[9];
        JSONObject hotbarJ = (JSONObject) jsonObject.get("hotbar");
        for (int i = 12; i < 21; i++) {
            JSONObject slot = (JSONObject) hotbarJ.get("slot_" + (i - 12));
            String id = (String) slot.get("id");
            if (id.equals("null"))
                hotbar[i - 12] = null;
            else {
                int count = 1;
                if (slot.containsKey("count")) {
                    count = NumberUtils.parseInt(slot.get("count"));
                }
                Item item = Registries.ITEMS.get(Identifier.tryParse(id));
                if (!item.isStackable())
                    count = 1;
                hotbar[i - 12] = new ItemStack(item, count);
            }
        }
        MaewilLauncher.LOGGER.info("loaded player hotbar!");

        int invIndex = 0;
        for (int i = 0; i < inventory.length; i++) {
            maewilEngine.getPlayer().getInventoryPlayer().getSlots().get(invIndex).setStack(inventory[invIndex]);
            invIndex++;
            if (invIndex >= inventory.length)
                break;
        }

        int hotbarIndex = 12;
        for (int i = 0; i < hotbar.length; i++) {
            maewilEngine.getPlayer().getInventoryPlayer().getSlots().get(hotbarIndex).setStack(hotbar[hotbarIndex - 12]);
            hotbarIndex++;
            if (hotbarIndex >= hotbar.length + 12)
                break;
        }
    }

    /*
     * Load an entity object
     */
    private void loadEntityObj(JsonObject entityObj) {
        String id = entityObj.get("id").getAsString();
        System.out.println(id);

        CompoundTag tags = new CompoundTag();
        if (entityObj.has("tags")) {
            JsonObject tagsJson = entityObj.get("tags").getAsJsonObject();
            try {
                tags = JsonToTag.getTagFromJson(tagsJson.getAsString());
            } catch (TagException e) {
                e.printStackTrace();
                tags = new CompoundTag();
            }
        }

        JsonArray pos = (JsonArray) entityObj.get("pos");
        float x = NumberUtils.parseFloat(pos.get(0));
        float y = NumberUtils.parseFloat(pos.get(1));

        int health = Entities.getEntityById(id).getMaxHealth();
        if (entityObj.has("health")) {
            int healthJ = NumberUtils.parseInt(entityObj.get("health"));
            if (healthJ < 0)
                healthJ = 0;
            if (healthJ > health)
                healthJ = health;
            health = healthJ;
        }

        Entity entity = Entities.getEntityById(id).newCopy();
        entity.setTags(tags);
        entity.setCurrentHealth(health);
        maewilEngine.getEntityManager().addEntity(entity, x, y, true);
    }

    public static void copyFiles(MaewilEngine maewilEngine, String from, String dest) {
        for (String file : BASE_FILES.values())
            copy(maewilEngine, WorldListScreen.class.getResourceAsStream(from + "/" + file + ".json"), dest + "/" + file + ".json");
    }


    public static boolean copy(MaewilEngine maewilEngine, InputStream source, String destination) {
        boolean success = true;

        MaewilLauncher.LOGGER.warn("Copying ->" + source + "\tto ->" + destination);

        try {
            if (!new File(destination).exists())
                new File(destination).mkdirs();

            Files.copy(source, Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            success = false;
        }

        return success;
    }

    // World
    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getSpawnX() {
        return spawnX;
    }

    public float getSpawnY() {
        return spawnY;
    }

    public TileList getBGTiles() {
        return bg_tiles;
    }

    public TileList getFGTiles() {
        return fg_tiles;
    }

    // Player Info

    public String getUsername() {
        return username;
    }

    public int getHealth() {
        return health;
    }

    public int[] getSelectedSlots() {
        return selectedSlots;
    }

    public ItemStack[] getInventory() {
        return inventory;
    }

    public ItemStack[] getHotbar() {
        return hotbar;
    }
}
