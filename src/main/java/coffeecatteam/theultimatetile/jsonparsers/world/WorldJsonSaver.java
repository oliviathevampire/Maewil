package coffeecatteam.theultimatetile.jsonparsers.world;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.entities.Entity;
import coffeecatteam.theultimatetile.entities.creatures.EntityCreature;
import coffeecatteam.theultimatetile.entities.creatures.EntityPlayer;
import coffeecatteam.theultimatetile.entities.statics.EntityStatic;
import coffeecatteam.theultimatetile.inventory.items.ItemStack;
import coffeecatteam.theultimatetile.jsonparsers.iinterface.IJSONSaver;
import coffeecatteam.theultimatetile.tiles.Tile;
import coffeecatteam.theultimatetile.utils.Logger;
import coffeecatteam.theultimatetile.world.World;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class WorldJsonSaver implements IJSONSaver {

    private String path;
    private World world;
    private TheUltimateTile theUltimateTile;

    public WorldJsonSaver(String path, World world, TheUltimateTile theUltimateTile) {
        this.path = path;
        this.world = world;
        this.theUltimateTile = theUltimateTile;
    }

    @Override
    public void save() throws IOException {
        Logger.print("\nSaving current world!");
        saveWorldInfo(world);
        Logger.print("World [" + world.getName() + "] info saved!\n");

        saveTiles();
        Logger.print("World [" + world.getName() + "] tiles saved!\n");

        saveEntities();
        Logger.print("World [" + world.getName() + "] entities saved!\n");

        saveItems();
        Logger.print("World [" + world.getName() + "] items saved!\n");

        savePlayerInfo();
        Logger.print("World [" + world.getName() + "] player info saved!\n");
    }

    public void saveWorldInfo(World world) throws IOException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("name", world.getName());

        JSONArray size = new JSONArray();
        size.add(0, world.getWidth());
        size.add(1, world.getHeight());
        jsonObject.put("size", size);

        JSONArray spawn = new JSONArray();
        spawn.add(0, String.valueOf(theUltimateTile.getEntityManager().getPlayer().getX() / Tile.TILE_WIDTH + "f"));
        spawn.add(1, String.valueOf(theUltimateTile.getEntityManager().getPlayer().getY() / Tile.TILE_HEIGHT + "f"));
        jsonObject.put("spawn", spawn);

        saveJSONFile(WorldJsonLoader.BASE_FILES.get("world"), jsonObject);
    }

    public void saveTiles() throws IOException {
        JSONObject jsonObjectBG = new JSONObject();
        JSONObject jsonObjectFG = new JSONObject();

        JSONObject bg_tile = new JSONObject();
        for (int y = 0; y < world.getHeight(); y++) {
            JSONArray chunk = new JSONArray();
            for (int x = 0; x < world.getWidth(); x++) {
                saveTile(chunk, true, x, y);
            }
            bg_tile.put("chunk" + y, chunk);
        }
        jsonObjectBG.put("bg_tile", bg_tile);

        JSONObject fg_tile = new JSONObject();
        for (int y = 0; y < world.getHeight(); y++) {
            JSONArray chunk = new JSONArray();
            for (int x = 0; x < world.getWidth(); x++) {
                saveTile(chunk, false, x, y);
            }
            fg_tile.put("chunk" + y, chunk);
        }
        jsonObjectFG.put("fg_tile", fg_tile);

        saveJSONFile(WorldJsonLoader.BASE_FILES.get("tile_bg"), jsonObjectBG);
        saveJSONFile(WorldJsonLoader.BASE_FILES.get("tile_fg"), jsonObjectFG);
    }

    private void saveTile(JSONArray chunk, boolean bg, int x, int y) {
        JSONObject tileObj = new JSONObject();
        tileObj.put("id", (bg ? world.getBGTile(x, y) : world.getFGTile(x, y)).getId());
        tileObj.put("x", String.valueOf(x));
        tileObj.put("y", String.valueOf(y));
        chunk.add(tileObj);
    }

    public void saveEntities() throws IOException {
        JSONObject jsonObjectStatic = new JSONObject();
        JSONObject jsonObjectCreature = new JSONObject();

        int entAmt = 0;
        for (Entity entity : theUltimateTile.getEntityManager().getEntities())
            if (!(entity instanceof EntityPlayer))
                entAmt++;
        if (entAmt > 0) {
            int staticAmt = 0;
            int creatureAmt = 0;
            for (Entity entity : theUltimateTile.getEntityManager().getEntities()) {
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
                for (Entity entity : theUltimateTile.getEntityManager().getEntities())
                    if (!(entity instanceof EntityPlayer))
                        if (entity instanceof EntityStatic)
                            saveEntityObj(entity, statics);
                jsonObjectStatic.put("statics", statics);
                Logger.print("World [" + path + "] static entities saved!");
            }

            // Creature
            if (creatureAmt > 0) {
                JSONArray creatures = new JSONArray();

                for (Entity entity : theUltimateTile.getEntityManager().getEntities())
                    if (!(entity instanceof EntityPlayer))
                        if (entity instanceof EntityCreature)
                            saveEntityObj(entity, creatures);
                jsonObjectCreature.put("creatures", creatures);
                Logger.print("World [" + path + "] creature entities saved!");
            }
        }
        Logger.print("World [" + path + "] entities saved!");

        saveJSONFile(WorldJsonLoader.BASE_FILES.get("entity_s"), jsonObjectStatic);
        saveJSONFile(WorldJsonLoader.BASE_FILES.get("entity_c"), jsonObjectCreature);
    }

    public void saveItems() throws IOException {
        JSONObject jsonObject = new JSONObject();

        JSONArray items = new JSONArray();
        for (ItemStack stack : theUltimateTile.getItemManager().getItems()) {
            JSONObject itemObj = new JSONObject();
            itemObj.put("id", stack.getId());

            JSONArray pos = new JSONArray();
            pos.add(0, String.valueOf(stack.getItem().getX() / Tile.TILE_WIDTH) + "f");
            pos.add(1, String.valueOf(stack.getItem().getY() / Tile.TILE_HEIGHT) + "f");
            itemObj.put("pos", pos);

            if (stack.getCount() > 1) {
                itemObj.put("count", String.valueOf(stack.getCount()));
            }
            items.add(itemObj);
        }
        jsonObject.put("items", items);
        Logger.print("World [" + path + "] items saved!");

        saveJSONFile(WorldJsonLoader.BASE_FILES.get("items"), jsonObject);
    }

    public void savePlayerInfo() throws IOException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("username", theUltimateTile.getEntityManager().getPlayer().getUsername());
        jsonObject.put("health", String.valueOf(theUltimateTile.getEntityManager().getPlayer().getCurrentHealth()));
        jsonObject.put("glubel", String.valueOf(theUltimateTile.getEntityManager().getPlayer().getGlubel()));
        jsonObject.put("lvl", String.valueOf(theUltimateTile.getEntityManager().getPlayer().getLvl()));

        JSONArray selected_slots = new JSONArray();
        selected_slots.add(0, theUltimateTile.getEntityManager().getPlayer().getInventoryPlayer().getInventorySelectedIndex());
        selected_slots.add(1, theUltimateTile.getEntityManager().getPlayer().getInventoryPlayer().getHotbarSelectedIndex());
        jsonObject.put("selected_slots", selected_slots);

        JSONObject inventory = new JSONObject();
        for (int i = 0; i < 12; i++) {
            JSONObject slot = new JSONObject();
            ItemStack stack = theUltimateTile.getEntityManager().getPlayer().getInventoryPlayer().getSlot(i).getStack();
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
            ItemStack stack = theUltimateTile.getEntityManager().getPlayer().getInventoryPlayer().getSlot(i).getStack();
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

        saveJSONFile(WorldJsonLoader.BASE_FILES.get("player"), jsonObject);
    }

    private void saveEntityObj(Entity entity, JSONArray entitiesArray) {
        JSONObject entityObj = new JSONObject();
        entityObj.put("id", entity.getId());

        JSONArray pos = new JSONArray();
        pos.add(0, String.valueOf(entity.getX() / Tile.TILE_WIDTH) + "f");
        pos.add(1, String.valueOf(entity.getY() / Tile.TILE_HEIGHT) + "f");
        entityObj.put("pos", pos);

        Map<String, String> tags = entity.saveTags();
        JSONObject tagsObj = new JSONObject(tags);
        for (Object key : tags.keySet()) {
            tagsObj.put(String.valueOf(key), tags.get(String.valueOf(key)));
        }
        entityObj.put("tags", tagsObj);

        if (entity.getCurrentHealth() < entity.getMaxHealth())
            entityObj.put("health", String.valueOf(entity.getCurrentHealth()));

        entitiesArray.add(entityObj);
    }

    private void saveJSONFile(String name, JSONObject data) throws IOException {
        FileWriter file = new FileWriter(path + "/" + name + ".json");
        file.write(data.toJSONString());
        file.flush();
    }
}
