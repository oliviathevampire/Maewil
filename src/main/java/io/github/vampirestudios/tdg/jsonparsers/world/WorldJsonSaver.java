package io.github.vampirestudios.tdg.jsonparsers.world;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.vampirestudios.tdg.objs.entities.Entities;
import io.github.vampirestudios.tdg.objs.entities.Entity;
import io.github.vampirestudios.tdg.objs.entities.ItemEntity;
import io.github.vampirestudios.tdg.objs.entities.creatures.LivingEntity;
import io.github.vampirestudios.tdg.objs.entities.creatures.PlayerEntity;
import io.github.vampirestudios.tdg.objs.entities.statics.StaticEntity;
import io.github.vampirestudios.tdg.objs.items.ItemStack;
import io.github.vampirestudios.tdg.objs.tiles.Tile;
import io.github.vampirestudios.tdg.start.TutEngine;
import io.github.vampirestudios.tdg.start.TutLauncher;
import io.github.vampirestudios.tdg.utils.iinterface.IJSONSaver;
import io.github.vampirestudios.tdg.world.TileList;
import io.github.vampirestudios.tdg.world.World;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class WorldJsonSaver implements IJSONSaver {

    private String path;
    private World world;
    private TutEngine tutEngine;

    public WorldJsonSaver(String path, World world, TutEngine tutEngine) {
        this.path = path;
        this.world = world;
        this.tutEngine = tutEngine;
    }

    public void save(String username) throws IOException {
        TutLauncher.LOGGER.info("Saving current world!");
        saveWorldInfo(world);
        TutLauncher.LOGGER.info("World [" + world.getWorldName() + "] info saved!");

        saveTiles(world.getWorldWidth(), world.getWorldHeight(), world.getBGTiles(), world.getFgTiles(), path, WorldJsonLoader.BASE_FILES.get("tile_bg"), WorldJsonLoader.BASE_FILES.get("tile_fg"));
        TutLauncher.LOGGER.info("World [" + world.getWorldName() + "] tiles saved!");

        saveEntities();
        TutLauncher.LOGGER.info("World [" + world.getWorldName() + "] entities saved!");

        saveItems();
        TutLauncher.LOGGER.info("World [" + world.getWorldName() + "] ITEMS saved!");

        savePlayerInfo(username);
        TutLauncher.LOGGER.info("World [" + world.getWorldName() + "] player info saved!");
    }

    @Override
    public void save() throws IOException {
        save(tutEngine.getUsername());
    }

    public void saveWorldInfo(World world) throws IOException {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("name", world.getWorldName());

        JsonArray size = new JsonArray();
        size.add(world.getWorldWidth());
        size.add(world.getWorldHeight());
        jsonObject.add("size", size);

        JsonArray spawn = new JsonArray();
        float spawnX, spawnY;
        spawnX = tutEngine.getPlayer().getX() / Tile.TILE_SIZE;
        spawnY = tutEngine.getPlayer().getY() / Tile.TILE_SIZE;
        spawn.add(spawnX);
        spawn.add(spawnY);
        jsonObject.add("spawn", spawn);

        saveJSONFileToSave(path, WorldJsonLoader.BASE_FILES.get("world"), jsonObject);
    }

    public void saveTiles(int width, int height, TileList bgTiles, TileList fgTiles, String savePath, String bgFileName, String fgFileName) throws IOException {
        JSONObject jsonObjectBG = new JSONObject();
        JSONObject jsonObjectFG = new JSONObject();

        JSONObject bg_tile = new JSONObject();
        for (int y = 0; y < height; y++) {
            JSONArray chunk = new JSONArray();
            for (int x = 0; x < width; x++)
                saveTile(chunk, bgTiles.getTileAtPos(x, y), x, y);
            bg_tile.put("chunk" + y, chunk);
        }
        jsonObjectBG.put("bg_tile", bg_tile);
        TutLauncher.LOGGER.info("Saved bg tiles");

        JSONObject fg_tile = new JSONObject();
        for (int y = 0; y < height; y++) {
            JSONArray chunk = new JSONArray();
            for (int x = 0; x < width; x++)
                saveTile(chunk, fgTiles.getTileAtPos(x, y), x, y);
            fg_tile.put("chunk" + y, chunk);
        }
        jsonObjectFG.put("fg_tile", fg_tile);
        TutLauncher.LOGGER.info("Saved fg tiles");

//        saveJSONFileToPath(savePath, bgFileName, jsonObjectBG);
//        saveJSONFileToPath(savePath, fgFileName, jsonObjectFG);
    }

    private void saveTile(JSONArray chunk, Tile tile, int x, int y) {
        JSONObject tileObj = new JSONObject();
        tileObj.put("id", tile.getId());
        tileObj.put("x", x);
        tileObj.put("y", y);
        chunk.add(tileObj);
    }

    public void saveEntities() throws IOException {
        JsonObject jsonObjectStatic = new JsonObject();
        JsonObject jsonObjectCreature = new JsonObject();

        int entAmt = 0;
        for (Entity entity : tutEngine.getEntityManager().getEntities())
            if (!(entity instanceof PlayerEntity))
                entAmt++;
        if (entAmt > 0) {
            int staticAmt = 0;
            int creatureAmt = 0;
            for (Entity entity : tutEngine.getEntityManager().getEntities()) {
                if (!(entity instanceof PlayerEntity)) {
                    if (entity instanceof StaticEntity) {
                        staticAmt++;
                    }
                    if (entity instanceof LivingEntity) {
                        creatureAmt++;
                    }
                }
            }

            // Static
            JsonArray statics = new JsonArray();
            if (staticAmt > 0) {
                for (Entity entity : tutEngine.getEntityManager().getEntities())
                    if (!(entity instanceof PlayerEntity))
                        if (entity instanceof StaticEntity)
                            statics.add(Entities.entityToJson(entity));
                TutLauncher.LOGGER.info("World [" + path + "] static entities saved!");
            }
            jsonObjectStatic.add("statics", statics);

            // Creature
            JsonArray creatures = new JsonArray();
            if (creatureAmt > 0) {

                for (Entity entity : tutEngine.getEntityManager().getEntities())
                    if (!(entity instanceof PlayerEntity))
                        if (entity instanceof LivingEntity)
                            creatures.add(Entities.entityToJson(entity));
                TutLauncher.LOGGER.info("World [" + path + "] creature entities saved!");
            }
            jsonObjectCreature.add("creatures", creatures);
        }
        TutLauncher.LOGGER.info("World [" + path + "] entities saved!");

        saveJSONFileToSave(path, WorldJsonLoader.BASE_FILES.get("entity_s"), jsonObjectStatic);
        saveJSONFileToSave(path, WorldJsonLoader.BASE_FILES.get("entity_c"), jsonObjectCreature);
    }

    public void saveItems() throws IOException {
        JsonObject jsonObject = new JsonObject();

        JsonArray items = new JsonArray();
        for (Entity entity : tutEngine.getEntityManager().getEntities()) {
            if (entity instanceof ItemEntity) {
                ItemStack stack = ((ItemEntity) entity).getStack();
                JsonObject itemObj = new JsonObject();
                itemObj.addProperty("id", stack.getId());

                JsonArray pos = new JsonArray();
                pos.add(entity.getPosition().x / Tile.TILE_SIZE);
                pos.add(entity.getPosition().y / Tile.TILE_SIZE);
                itemObj.add("pos", pos);

                if (stack.getCount() > 1) {
                    itemObj.addProperty("count", stack.getCount());
                }
                items.add(itemObj);
            }
        }
        jsonObject.add("ITEMS", items);
        TutLauncher.LOGGER.info("World [" + path + "] ITEMS saved!");

        saveJSONFileToSave(path, WorldJsonLoader.BASE_FILES.get("ITEMS"), jsonObject);
    }

    public void savePlayerInfo(String username) throws IOException {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("username", username);
        TutLauncher.LOGGER.info("Player username saved [" + tutEngine.getUsername() + "]");
        jsonObject.addProperty("health", tutEngine.getPlayer().getCurrentHealth());
        jsonObject.addProperty("glubel", tutEngine.getPlayer().getGlubel());
        jsonObject.addProperty("lvl", tutEngine.getPlayer().getLvl());

        JsonArray selected_slots = new JsonArray();
        JsonObject object = new JsonObject();
        object.addProperty("0", tutEngine.getPlayer().getInventoryPlayer().getInventorySelectedIndex());
        selected_slots.add(object);
        JsonObject object2 = new JsonObject();
        object2.addProperty("1", tutEngine.getPlayer().getInventoryPlayer().getHotbarSelectedIndex());
        selected_slots.add(object2);
        jsonObject.add("selected_slots", selected_slots);

        JsonObject inventory = new JsonObject();
        for (int i = 0; i < 12; i++) {
            JsonObject slot = new JsonObject();
            ItemStack stack = tutEngine.getPlayer().getInventoryPlayer().getSlot(i).getStack();
            if (stack == null)
                slot.addProperty("id", "null");
            else {
                slot.addProperty("id", stack.getId());
                if (stack.getCount() > 1)
                    slot.addProperty("count", stack.getCount());
            }
            inventory.add("slot_" + i, slot);
        }
        jsonObject.add("inventory", inventory);

        JsonObject hotbar = new JsonObject();
        for (int i = 12; i < 15; i++) {
            JsonObject slot = new JsonObject();
            ItemStack stack = tutEngine.getPlayer().getInventoryPlayer().getSlot(i).getStack();
            if (stack == null)
                slot.addProperty("id", "null");
            else {
                slot.addProperty("id", stack.getId());
                if (stack.getCount() > 1)
                    slot.addProperty("count", stack.getCount());
            }
            hotbar.add("slot_" + (i - 12), slot);
        }
        jsonObject.add("hotbar", hotbar);

        saveJSONFileToSave(path, WorldJsonLoader.BASE_FILES.get("player"), jsonObject);
    }

    private void saveJSONFileToSave(String path, String fileName, JsonObject data) throws IOException {
        saveJSONFileToPath(path, fileName, data);
    }

    private void saveJSONFileToPath(String path, String fileName, JsonObject data) throws IOException {
        String trueFilePath = path + "/" + fileName + ".json";
        FileWriter writer = new FileWriter(trueFilePath);
        writer.write(data.toString());
        writer.flush();
    }
}
