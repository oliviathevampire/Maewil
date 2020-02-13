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
import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.start.MaewilLauncher;
import io.github.vampirestudios.tdg.utils.JsonUtils;
import io.github.vampirestudios.tdg.utils.iinterface.IJSONSaver;
import io.github.vampirestudios.tdg.world.TileList;
import io.github.vampirestudios.tdg.world.World;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WorldJsonSaver implements IJSONSaver {

    private String path;
    private World world;
    private MaewilEngine maewilEngine;

    public WorldJsonSaver(String path, World world, MaewilEngine maewilEngine) {
        this.path = path;
        this.world = world;
        this.maewilEngine = maewilEngine;
    }

    public void save(String username) throws IOException {
        MaewilLauncher.LOGGER.info("Saving current world!");
        saveWorldInfo(world);
        MaewilLauncher.LOGGER.info("World [" + world.getWorldName() + "] info saved!");

        saveTiles(world.getWorldWidth(), world.getWorldHeight(), world.getBGTiles(), world.getFgTiles(), path, WorldJsonLoader.BASE_FILES.get("tile_bg"), WorldJsonLoader.BASE_FILES.get("tile_fg"));
        MaewilLauncher.LOGGER.info("World [" + world.getWorldName() + "] tiles saved!");

        saveEntities();
        MaewilLauncher.LOGGER.info("World [" + world.getWorldName() + "] entities saved!");

        saveItems();
        MaewilLauncher.LOGGER.info("World [" + world.getWorldName() + "] items saved!");

        savePlayerInfo(username);
        MaewilLauncher.LOGGER.info("World [" + world.getWorldName() + "] player info saved!");
    }

    @Override
    public void save() throws IOException {
        save(maewilEngine.getUsername());
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
        spawnX = maewilEngine.getPlayer().getX() / Tile.TILE_SIZE;
        spawnY = maewilEngine.getPlayer().getY() / Tile.TILE_SIZE;
        spawn.add(spawnX);
        spawn.add(spawnY);
        jsonObject.add("spawn", spawn);

        saveJSONFileToSave(path, WorldJsonLoader.BASE_FILES.get("world"), jsonObject);
    }

    public void saveTiles(int width, int height, TileList bgTiles, TileList fgTiles, String savePath, String bgFileName, String fgFileName) throws IOException {
        JsonObject jsonObjectBG = new JsonObject();
        JsonObject jsonObjectFG = new JsonObject();

        JsonObject bg_tile = new JsonObject();
        for (int y = 0; y < height; y++) {
            JsonArray chunk = new JsonArray();
            for (int x = 0; x < width; x++)
                saveTile(chunk, bgTiles.getTileAtPos(x, y), x, y);
            bg_tile.add("chunk" + y, chunk);
        }
        jsonObjectBG.add("bg_tile", bg_tile);
        MaewilLauncher.LOGGER.info("Saved bg tiles");

        JsonObject fg_tile = new JsonObject();
        for (int y = 0; y < height; y++) {
            JsonArray chunk = new JsonArray();
            for (int x = 0; x < width; x++)
                saveTile(chunk, fgTiles.getTileAtPos(x, y), x, y);
            fg_tile.add("chunk" + y, chunk);
        }
        jsonObjectFG.add("fg_tile", fg_tile);
        MaewilLauncher.LOGGER.info("Saved fg tiles");

        saveJSONFileToPath(savePath, bgFileName, jsonObjectBG);
        saveJSONFileToPath(savePath, fgFileName, jsonObjectFG);
    }

    private void saveTile(JsonArray chunk, Tile tile, int x, int y) {
        JsonObject tileObj = new JsonObject();
        tileObj.addProperty("id", tile.getId());
        tileObj.addProperty("x", x);
        tileObj.addProperty("y", y);
        chunk.add(tileObj);
    }

    public void saveEntities() throws IOException {
        JsonObject jsonObjectStatic = new JsonObject();
        JsonObject jsonObjectCreature = new JsonObject();

        int entAmt = 0;
        for (Entity entity : maewilEngine.getEntityManager().getEntities())
            if (!(entity instanceof PlayerEntity))
                entAmt++;
        if (entAmt > 0) {
            int staticAmt = 0;
            int creatureAmt = 0;
            for (Entity entity : maewilEngine.getEntityManager().getEntities()) {
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
                for (Entity entity : maewilEngine.getEntityManager().getEntities())
                    if (!(entity instanceof PlayerEntity))
                        if (entity instanceof StaticEntity)
                            statics.add(Entities.entityToJson(entity));
                MaewilLauncher.LOGGER.info("World [" + path + "] static entities saved!");
            }
            jsonObjectStatic.add("statics", statics);

            // Creature
            JsonArray creatures = new JsonArray();
            if (creatureAmt > 0) {

                for (Entity entity : maewilEngine.getEntityManager().getEntities())
                    if (!(entity instanceof PlayerEntity))
                        if (entity instanceof LivingEntity)
                            creatures.add(Entities.entityToJson(entity));
                MaewilLauncher.LOGGER.info("World [" + path + "] creature entities saved!");
            }
            jsonObjectCreature.add("creatures", creatures);
        }
        MaewilLauncher.LOGGER.info("World [" + path + "] entities saved!");

        saveJSONFileToSave(path, WorldJsonLoader.BASE_FILES.get("entity_s"), jsonObjectStatic);
        saveJSONFileToSave(path, WorldJsonLoader.BASE_FILES.get("entity_c"), jsonObjectCreature);
    }

    public void saveItems() throws IOException {
        JsonObject jsonObject = new JsonObject();

        JsonArray items = new JsonArray();
        for (Entity entity : maewilEngine.getEntityManager().getEntities()) {
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
        jsonObject.add("items", items);
        MaewilLauncher.LOGGER.info("World [" + path + "] items saved!");

        saveJSONFileToSave(path, WorldJsonLoader.BASE_FILES.get("items"), jsonObject);
    }

    public void savePlayerInfo(String username) throws IOException {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("username", username);
        MaewilLauncher.LOGGER.info("Player username saved [" + maewilEngine.getUsername() + "]");
        jsonObject.addProperty("health", maewilEngine.getPlayer().getCurrentHealth());

        JsonArray selected_slots = new JsonArray();
        selected_slots.add(maewilEngine.getPlayer().getInventoryPlayer().getInventorySelectedIndex());
        selected_slots.add(maewilEngine.getPlayer().getInventoryPlayer().getHotbarSelectedIndex());
        jsonObject.add("selected_slots", selected_slots);

        JsonObject inventory = new JsonObject();
        for (int i = 0; i < 12; i++) {
            JsonObject slot = new JsonObject();
            ItemStack stack = maewilEngine.getPlayer().getInventoryPlayer().getSlot(i).getStack();
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
        for (int i = 12; i < 21; i++) {
            JsonObject slot = new JsonObject();
            ItemStack stack = maewilEngine.getPlayer().getInventoryPlayer().getSlot(i).getStack();
            if (stack == null) {
                slot.addProperty("id", "maewil:stone_pick");
                slot.addProperty("count", 1);
            }
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
        Path parentPath = Paths.get(path, fileName).getParent();
        if(!Files.exists(parentPath)) {
            Files.createDirectories(parentPath);
        }
        String trueFilePath = path + "/" + fileName + ".json";
        FileWriter writer = new FileWriter(trueFilePath);
        writer.write(JsonUtils.prettyPrintJSON(data.toString()));
        writer.flush();
        writer.close();
    }
}
