package io.github.vampirestudios.tdg.jsonparsers.world;

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
import io.github.vampirestudios.tdg.utils.JsonUtils;
import io.github.vampirestudios.tdg.utils.iinterface.IJSONSaver;
import io.github.vampirestudios.tdg.world.TileList;
import io.github.vampirestudios.tdg.world.World;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
        TutLauncher.LOGGER.info("World [" + world.getWorldName() + "] items saved!");

        savePlayerInfo(username);
        TutLauncher.LOGGER.info("World [" + world.getWorldName() + "] player info saved!");
    }

    @Override
    public void save() throws IOException {
        save(tutEngine.getUsername());
    }

    public void saveWorldInfo(World world) throws IOException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("name", world.getWorldName());

        JSONArray size = new JSONArray();
        size.add(0, world.getWorldWidth());
        size.add(1, world.getWorldHeight());
        jsonObject.put("size", size);

        JSONArray spawn = new JSONArray();
        float spawnX, spawnY;
        spawnX = tutEngine.getPlayer().getX() / Tile.TILE_SIZE;
        spawnY = tutEngine.getPlayer().getY() / Tile.TILE_SIZE;
        spawn.add(0, spawnX);
        spawn.add(1, spawnY);
        jsonObject.put("spawn", spawn);

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

        saveJSONFileToPath(savePath, bgFileName, jsonObjectBG);
        saveJSONFileToPath(savePath, fgFileName, jsonObjectFG);
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
            JSONArray statics = new JSONArray();
            if (staticAmt > 0) {
                for (Entity entity : tutEngine.getEntityManager().getEntities())
                    if (!(entity instanceof PlayerEntity))
                        if (entity instanceof StaticEntity)
                            statics.add(Entities.entityToJson(entity));
                TutLauncher.LOGGER.info("World [" + path + "] static entities saved!");
            }
            jsonObjectStatic.put("statics", statics);

            // Creature
            JSONArray creatures = new JSONArray();
            if (creatureAmt > 0) {

                for (Entity entity : tutEngine.getEntityManager().getEntities())
                    if (!(entity instanceof PlayerEntity))
                        if (entity instanceof LivingEntity)
                            creatures.add(Entities.entityToJson(entity));
                TutLauncher.LOGGER.info("World [" + path + "] creature entities saved!");
            }
            jsonObjectCreature.put("creatures", creatures);
        }
        TutLauncher.LOGGER.info("World [" + path + "] entities saved!");

        saveJSONFileToSave(path, WorldJsonLoader.BASE_FILES.get("entity_s"), jsonObjectStatic);
        saveJSONFileToSave(path, WorldJsonLoader.BASE_FILES.get("entity_c"), jsonObjectCreature);
    }

    public void saveItems() throws IOException {
        JSONObject jsonObject = new JSONObject();

        JSONArray items = new JSONArray();
        for (Entity entity : tutEngine.getEntityManager().getEntities()) {
            if (entity instanceof ItemEntity) {
                ItemStack stack = ((ItemEntity) entity).getStack();
                JSONObject itemObj = new JSONObject();
                itemObj.put("id", stack.getId());

                JSONArray pos = new JSONArray();
                pos.add(0, entity.getPosition().x / Tile.TILE_SIZE);
                pos.add(1, entity.getPosition().y / Tile.TILE_SIZE);
                itemObj.put("pos", pos);

                if (stack.getCount() > 1) {
                    itemObj.put("count", stack.getCount());
                }
                items.add(itemObj);
            }
        }
        jsonObject.put("items", items);
        TutLauncher.LOGGER.info("World [" + path + "] items saved!");

        saveJSONFileToSave(path, WorldJsonLoader.BASE_FILES.get("items"), jsonObject);
    }

    public void savePlayerInfo(String username) throws IOException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("username", username);
        TutLauncher.LOGGER.info("Player username saved [" + tutEngine.getUsername() + "]");
        jsonObject.put("health", tutEngine.getPlayer().getCurrentHealth());

        JSONArray selected_slots = new JSONArray();
        selected_slots.add(0, tutEngine.getPlayer().getInventoryPlayer().getInventorySelectedIndex());
        selected_slots.add(1, tutEngine.getPlayer().getInventoryPlayer().getHotbarSelectedIndex());
        jsonObject.put("selected_slots", selected_slots);

        JSONObject inventory = new JSONObject();
        for (int i = 0; i < 12; i++) {
            JSONObject slot = new JSONObject();
            ItemStack stack = tutEngine.getPlayer().getInventoryPlayer().getSlot(i).getStack();
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
            ItemStack stack = tutEngine.getPlayer().getInventoryPlayer().getSlot(i).getStack();
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

        saveJSONFileToSave(path, WorldJsonLoader.BASE_FILES.get("player"), jsonObject);
    }

    private void saveJSONFileToSave(String path, String fileName, JSONObject data) throws IOException {
        saveJSONFileToPath(path, fileName, data);
    }

    private void saveJSONFileToPath(String path, String fileName, JSONObject data) throws IOException {
        Path parentPath = Paths.get(path, fileName).getParent();
        if(!Files.exists(parentPath)) {
            Files.createDirectories(parentPath);
        }
        String trueFilePath = path + "/" + fileName + ".json";
        FileWriter writer = new FileWriter(trueFilePath);
        writer.write(JsonUtils.prettyPrintJSON(data.toJSONString()));
        writer.flush();
        writer.close();
    }
}
