package coffeecatteam.theultimatetile.jsonparsers.world;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.entities.Entity;
import coffeecatteam.theultimatetile.inventory.items.Item;
import coffeecatteam.theultimatetile.inventory.items.ItemStack;
import coffeecatteam.theultimatetile.jsonparsers.iinterface.IJSONLoader;
import coffeecatteam.theultimatetile.manager.EntityManager;
import coffeecatteam.theultimatetile.tiles.Tile;
import coffeecatteam.theultimatetile.utils.Logger;
import coffeecatteam.theultimatetile.utils.Utils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class WorldJsonLoader implements IJSONLoader {

    private String path;
    private TheUltimateTile theUltimateTile;

    private String name;
    private int width, height;
    private int spawnX, spawnY;

    private int[][] bg_tiles;
    private int[][] fg_tiles;

    public WorldJsonLoader(String path, TheUltimateTile theUltimateTile) {
        this.path = path;
        this.theUltimateTile = theUltimateTile;
    }

    @Override
    public void load() throws IOException, ParseException {
        loadWorld();
        Logger.print("World " + name + " loaded!\n");
        loadObjects();
        Logger.print("World " + name + " objects (e.g. entities & items) loaded!\n");
    }

    /*
     * Load world
     * ------------------
     * World name, size
     * Player spawn
     * Background tiles
     * Foreground tiles
     */
    public void loadWorld() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(Utils.loadFileOutSideJar(path + "/world.json"));

        name = (String) jsonObject.get("name");
        Logger.print("Loaded world name");

        JSONArray size = (JSONArray) jsonObject.get("size");
        width = Utils.parseInt(size.get(0).toString());
        height = Utils.parseInt(size.get(1).toString());
        Logger.print("Loaded world size");

        bg_tiles = new int[width][height];
        fg_tiles = new int[width][height];

        JSONArray spawn = (JSONArray) jsonObject.get("spawn");
        spawnX = Utils.parseInt(spawn.get(0).toString());
        spawnY = Utils.parseInt(spawn.get(1).toString());
        Logger.print("Loaded world player spawn");

        JSONObject bgTiles = (JSONObject) jsonObject.get("bg_tile");
        for (int y = 0; y < height; y++) {
            JSONArray currentRow = (JSONArray) bgTiles.get("row" + y);
            for (int x = 0; x < width; x++) {
                bg_tiles[x][y] = Utils.parseInt(currentRow.get(x).toString());
            }
        }
        Logger.print("Loaded world bg tiles");

        JSONObject fgTiles = (JSONObject) jsonObject.get("fg_tile");
        for (int y = 0; y < height; y++) {
            JSONArray currentRow = (JSONArray) fgTiles.get("row" + y);
            for (int x = 0; x < width; x++) {
                fg_tiles[x][y] = Utils.parseInt(currentRow.get(x).toString());
            }
        }
        Logger.print("Loaded world fg tiles");
    }

    /*
     * Load objects
     * ------------------
     * Static entities
     * Creature entities
     * Items
     */
    public void loadObjects() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(Utils.loadFileOutSideJar(path + "/objects.json"));

        /*
         * Entities
         */
        if (jsonObject.containsKey("entities")) {
            JSONObject entities = (JSONObject) jsonObject.get("entities");

            if (entities.containsKey("statics")) {
                JSONArray statics = (JSONArray) entities.get("statics");
                for (Object aStatic : statics) {
                    JSONObject entity = (JSONObject) aStatic;
                    loadEntityObj(entity, false);
                }
                Logger.print("Loaded world static entities");
            }

            if (entities.containsKey("creatures")) {
                JSONArray creatures = (JSONArray) entities.get("creatures");
                for (Object creature : creatures) {
                    JSONObject entity = (JSONObject) creature;
                    loadEntityObj(entity, true);
                }
                Logger.print("Loaded world creature entities");
            }
            Logger.print("Loaded world entities");
        }

        /*
         * Items
         */
        if (jsonObject.containsKey("items")) {
            JSONArray items = (JSONArray) jsonObject.get("items");
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

    /*
     * Load an entity object
     */
    private void loadEntityObj(JSONObject entityObj, boolean isCreature) {
        String id = (String) entityObj.get("id");

        String[] data = null;
        if (isCreature) {
            if (entityObj.containsKey("dataTags")) {
                JSONArray dataTags = (JSONArray) entityObj.get("dataTags");
                data = new String[dataTags.size()];
                for (int i = 0; i < dataTags.size(); i++) {
                    data[i] = (String) dataTags.get(i);
                }
            }
        }

        JSONArray pos = (JSONArray) entityObj.get("pos");
        float x = Utils.parseFloat(pos.get(0).toString());
        float y = Utils.parseFloat(pos.get(1).toString());

        if (entityObj.containsKey("count")) {
            int count = Utils.parseInt(entityObj.get("count").toString());
            if (count > 9)
                count = 9;
            float ogX = Utils.parseFloat(pos.get(0).toString());
            for (int i = 0; i < count; i++) {
                loadEntity(EntityManager.loadEntity(theUltimateTile, id).setDataTags(data), x, y);
                x++;
                if (x > ogX + 2) {
                    x = ogX;
                    y++;
                }
            }
        } else {
            loadEntity(EntityManager.loadEntity(theUltimateTile, id).setDataTags(data), x, y);
        }
    }

    private void loadEntity(Entity e, float x, float y) {
        theUltimateTile.getEntityManager().addEntity(e, x, y, true);
    }

    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getSpawnX() {
        return spawnX;
    }

    public int getSpawnY() {
        return spawnY;
    }

    public int[][] getBg_tiles() {
        return bg_tiles;
    }

    public int[][] getFg_tiles() {
        return fg_tiles;
    }
}
