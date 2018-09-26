package coffeecatteam.theultimatetile.jsonparsers.world;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.entities.Entity;
import coffeecatteam.theultimatetile.entities.creatures.EntityCreature;
import coffeecatteam.theultimatetile.entities.creatures.EntityPlayer;
import coffeecatteam.theultimatetile.entities.statics.EntityStatic;
import coffeecatteam.theultimatetile.inventory.items.Item;
import coffeecatteam.theultimatetile.inventory.items.ItemStack;
import coffeecatteam.theultimatetile.jsonparsers.iinterface.IJSONSaver;
import coffeecatteam.theultimatetile.manager.EntityManager;
import coffeecatteam.theultimatetile.tiles.Tile;
import coffeecatteam.theultimatetile.utils.Logger;
import coffeecatteam.theultimatetile.utils.Utils;
import coffeecatteam.theultimatetile.world.World;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileWriter;
import java.io.IOException;

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
        saveWorld(path, world);
        saveObjects(path);
        savePlayerInfo(path);
    }

    public void saveWorld(String path, World world) throws IOException {
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

        JSONObject bg_tile = new JSONObject();
        for (int y = 0; y < world.getHeight(); y++) {
            JSONArray currentRow = new JSONArray();
            for (int x = 0; x < world.getWidth(); x++) {
                currentRow.add(String.valueOf(world.getBGTile(x, y).getId()));
            }
            bg_tile.put("row" + y, currentRow);
        }
        jsonObject.put("bg_tile", bg_tile);

        JSONObject fg_tile = new JSONObject();
        for (int y = 0; y < world.getHeight(); y++) {
            JSONArray currentRow = new JSONArray();
            for (int x = 0; x < world.getWidth(); x++) {
                currentRow.add(String.valueOf(world.getFGTile(x, y).getId()));
            }
            fg_tile.put("row" + y, currentRow);
        }
        jsonObject.put("fg_tile", fg_tile);

        FileWriter file = new FileWriter(path + "/world.json");
        file.write(jsonObject.toJSONString());
        file.flush();
    }

    public void saveObjects(String path) throws IOException {
        JSONObject jsonObject = new JSONObject();

        /*
         * Entities
         */
        JSONObject entities = new JSONObject();
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

            if (staticAmt > 0) {
                JSONArray statics = new JSONArray();
                for (Entity entity : theUltimateTile.getEntityManager().getEntities())
                    if (!(entity instanceof EntityPlayer))
                        if (entity instanceof EntityStatic)
                            saveEntityObj(entity, statics, false);
                entities.put("statics", statics);
                Logger.print("World [" + path + "] static entities saved!");
            }

            if (creatureAmt > 0) {
                JSONArray creatures = new JSONArray();

                for (Entity entity : theUltimateTile.getEntityManager().getEntities())
                    if (!(entity instanceof EntityPlayer))
                        if (entity instanceof EntityCreature)
                            saveEntityObj(entity, creatures, true);
                entities.put("creatures", creatures);
                Logger.print("World [" + path + "] creature entities saved!");
            }
        }
        jsonObject.put("entities", entities);
        Logger.print("World [" + path + "] entities saved!");

        /*
         * Items
         */
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

        FileWriter file = new FileWriter(path + "/objects.json");
        file.write(jsonObject.toJSONString());
        file.flush();
    }

    public void savePlayerInfo(String path) throws IOException {
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

        FileWriter file = new FileWriter(path + "/player_info.json");
        file.write(jsonObject.toJSONString());
        file.flush();
    }

    private void saveEntityObj(Entity entity, JSONArray entitiesArray, boolean isCreature) {
        JSONObject entityObj = new JSONObject();
        entityObj.put("id", entity.getId());

        JSONArray pos = new JSONArray();
        pos.add(0, String.valueOf(entity.getX() / Tile.TILE_WIDTH) + "f");
        pos.add(1, String.valueOf(entity.getY() / Tile.TILE_HEIGHT) + "f");
        entityObj.put("pos", pos);

        if (isCreature) {
            if (entity.getDataTags() != null) {
                JSONArray dataTags = new JSONArray();
                for (String tag : entity.getDataTags())
                    dataTags.add(tag);
                entityObj.put("dataTags", dataTags);
            }
        }

        if (entity.getCurrentHealth() < entity.getMaxHealth())
            entityObj.put("health", String.valueOf(entity.getCurrentHealth()));

        entitiesArray.add(entityObj);
    }
}
