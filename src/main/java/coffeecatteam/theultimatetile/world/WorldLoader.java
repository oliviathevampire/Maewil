package coffeecatteam.theultimatetile.world;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.inventory.items.Item;
import coffeecatteam.theultimatetile.inventory.items.ItemStack;
import coffeecatteam.theultimatetile.manager.EntityManager;
import coffeecatteam.theultimatetile.tiles.Tile;
import coffeecatteam.theultimatetile.utils.Utils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;

public class WorldLoader {

    private String path;
    private TheUltimateTile theUltimateTile;

    private String name;
    private int width, height;
    private int spawnX, spawnY;

    private int[][] bg_tiles;
    private int[][] fg_tiles;

    public WorldLoader(String path, TheUltimateTile theUltimateTile) {
        this.path = path;
        this.theUltimateTile = theUltimateTile;
    }

    public void loadWorld(boolean inJar) throws IOException, ParseException, URISyntaxException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(loadFile(path + "/world.json", inJar));

        name = (String) jsonObject.get("name");

        JSONArray size = (JSONArray) jsonObject.get("size");
        width = Utils.parseInt(size.get(0).toString());
        height = Utils.parseInt(size.get(1).toString());

        bg_tiles = new int[width][height];
        fg_tiles = new int[width][height];

        JSONArray spawn = (JSONArray) jsonObject.get("spawn");
        spawnX = Utils.parseInt(spawn.get(0).toString());
        spawnY = Utils.parseInt(spawn.get(1).toString());

        JSONObject bgTiles = (JSONObject) jsonObject.get("bg_tile");
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                JSONArray currentRow = (JSONArray) bgTiles.get("row" + y);
                bg_tiles[x][y] = Utils.parseInt(currentRow.get(x).toString());
            }
        }

        JSONObject fgTiles = (JSONObject) jsonObject.get("fg_tile");
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                JSONArray currentRow = (JSONArray) fgTiles.get("row" + y);
                fg_tiles[x][y] = Utils.parseInt(currentRow.get(x).toString());
            }
        }
    }

    private void loadEntityObj(JSONObject entityObj) {
        String id = (String) entityObj.get("id");
        JSONArray pos = (JSONArray) entityObj.get("pos");
        float x = Utils.parseFloat(pos.get(0).toString());
        float y = Utils.parseFloat(pos.get(1).toString());

        if (entityObj.containsKey("count")) {
            int count = Utils.parseInt(entityObj.get("count").toString());
            if (count > 9)
                count = 9;
            float ogX = Utils.parseFloat(pos.get(0).toString());
            for (int i = 0; i < count; i++) {
                theUltimateTile.getEntityManager().addEntity(EntityManager.loadEntity(theUltimateTile, id), x, y, true);
                x++;
                if (x > ogX + 2) {
                    x = ogX;
                    y++;
                }
            }
        } else {
            theUltimateTile.getEntityManager().addEntity(EntityManager.loadEntity(theUltimateTile, id), x, y, true);
        }
    }

    public void loadObjects(boolean inJar) throws IOException, ParseException, URISyntaxException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(loadFile(path + "/objects.json", inJar));

        /*
         * Entities
         */
        if (jsonObject.containsKey("entities")) {
            JSONObject entities = (JSONObject) jsonObject.get("entities");

            if (entities.containsKey("statics")) {
                JSONArray statics = (JSONArray) entities.get("statics");
                for (int i = 0; i < statics.size(); i++) {
                    JSONObject entity = (JSONObject) statics.get(i);
                    loadEntityObj(entity);
                }
            }

            if (entities.containsKey("creatures")) {
                JSONArray creatures = (JSONArray) entities.get("creatures");
                for (int i = 0; i < creatures.size(); i++) {
                    JSONObject entity = (JSONObject) creatures.get(i);
                    loadEntityObj(entity);
                }
            }
        }

        /*
         * Items
         */
        if (jsonObject.containsKey("items")) {
            JSONArray items = (JSONArray) jsonObject.get("items");
            for (int i = 0; i < items.size(); i++) {
                JSONObject itemObj = (JSONObject) items.get(i);
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
        }
    }

    private FileReader loadFile(String path, boolean inJar) throws URISyntaxException, FileNotFoundException {
        if (inJar)
            return new FileReader(new File(WorldLoader.class.getResource(path).toURI()));
        else
            return new FileReader(new File(path));
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
