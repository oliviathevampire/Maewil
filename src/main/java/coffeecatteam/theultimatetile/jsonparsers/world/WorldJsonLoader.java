package coffeecatteam.theultimatetile.jsonparsers.world;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.inventory.items.Item;
import coffeecatteam.theultimatetile.inventory.items.ItemStack;
import coffeecatteam.theultimatetile.jsonparsers.iinterface.IJSONLoader;
import coffeecatteam.theultimatetile.manager.EntityManager;
import coffeecatteam.theultimatetile.state.game.StateSelectGame;
import coffeecatteam.theultimatetile.tiles.Tile;
import coffeecatteam.theultimatetile.tiles.Tiles;
import coffeecatteam.theultimatetile.utils.Logger;
import coffeecatteam.theultimatetile.utils.Utils;
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
    private TheUltimateTile theUltimateTile;

    // World
    private String name;
    private int width, height;
    private float spawnX;
    private float spawnY;

    private Tile[][] bg_tiles;
    private Tile[][] fg_tiles;

    // Player Info
    private String username;
    private int health, glubel, lvl;
    private int[] selected_slots;
    private ItemStack[] inventory, hotbar;

    public WorldJsonLoader(String path, TheUltimateTile theUltimateTile) {
        this.path = path;
        this.theUltimateTile = theUltimateTile;
    }

    @Override
    public void load() throws IOException, ParseException {
        loadWorldInfo();
        Logger.print("World [" + name + "] info loaded!\n");

        loadTiles();
        Logger.print("World [" + name + "] tiles loaded!\n");

        loadEntities();
        Logger.print("World [" + name + "] entities loaded!\n");

        loadItems();
        Logger.print("World [" + name + "] items loaded!\n");

        loadPlayerInfo();
        Logger.print("World [" + name + "] player info loaded!\n");
    }

    public void loadWorldInfo() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(Utils.loadFileOutSideJar(path + "/" + BASE_FILES.get("world") + ".json"));

        name = (String) jsonObject.get("name");
        Logger.print("Loaded world name");

        JSONArray size = (JSONArray) jsonObject.get("size");
        width = Utils.parseInt(size.get(0).toString());
        height = Utils.parseInt(size.get(1).toString());
        Logger.print("Loaded world size");

        JSONArray spawn = (JSONArray) jsonObject.get("spawn");
        spawnX = Utils.parseFloat(spawn.get(0).toString());
        spawnY = Utils.parseFloat(spawn.get(1).toString());
        Logger.print("Loaded world player spawn");
    }

    public void loadTiles() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObjectBG = (JSONObject) parser.parse(Utils.loadFileOutSideJar(path + "/" + BASE_FILES.get("tile_bg") + ".json"));
        JSONObject jsonObjectFG = (JSONObject) parser.parse(Utils.loadFileOutSideJar(path + "/" + BASE_FILES.get("tile_fg") + ".json"));

        bg_tiles = new Tile[width][height];
        fg_tiles = new Tile[width][height];

        JSONObject bgTiles = (JSONObject) jsonObjectBG.get("bg_tile");
        for (int y = 0; y < height; y++) {
            JSONArray chunk = (JSONArray) bgTiles.get("chunk" + y);
            for (int x = 0; x < width; x++) {
                loadTile(chunk, true, x);
            }
        }
        Logger.print("Loaded world background tiles");

        JSONObject fgTiles = (JSONObject) jsonObjectFG.get("fg_tile");
        for (int y = 0; y < height; y++) {
            JSONArray chunk = (JSONArray) fgTiles.get("chunk" + y);
            for (int x = 0; x < width; x++) {
                loadTile(chunk, false, x);
            }
        }
        Logger.print("Loaded world foreground tiles");
    }

    private void loadTile(JSONArray chunk, boolean bg, int x) {
        JSONObject tileObj = (JSONObject) chunk.get(x);
        Tile tile = Tiles.getTile(theUltimateTile, (String) tileObj.get("id"));
        int tx = Utils.parseInt((String) tileObj.get("x"));
        int ty = Utils.parseInt((String) tileObj.get("y"));

        if (bg)
            bg_tiles[tx][ty] = tile;
        else
            fg_tiles[tx][ty] = tile;
    }

    public void loadEntities() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObjectStatic = (JSONObject) parser.parse(Utils.loadFileOutSideJar(path + "/" + BASE_FILES.get("entity_s") + ".json"));
        JSONObject jsonObjectCreature = (JSONObject) parser.parse(Utils.loadFileOutSideJar(path + "/" + BASE_FILES.get("entity_c") + ".json"));

        // Static
        if (jsonObjectStatic.containsKey("statics")) {
            JSONArray statics = (JSONArray) jsonObjectStatic.get("statics");
            for (Object aStatic : statics) {
                JSONObject entity = (JSONObject) aStatic;
                loadEntityObj(entity);
            }
            Logger.print("Loaded world static entities");
        }

        // Creatures
        if (jsonObjectCreature.containsKey("creatures")) {
            JSONArray creatures = (JSONArray) jsonObjectCreature.get("creatures");
            for (Object creature : creatures) {
                JSONObject entity = (JSONObject) creature;
                loadEntityObj(entity);
            }
            Logger.print("Loaded world creature entities");
        }
        Logger.print("Loaded world entities");
    }

    public void loadItems() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObjectItems = (JSONObject) parser.parse(Utils.loadFileOutSideJar(path + "/" + BASE_FILES.get("items") + ".json"));

        if (jsonObjectItems.containsKey("items")) {
            JSONArray items = (JSONArray) jsonObjectItems.get("items");
            for (Object item1 : items) {
                JSONObject itemObj = (JSONObject) item1;
                String id = (String) itemObj.get("id");
                JSONArray pos = (JSONArray) itemObj.get("pos");
                float x = Utils.parseFloat(pos.get(0).toString());
                float y = Utils.parseFloat(pos.get(1).toString());
                int count = 1;
                if (itemObj.containsKey("count"))
                    count = Utils.parseInt(itemObj.get("count").toString());
                Item item = Item.items.get(id);
                if (!item.isStackable())
                    count = 1;

                theUltimateTile.getItemManager().addItem(new ItemStack(item, count), x * Tile.TILE_WIDTH, y * Tile.TILE_HEIGHT);
            }
            Logger.print("Loaded world items");
        }
    }

    public void loadPlayerInfo() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(Utils.loadFileOutSideJar(path + "/" + BASE_FILES.get("player") + ".json"));

        if (jsonObject.containsKey("username")) {
            username = (String) jsonObject.get("username");
            theUltimateTile.getEntityManager().getPlayer().setUsername(username);
            Logger.print("loaded player username!");
        }

        health = Utils.parseInt(jsonObject.get("health").toString());
        theUltimateTile.getEntityManager().getPlayer().setCurrentHealth(health);
        Logger.print("loaded player health!");

        glubel = Utils.parseInt(jsonObject.get("glubel").toString());
        theUltimateTile.getEntityManager().getPlayer().setGlubel(glubel);
        Logger.print("loaded player glubel!");

        lvl = Utils.parseInt(jsonObject.get("lvl").toString());
        theUltimateTile.getEntityManager().getPlayer().setLvl(lvl);
        Logger.print("loaded player lvl!");

        selected_slots = new int[2];
        JSONArray selected_slotsJ = (JSONArray) jsonObject.get("selected_slots");
        selected_slots[0] = Utils.parseInt(selected_slotsJ.get(0).toString());
        selected_slots[1] = Utils.parseInt(selected_slotsJ.get(1).toString());
        theUltimateTile.getEntityManager().getPlayer().getInventoryPlayer().setInventorySelectedIndex(selected_slots[0]);
        theUltimateTile.getEntityManager().getPlayer().getInventoryPlayer().setHotbarSelectedIndex(selected_slots[1]);

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
                    count = Utils.parseInt(slot.get("count").toString());
                }
                Item item = Item.items.get(id);
                if (!item.isStackable())
                    count = 1;
                inventory[i] = new ItemStack(item, count);
            }
        }
        Logger.print("loaded player inventory!");

        hotbar = new ItemStack[3];
        JSONObject hotbarJ = (JSONObject) jsonObject.get("hotbar");
        for (int i = 12; i < 15; i++) {
            JSONObject slot = (JSONObject) hotbarJ.get("slot_" + (i - 12));
            String id = (String) slot.get("id");
            if (id.equals("null"))
                hotbar[i - 12] = null;
            else {
                int count = 1;
                if (slot.containsKey("count")) {
                    count = Utils.parseInt(slot.get("count").toString());
                }
                Item item = Item.items.get(id);
                if (!item.isStackable())
                    count = 1;
                hotbar[i - 12] = new ItemStack(item, count);
            }
        }
        Logger.print("loaded player hotbar!");

        int invIndex = 0;
        for (int i = 0; i < inventory.length; i++) {
            theUltimateTile.getEntityManager().getPlayer().getInventoryPlayer().getSlots().get(invIndex).setStack(inventory[invIndex]);
            invIndex++;
            if (invIndex >= inventory.length)
                break;
        }

        int hotbarIndex = 12;
        for (int i = 0; i < hotbar.length; i++) {
            theUltimateTile.getEntityManager().getPlayer().getInventoryPlayer().getSlots().get(hotbarIndex).setStack(hotbar[hotbarIndex - 12]);
            hotbarIndex++;
            if (hotbarIndex >= hotbar.length + 12)
                break;
        }
    }

    /*
     * Load an entity object
     */
    private void loadEntityObj(JSONObject entityObj) {
        String id = (String) entityObj.get("id");

        String[] data = null;
        if (entityObj.containsKey("dataTags")) {
            JSONArray dataTags = (JSONArray) entityObj.get("dataTags");
            data = new String[dataTags.size()];
            for (int i = 0; i < dataTags.size(); i++) {
                data[i] = (String) dataTags.get(i);
            }
        }

        JSONArray pos = (JSONArray) entityObj.get("pos");
        float x = Utils.parseFloat(pos.get(0).toString());
        float y = Utils.parseFloat(pos.get(1).toString());

        int health = EntityManager.loadEntity(theUltimateTile, id).getMaxHealth();
        if (entityObj.containsKey("health")) {
            int healthJ = Utils.parseInt(entityObj.get("health").toString());
            if (healthJ < 0)
                healthJ = 0;
            if (healthJ > health)
                healthJ = health;
            health = healthJ;
        }

        int count = 1;
        if (entityObj.containsKey("count")) {
            int countJ = Utils.parseInt(entityObj.get("count").toString());
            if (countJ > 9)
                countJ = 9;
            if (countJ < 1)
                countJ = 1;
            count = countJ;
        }
        loadEntity(id, x, y, count, pos, health, data);
    }

    private void loadEntity(String id, float x, float y, int count, JSONArray pos, int health, String[] data) {
        float ogX = Utils.parseFloat(pos.get(0).toString());
        for (int i = 0; i < count; i++) {
            theUltimateTile.getEntityManager().addEntity(EntityManager.loadEntity(theUltimateTile, id).setDataTags(data).setCurrentHealth(health), x, y, true);
            x++;
            if (x > ogX + 2) {
                x = ogX;
                y++;
            }
        }
    }

    public static void copyFiles(String from, String dest) {
        for (String file : BASE_FILES.values())
            copy(StateSelectGame.class.getResourceAsStream(from + "/" + file + ".json"), dest + "/" + file + ".json");
    }


    public static boolean copy(InputStream source, String destination) {
        boolean success = true;

        System.out.println("Copying ->" + source + "\n\tto ->" + destination);

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

    public Tile[][] getBg_tiles() {
        return bg_tiles;
    }

    public Tile[][] getFg_tiles() {
        return fg_tiles;
    }

    // Player Info

    public String getUsername() {
        return username;
    }

    public int getHealth() {
        return health;
    }

    public int getGlubel() {
        return glubel;
    }

    public int getLvl() {
        return lvl;
    }

    public int[] getSelected_slots() {
        return selected_slots;
    }

    public ItemStack[] getInventory() {
        return inventory;
    }

    public ItemStack[] getHotbar() {
        return hotbar;
    }
}
