package coffeecatteam.theultimatetile.objs.entities;

import coffeecatteam.coffeecatutils.ArgUtils;
import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.TutLauncher;
import coffeecatteam.theultimatetile.objs.EntityDataParser;
import coffeecatteam.theultimatetile.objs.entities.creatures.EntityPlayer;
import coffeecatteam.theultimatetile.objs.entities.creatures.passive.EntityCow;
import coffeecatteam.theultimatetile.objs.entities.creatures.passive.EntityFox;
import coffeecatteam.theultimatetile.objs.entities.creatures.passive.EntityPig;
import coffeecatteam.theultimatetile.objs.entities.creatures.passive.EntitySheep;
import coffeecatteam.theultimatetile.objs.entities.creatures.undead.EntityBouncer;
import coffeecatteam.theultimatetile.objs.entities.creatures.undead.EntitySkeleton;
import coffeecatteam.theultimatetile.objs.entities.creatures.undead.EntityThing;
import coffeecatteam.theultimatetile.objs.entities.creatures.undead.EntityZombie;
import coffeecatteam.theultimatetile.objs.entities.statics.EntityExtraLife;
import coffeecatteam.theultimatetile.objs.entities.statics.EntityUltimateTile;
import coffeecatteam.theultimatetile.objs.entities.statics.interactable.EntityCampfire;
import coffeecatteam.theultimatetile.objs.entities.statics.interactable.EntityShopStall;
import coffeecatteam.theultimatetile.objs.entities.statics.nature.EntityBush;
import coffeecatteam.theultimatetile.objs.entities.statics.nature.EntityCrop;
import coffeecatteam.theultimatetile.objs.entities.statics.nature.EntityRock;
import coffeecatteam.theultimatetile.objs.entities.statics.nature.EntityTree;
import coffeecatteam.theultimatetile.objs.items.ItemStack;
import coffeecatteam.theultimatetile.tags.JsonToTag;
import coffeecatteam.theultimatetile.tags.TagCompound;
import coffeecatteam.theultimatetile.tags.TagException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author CoffeeCatRailway
 * Created: 24/02/2019
 */
public class Entities {

    public static final HashMap<String, Entity> ENTITIES = new HashMap<>();

    /*
     * Player
     */
    public static EntityPlayer PLAYER;

    /*
     * Static
     */
    public static Entity TREE_SMALL, TREE_MEDIUM, TREE_LARGE, TREE_EXTRA_LARGE;
    public static Entity ROCK_SMALL, ROCK_MEDIUM;
    public static Entity BUSH_SMALL, BUSH_MEDIUM;

    public static Entity CROP_CARROT, CROP_WHEAT, CROP_POTATO, CROP_TOMATO, CROP_CORN;

    public static Entity ULTIMATE;
    public static Entity EXTRA_LIFE;
    public static Entity SHOP;
    public static Entity CAMPFIRE;

    /*
     * Creature
     */
    // Undead
    public static Entity ZOMBIE, SKELETON, BOUNCER, THING;

    // Living
    public static Entity PIG, COW, SHEEP, FOX;

    /*
     * Other
     */
    public static Entity ITEM;

    public static void init(TutEngine tutEngine) throws IOException, ParseException {
        EntityDataParser parser = new EntityDataParser();

        /*
         * Player
         */
        String username = ArgUtils.hasArgument(tutEngine.getArgs(), "-username") ? ArgUtils.getArgument(tutEngine.getArgs(), "-username") : "";
        register(PLAYER = (EntityPlayer) parser.loadData(new EntityPlayer(tutEngine, username)));
        tutEngine.getEntityManager().setPlayer(PLAYER);

        /*
         * Static
         */
        register(TREE_SMALL = parser.loadData(new EntityTree(tutEngine, EntityTree.TreeType.SMALL)));
        register(TREE_MEDIUM = parser.loadData(new EntityTree(tutEngine, EntityTree.TreeType.MEDIUM)));
        register(TREE_LARGE = parser.loadData(new EntityTree(tutEngine, EntityTree.TreeType.LARGE)));
        register(TREE_EXTRA_LARGE = parser.loadData(new EntityTree(tutEngine, EntityTree.TreeType.EXTRA_LARGE)));

        register(ROCK_SMALL = parser.loadData(new EntityRock(tutEngine, EntityRock.RockType.SMALL)));
        register(ROCK_MEDIUM = parser.loadData(new EntityRock(tutEngine, EntityRock.RockType.MEDIUM)));

        register(BUSH_SMALL = parser.loadData(new EntityBush(tutEngine, EntityRock.RockType.SMALL)));
        register(BUSH_MEDIUM = parser.loadData(new EntityBush(tutEngine, EntityRock.RockType.MEDIUM)));

        register(CROP_CARROT = parser.loadData(new EntityCrop(tutEngine, EntityCrop.CropType.CARROT)));
        register(CROP_WHEAT = parser.loadData(new EntityCrop(tutEngine, EntityCrop.CropType.WHEAT)));
        register(CROP_POTATO = parser.loadData(new EntityCrop(tutEngine, EntityCrop.CropType.POTATO)));
        register(CROP_TOMATO = parser.loadData(new EntityCrop(tutEngine, EntityCrop.CropType.TOMATO)));
        register(CROP_CORN = parser.loadData(new EntityCrop(tutEngine, EntityCrop.CropType.CORN)));

        register(ULTIMATE = parser.loadData(new EntityUltimateTile(tutEngine)));
        register(EXTRA_LIFE = parser.loadData(new EntityExtraLife(tutEngine)));
        register(SHOP = parser.loadData(new EntityShopStall(tutEngine)));
        register(CAMPFIRE = parser.loadData(new EntityCampfire(tutEngine)));

        /*
         * Creature
         */
        // Undead
        register(ZOMBIE = parser.loadData(new EntityZombie(tutEngine)));
        register(SKELETON = parser.loadData(new EntitySkeleton(tutEngine)));
        register(BOUNCER = parser.loadData(new EntityBouncer(tutEngine)));
        register(THING = parser.loadData(new EntityThing(tutEngine)));

        // Living
        register(PIG = parser.loadData(new EntityPig(tutEngine)));
        register(COW = parser.loadData(new EntityCow(tutEngine)));
        register(SHEEP = parser.loadData(new EntitySheep(tutEngine)));
        register(FOX = parser.loadData(new EntityFox(tutEngine)));

        /*
         * Other
         */
        register(ITEM = parser.loadData(new EntityItem(tutEngine, (ItemStack) null))); // PLACE HOLDER IF NEEDED

        TutLauncher.LOGGER.info("Entities registered!");
    }

    private static void register(Entity entity) {
        ENTITIES.put(entity.getId(), entity);
    }

    public static Entity getEntityById(String id) {
        return ENTITIES.get(id).newCopy();
    }

    public static List<Entity> getEntities() {
        return new ArrayList<>(ENTITIES.values());
    }

    public static Entity jsonToEntity(JSONObject json) {
        Entity entity = getEntityById((String) json.get("id"));

        JSONObject posObj = (JSONObject) json.get("pos");
        Vector2D position = new Vector2D();
        position.x = NumberUtils.parseDouble(posObj.get("x"));
        position.y = NumberUtils.parseDouble(posObj.get("y"));
        entity.setPosition(position);

        TagCompound tags;
        try {
            JSONObject tagsJson = (JSONObject) json.get("tags");
            tags = JsonToTag.getTagFromJson(tagsJson.toString());
        } catch (TagException e) {
            TutLauncher.LOGGER.error(e);
            tags = new TagCompound();
        }
        entity.setTags(tags);

        int health = NumberUtils.parseInt(json.get("health"));
        entity.setCurrentHealth(health);

        int maxHealth = NumberUtils.parseInt(json.get("maxHealth"));
        entity.setMaxHealth(maxHealth);

        int hitType = NumberUtils.parseInt(json.get("hitType"));
        entity.setHitType(Entity.HitType.getHitType(hitType));

        String textureId = String.valueOf(json.get("currentTextureId"));
        entity.setCurrentTextureId(textureId);

        return entity.newCopy();
    }

    public static JSONObject entityToJson(Entity entity) {
        JSONObject json = new JSONObject();

        json.put("id", entity.getId());
        JSONObject pos = new JSONObject();
        pos.put("x", entity.getPosition().x);
        pos.put("y", entity.getPosition().y);
        json.put("pos", pos);
        try {
            json.put("tags", new JSONParser().parse(entity.getTags().toString()));
        } catch (ParseException e) {
            TutLauncher.LOGGER.error(e);
            json.put("tags", new JSONObject());
        }
        json.put("health", entity.getCurrentHealth());
        json.put("maxHealth", entity.getMaxHealth());
        json.put("hitType", entity.getHitType().getIndex());
        json.put("currentTextureId", entity.getCurrentTextureId());

        return json;
    }
}
