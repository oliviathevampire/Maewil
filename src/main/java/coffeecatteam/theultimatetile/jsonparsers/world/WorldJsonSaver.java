package coffeecatteam.theultimatetile.jsonparsers.world;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.objs.entities.creatures.EntityCreature;
import coffeecatteam.theultimatetile.objs.entities.creatures.EntityPlayer;
import coffeecatteam.theultimatetile.objs.entities.statics.EntityStatic;
import coffeecatteam.theultimatetile.objs.items.ItemStack;
import coffeecatteam.theultimatetile.objs.tiles.Tile;
import coffeecatteam.theultimatetile.world.World;
import coffeecatteam.theultimatetile.utils.iinterface.IJSONSaver;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
        tutEngine.getLogger().print("Saving current world!");
        saveWorldInfo(world);
        tutEngine.getLogger().print("World [" + world.getWorldName() + "] info saved!");

        saveTiles(world.getWidth(), world.getHeight(), world.getBg_tiles(), world.getFg_tiles(), path, WorldJsonLoader.BASE_FILES.get("tile_bg"), WorldJsonLoader.BASE_FILES.get("tile_fg"));
        tutEngine.getLogger().print("World [" + world.getWorldName() + "] tiles saved!");

        /*
         * TEMP!!!
         */
        if (tutEngine instanceof TutEngine) {
            saveEntities();
            tutEngine.getLogger().print("World [" + world.getWorldName() + "] entities saved!");

            saveItems();
            tutEngine.getLogger().print("World [" + world.getWorldName() + "] ITEMS saved!");

            savePlayerInfo(username);
            tutEngine.getLogger().print("World [" + world.getWorldName() + "] player info saved!");
        }
    }

    @Override
    public void save() throws IOException {
        save(tutEngine.getUsername());
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
        if (tutEngine instanceof TutEngine) {
            spawnX = tutEngine.getEntityManager().getPlayer().getX() / Tile.TILE_WIDTH;
            spawnY = tutEngine.getEntityManager().getPlayer().getY() / Tile.TILE_HEIGHT;
        } else {
            spawnX = world.getSpawnX();
            spawnY = world.getSpawnY();
        }
        spawn.add(0, spawnX);
        spawn.add(1, spawnY);
        jsonObject.put("spawn", spawn);

        saveJSONFileToSave(path, WorldJsonLoader.BASE_FILES.get("world"), jsonObject);
    }

    public void saveTiles(int width, int height, Tile[][] bgTiles, Tile[][] fgTiles, String savePath, String bgFileName, String fgFileName) throws IOException {
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
        tutEngine.getLogger().print("Saved bg tiles");

        JSONObject fg_tile = new JSONObject();
        for (int y = 0; y < height; y++) {
            JSONArray chunk = new JSONArray();
            for (int x = 0; x < width; x++) {
                saveTile(chunk, fgTiles[x][y], x, y);
            }
            fg_tile.put("chunk" + y, chunk);
        }
        jsonObjectFG.put("fg_tile", fg_tile);
        tutEngine.getLogger().print("Saved fg tiles");

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
            if (!(entity instanceof EntityPlayer))
                entAmt++;
        if (entAmt > 0) {
            int staticAmt = 0;
            int creatureAmt = 0;
            for (Entity entity : tutEngine.getEntityManager().getEntities()) {
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
            JSONArray statics = new JSONArray();
            if (staticAmt > 0) {
                for (Entity entity : tutEngine.getEntityManager().getEntities())
                    if (!(entity instanceof EntityPlayer))
                        if (entity instanceof EntityStatic)
                            saveEntityObj(entity, statics);
                tutEngine.getLogger().print("World [" + path + "] static entities saved!");
            }
            jsonObjectStatic.put("statics", statics);

            // Creature
            JSONArray creatures = new JSONArray();
            if (creatureAmt > 0) {

                for (Entity entity : tutEngine.getEntityManager().getEntities())
                    if (!(entity instanceof EntityPlayer))
                        if (entity instanceof EntityCreature)
                            saveEntityObj(entity, creatures);
                tutEngine.getLogger().print("World [" + path + "] creature entities saved!");
            }
            jsonObjectCreature.put("creatures", creatures);
        }
        tutEngine.getLogger().print("World [" + path + "] entities saved!");

        saveJSONFileToSave(path, WorldJsonLoader.BASE_FILES.get("entity_s"), jsonObjectStatic);
        saveJSONFileToSave(path, WorldJsonLoader.BASE_FILES.get("entity_c"), jsonObjectCreature);
    }

    public void saveItems() throws IOException {
        JSONObject jsonObject = new JSONObject();

        JSONArray items = new JSONArray();
        for (ItemStack stack : tutEngine.getItems().getITEMS()) {
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
        jsonObject.put("ITEMS", items);
        tutEngine.getLogger().print("World [" + path + "] ITEMS saved!");

        saveJSONFileToSave(path, WorldJsonLoader.BASE_FILES.get("ITEMS"), jsonObject);
    }

    public void savePlayerInfo(String username) throws IOException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("username", username);
        tutEngine.getLogger().print("Player username saved [" + tutEngine.getUsername() + "]");
        jsonObject.put("health", tutEngine.getEntityManager().getPlayer().getCurrentHealth());
        jsonObject.put("glubel", tutEngine.getEntityManager().getPlayer().getGlubel());
        jsonObject.put("lvl", tutEngine.getEntityManager().getPlayer().getLvl());

        JSONArray selected_slots = new JSONArray();
        selected_slots.add(0, tutEngine.getEntityManager().getPlayer().getInventoryPlayer().getInventorySelectedIndex());
        selected_slots.add(1, tutEngine.getEntityManager().getPlayer().getInventoryPlayer().getHotbarSelectedIndex());
        jsonObject.put("selected_slots", selected_slots);

        JSONObject inventory = new JSONObject();
        for (int i = 0; i < 12; i++) {
            JSONObject slot = new JSONObject();
            ItemStack stack = tutEngine.getEntityManager().getPlayer().getInventoryPlayer().getSlot(i).getStack();
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
            ItemStack stack = tutEngine.getEntityManager().getPlayer().getInventoryPlayer().getSlot(i).getStack();
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

    private void saveEntityObj(Entity entity, JSONArray entitiesArray) {
        JSONObject entityObj = new JSONObject();
        entityObj.put("id", entity.getId());

        JSONArray pos = new JSONArray();
        pos.add(0, entity.getX() / Tile.TILE_WIDTH);
        pos.add(1, entity.getY() / Tile.TILE_HEIGHT);
        entityObj.put("pos", pos);

        try {
            JSONParser parser = new JSONParser();
            if (!entity.getTags().hasNoTags()) {
                entityObj.put("tags", parser.parse(entity.getTags().toString()));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (entity.getCurrentHealth() < entity.getMaxHealth())
            entityObj.put("health", entity.getCurrentHealth());

        entitiesArray.add(entityObj);
    }

    private void saveJSONFileToSave(String path, String fileName, JSONObject data) throws IOException {
        saveJSONFileToPath(path, fileName, data);
    }

    private void saveJSONFileToPath(String path, String fileName, JSONObject data) throws IOException {
        String trueFilePath = path + "/" + fileName + ".json";
        FileWriter writer = new FileWriter(trueFilePath);
        writer.write(data.toJSONString());
        writer.flush();
    }
}
