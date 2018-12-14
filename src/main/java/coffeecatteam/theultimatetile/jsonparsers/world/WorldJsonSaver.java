package coffeecatteam.theultimatetile.jsonparsers.world;

import coffeecatteam.coffeecatutils.Logger;
import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.entities.Entity;
import coffeecatteam.theultimatetile.game.entities.creatures.EntityCreature;
import coffeecatteam.theultimatetile.game.entities.creatures.EntityPlayer;
import coffeecatteam.theultimatetile.game.entities.statics.EntityStatic;
import coffeecatteam.theultimatetile.game.inventory.items.ItemStack;
import coffeecatteam.theultimatetile.game.tiles.Tile;
import coffeecatteam.theultimatetile.game.world.World;
import coffeecatteam.theultimatetile.utils.iinterface.IJSONSaver;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class WorldJsonSaver implements IJSONSaver {

    private String path;
    private World world;
    private Engine engine;

    public WorldJsonSaver(String path, World world, Engine engine) {
        this.path = path;
        this.world = world;
        this.engine = engine;
    }

    public void save(String username) throws IOException {
        Logger.print("\nSaving current world!");
        saveWorldInfo(world);
        Logger.print("World [" + world.getWorldName() + "] info saved!\n");

        saveTiles(world.getWidth(), world.getHeight(), world.getBg_tiles(), world.getFg_tiles(), path + "/" + WorldJsonLoader.BASE_FILES.get("tile_bg"), path + "/" + WorldJsonLoader.BASE_FILES.get("tile_fg"));
        Logger.print("World [" + world.getWorldName() + "] tiles saved!\n");

        /*
         * TEMP!!!
         */
        if (engine instanceof GameEngine) {
            saveEntities();
            Logger.print("World [" + world.getWorldName() + "] entities saved!\n");

            saveItems();
            Logger.print("World [" + world.getWorldName() + "] items saved!\n");

            savePlayerInfo(username);
            Logger.print("World [" + world.getWorldName() + "] player info saved!\n");
        }
    }

    @Override
    public void save() throws IOException {
        save(GameEngine.getGameEngine().getUsername());
    }

    public void saveWorldInfo(World world) throws IOException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("name", world.getWorldName());

        JSONArray size = new JSONArray();
        size.add(0, world.getWidth());
        size.add(1, world.getHeight());
        jsonObject.put("size", size);

        JSONArray spawn = new JSONArray();
        float spawnX, spawnY;
        if (engine instanceof GameEngine) {
            spawnX = GameEngine.getGameEngine().getEntityManager().getPlayer().getX() / Tile.TILE_WIDTH;
            spawnY = GameEngine.getGameEngine().getEntityManager().getPlayer().getY() / Tile.TILE_HEIGHT;
        } else {
            spawnX = world.getSpawnX();
            spawnY = world.getSpawnY();
        }
        spawn.add(0, spawnX);
        spawn.add(1, spawnY);
        jsonObject.put("spawn", spawn);

        saveJSONFileToSave(WorldJsonLoader.BASE_FILES.get("world"), jsonObject);
    }

    public void saveTiles(int width, int height, Tile[][] bgTiles, Tile[][] fgTiles, String bgSavePath, String fgSavePath) throws IOException {
        JSONObject jsonObjectBG = new JSONObject();
        JSONObject jsonObjectFG = new JSONObject();

        JSONObject bg_tile = new JSONObject();
        for (int y = 0; y < height; y++) {
            JSONArray chunk = new JSONArray();
            for (int x = 0; x < width; x++) {
                saveTile(chunk, bgTiles[x][y], x, y);
            }
            bg_tile.put("chunk" + y, chunk);
        }
        jsonObjectBG.put("bg_tile", bg_tile);
        Logger.print("Saved bg tiles");

        JSONObject fg_tile = new JSONObject();
        for (int y = 0; y < height; y++) {
            JSONArray chunk = new JSONArray();
            for (int x = 0; x < width; x++) {
                saveTile(chunk, fgTiles[x][y], x, y);
            }
            fg_tile.put("chunk" + y, chunk);
        }
        jsonObjectFG.put("fg_tile", fg_tile);
        Logger.print("Saved fg tiles");

        saveJSONFileToPath(bgSavePath, jsonObjectBG);
        saveJSONFileToPath(fgSavePath, jsonObjectFG);
    }

    private void saveTile(JSONArray chunk, Tile tile, int x, int y) {
        JSONObject tileObj = new JSONObject();
        tileObj.put("id", tile.getId());
        tileObj.put("x", x);
        tileObj.put("y", y);
        chunk.add(tileObj);
    }

    public void saveEntities() throws IOException {
        JSONObject jsonObjectStatic = new JSONObject();
        JSONObject jsonObjectCreature = new JSONObject();

        int entAmt = 0;
        for (Entity entity : GameEngine.getGameEngine().getEntityManager().getEntities())
            if (!(entity instanceof EntityPlayer))
                entAmt++;
        if (entAmt > 0) {
            int staticAmt = 0;
            int creatureAmt = 0;
            for (Entity entity : GameEngine.getGameEngine().getEntityManager().getEntities()) {
                if (!(entity instanceof EntityPlayer)) {
                    if (entity instanceof EntityStatic) {
                        staticAmt++;
                    }
                    if (entity instanceof EntityCreature) {
                        creatureAmt++;
                    }
                }
            }

            // Static
            if (staticAmt > 0) {
                JSONArray statics = new JSONArray();
                for (Entity entity : GameEngine.getGameEngine().getEntityManager().getEntities())
                    if (!(entity instanceof EntityPlayer))
                        if (entity instanceof EntityStatic)
                            saveEntityObj(entity, statics);
                jsonObjectStatic.put("statics", statics);
                Logger.print("World [" + path + "] static entities saved!");
            }

            // Creature
            if (creatureAmt > 0) {
                JSONArray creatures = new JSONArray();

                for (Entity entity : GameEngine.getGameEngine().getEntityManager().getEntities())
                    if (!(entity instanceof EntityPlayer))
                        if (entity instanceof EntityCreature)
                            saveEntityObj(entity, creatures);
                jsonObjectCreature.put("creatures", creatures);
                Logger.print("World [" + path + "] creature entities saved!");
            }
        }
        Logger.print("World [" + path + "] entities saved!");

        saveJSONFileToSave(WorldJsonLoader.BASE_FILES.get("entity_s"), jsonObjectStatic);
        saveJSONFileToSave(WorldJsonLoader.BASE_FILES.get("entity_c"), jsonObjectCreature);
    }

    public void saveItems() throws IOException {
        JSONObject jsonObject = new JSONObject();

        JSONArray items = new JSONArray();
        for (ItemStack stack : GameEngine.getGameEngine().getItemManager().getItems()) {
            JSONObject itemObj = new JSONObject();
            itemObj.put("id", stack.getId());

            JSONArray pos = new JSONArray();
            pos.add(0, stack.getItem().getPosition().x / Tile.TILE_WIDTH);
            pos.add(1, stack.getItem().getPosition().y / Tile.TILE_HEIGHT);
            itemObj.put("pos", pos);

            if (stack.getCount() > 1) {
                itemObj.put("count", stack.getCount());
            }
            items.add(itemObj);
        }
        jsonObject.put("items", items);
        Logger.print("World [" + path + "] items saved!");

        saveJSONFileToSave(WorldJsonLoader.BASE_FILES.get("items"), jsonObject);
    }

    public void savePlayerInfo(String username) throws IOException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("username", username);
        Logger.print("Player username saved [" + GameEngine.getGameEngine().getUsername() + "]");
        jsonObject.put("health", GameEngine.getGameEngine().getEntityManager().getPlayer().getCurrentHealth());
        jsonObject.put("glubel", GameEngine.getGameEngine().getEntityManager().getPlayer().getGlubel());
        jsonObject.put("lvl", GameEngine.getGameEngine().getEntityManager().getPlayer().getLvl());

        JSONArray selected_slots = new JSONArray();
        selected_slots.add(0, GameEngine.getGameEngine().getEntityManager().getPlayer().getInventoryPlayer().getInventorySelectedIndex());
        selected_slots.add(1, GameEngine.getGameEngine().getEntityManager().getPlayer().getInventoryPlayer().getHotbarSelectedIndex());
        jsonObject.put("selected_slots", selected_slots);

        JSONObject inventory = new JSONObject();
        for (int i = 0; i < 12; i++) {
            JSONObject slot = new JSONObject();
            ItemStack stack = GameEngine.getGameEngine().getEntityManager().getPlayer().getInventoryPlayer().getSlot(i).getStack();
            if (stack == null)
                slot.put("id", "null");
            else {
                slot.put("id", stack.getId());
                if (stack.getCount() > 1)
                    slot.put("count", stack.getCount());
            }
            inventory.put("slot_" + i, slot);
        }
        jsonObject.put("inventory", inventory);

        JSONObject hotbar = new JSONObject();
        for (int i = 12; i < 15; i++) {
            JSONObject slot = new JSONObject();
            ItemStack stack = GameEngine.getGameEngine().getEntityManager().getPlayer().getInventoryPlayer().getSlot(i).getStack();
            if (stack == null)
                slot.put("id", "null");
            else {
                slot.put("id", stack.getId());
                if (stack.getCount() > 1)
                    slot.put("count", stack.getCount());
            }
            hotbar.put("slot_" + (i - 12), slot);
        }
        jsonObject.put("hotbar", hotbar);

        saveJSONFileToSave(WorldJsonLoader.BASE_FILES.get("player"), jsonObject);
    }

    private void saveEntityObj(Entity entity, JSONArray entitiesArray) {
        JSONObject entityObj = new JSONObject();
        entityObj.put("id", entity.getId());

        JSONArray pos = new JSONArray();
        pos.add(0, entity.getX() / Tile.TILE_WIDTH);
        pos.add(1, entity.getY() / Tile.TILE_HEIGHT);
        entityObj.put("pos", pos);

        Map<String, String> tags = entity.saveTags();
        JSONObject tagsObj = new JSONObject(tags);
        for (String key : tags.keySet()) {
            tagsObj.put(key, tags.get(key));
        }
        entityObj.put("tags", tagsObj);

        if (entity.getCurrentHealth() < entity.getMaxHealth())
            entityObj.put("health", entity.getCurrentHealth());

        entitiesArray.add(entityObj);
    }

    private void saveJSONFileToSave(String name, JSONObject data) throws IOException {
        saveJSONFileToPath(path + "/" + name, data);
    }

    private void saveJSONFileToPath(String path, JSONObject data) throws IOException {
        FileWriter file = new FileWriter(path + ".json");
        file.write(data.toJSONString());
        file.flush();
    }
}
