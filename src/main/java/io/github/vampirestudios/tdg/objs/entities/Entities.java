package io.github.vampirestudios.tdg.objs.entities;

import coffeecatteam.coffeecatutils.ArgUtils;
import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.coffeecatutils.position.Vector2D;
import com.google.gson.JsonObject;
import io.github.vampirestudios.tdg.objs.EntityDataParser;
import io.github.vampirestudios.tdg.objs.entities.creatures.PlayerEntity;
import io.github.vampirestudios.tdg.objs.entities.creatures.passive.CowEntity;
import io.github.vampirestudios.tdg.objs.entities.creatures.passive.FoxEntity;
import io.github.vampirestudios.tdg.objs.entities.creatures.passive.PigEntity;
import io.github.vampirestudios.tdg.objs.entities.creatures.passive.SheepEntity;
import io.github.vampirestudios.tdg.objs.entities.creatures.undead.BouncerEntity;
import io.github.vampirestudios.tdg.objs.entities.creatures.undead.SkeletonEntity;
import io.github.vampirestudios.tdg.objs.entities.creatures.undead.ThingEntity;
import io.github.vampirestudios.tdg.objs.entities.creatures.undead.ZombieEntity;
import io.github.vampirestudios.tdg.objs.entities.statics.ExtraLifeEntity;
import io.github.vampirestudios.tdg.objs.entities.statics.UltimateTileEntity;
import io.github.vampirestudios.tdg.objs.entities.statics.interactable.CampfireEntity;
import io.github.vampirestudios.tdg.objs.entities.statics.interactable.MerchantStallEntity;
import io.github.vampirestudios.tdg.objs.entities.statics.nature.BushEntity;
import io.github.vampirestudios.tdg.objs.entities.statics.nature.CropEntity;
import io.github.vampirestudios.tdg.objs.entities.statics.nature.RockEntity;
import io.github.vampirestudios.tdg.objs.entities.statics.nature.TreeEntity;
import io.github.vampirestudios.tdg.objs.items.ItemStack;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.start.MaewilLauncher;
import io.github.vampirestudios.tdg.tags.CompoundTag;
import io.github.vampirestudios.tdg.tags.JsonToTag;
import io.github.vampirestudios.tdg.tags.TagException;

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
    public static PlayerEntity PLAYER;

    /*
     * Static
     */
    public static Entity TREE_SMALL, TREE_MEDIUM, TREE_LARGE, TREE_EXTRA_LARGE;
    public static Entity ROCK_SMALL, ROCK_MEDIUM;
    public static Entity BUSH_SMALL, BUSH_MEDIUM;

    public static Entity CROP_CARROT, CROP_WHEAT, CROP_POTATO, CROP_CORN;

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

    public static void init(MaewilEngine maewilEngine) throws IOException {
        EntityDataParser parser = new EntityDataParser();

        /*
         * Player
         */
        String username = ArgUtils.hasArgument("-username") ? ArgUtils.getArgument("-username") : "";
        register(PLAYER = (PlayerEntity) parser.loadData(new PlayerEntity(maewilEngine, username)));
        maewilEngine.getEntityManager().setPlayer(PLAYER);

        /*
         * Static
         */
        register(TREE_SMALL = parser.loadData(new TreeEntity(maewilEngine, TreeEntity.TreeType.SMALL)));
        register(TREE_MEDIUM = parser.loadData(new TreeEntity(maewilEngine, TreeEntity.TreeType.MEDIUM)));
        register(TREE_LARGE = parser.loadData(new TreeEntity(maewilEngine, TreeEntity.TreeType.LARGE)));
        register(TREE_EXTRA_LARGE = parser.loadData(new TreeEntity(maewilEngine, TreeEntity.TreeType.EXTRA_LARGE)));

        register(ROCK_SMALL = parser.loadData(new RockEntity(maewilEngine, RockEntity.RockType.SMALL)));
        register(ROCK_MEDIUM = parser.loadData(new RockEntity(maewilEngine, RockEntity.RockType.MEDIUM)));

        register(BUSH_SMALL = parser.loadData(new BushEntity(maewilEngine, RockEntity.RockType.SMALL)));
        register(BUSH_MEDIUM = parser.loadData(new BushEntity(maewilEngine, RockEntity.RockType.MEDIUM)));

        register(CROP_CARROT = parser.loadData(new CropEntity(maewilEngine, CropEntity.CropType.CARROT)));
        register(CROP_WHEAT = parser.loadData(new CropEntity(maewilEngine, CropEntity.CropType.WHEAT)));
        register(CROP_POTATO = parser.loadData(new CropEntity(maewilEngine, CropEntity.CropType.POTATO)));
        register(CROP_CORN = parser.loadData(new CropEntity(maewilEngine, CropEntity.CropType.CORN)));

        register(ULTIMATE = parser.loadData(new UltimateTileEntity(maewilEngine)));
        register(EXTRA_LIFE = parser.loadData(new ExtraLifeEntity(maewilEngine)));
        register(SHOP = parser.loadData(new MerchantStallEntity(maewilEngine)));
        register(CAMPFIRE = parser.loadData(new CampfireEntity(maewilEngine)));

        /*
         * Creature
         */
        // Undead
        register(ZOMBIE = parser.loadData(new ZombieEntity(maewilEngine)));
        register(SKELETON = parser.loadData(new SkeletonEntity(maewilEngine)));
        register(BOUNCER = parser.loadData(new BouncerEntity(maewilEngine)));
        register(THING = parser.loadData(new ThingEntity(maewilEngine)));

        // Living
        register(PIG = parser.loadData(new PigEntity(maewilEngine)));
        register(COW = parser.loadData(new CowEntity(maewilEngine)));
        register(SHEEP = parser.loadData(new SheepEntity(maewilEngine)));
        register(FOX = parser.loadData(new FoxEntity(maewilEngine)));

        /*
         * Other
         */
        register(ITEM = parser.loadData(new ItemEntity(maewilEngine, (ItemStack) null))); // PLACE HOLDER IF NEEDED

        MaewilLauncher.LOGGER.info("Entities registered!");
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

    public static Entity jsonToEntity(JsonObject json) {
        Entity entity = getEntityById(json.get("id").getAsString());
        System.out.println(json.get("id").getAsString());

        JsonObject posObj = (JsonObject) json.get("pos");
        Vector2D position = new Vector2D();
        position.x = NumberUtils.parseDouble(posObj.get("x"));
        position.y = NumberUtils.parseDouble(posObj.get("y"));
        entity.setPosition(position);

        CompoundTag tags;
        try {
            JsonObject tagsJson = (JsonObject) json.get("tags");
            tags = JsonToTag.getTagFromJson(tagsJson.getAsString());
        } catch (TagException e) {
            MaewilLauncher.LOGGER.error(e);
            tags = new CompoundTag();
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

    public static JsonObject entityToJson(Entity entity) {
        JsonObject json = new JsonObject();

        json.addProperty("id", entity.getId());
        JsonObject pos = new JsonObject();
        pos.addProperty("x", entity.getPosition().x);
        pos.addProperty("y", entity.getPosition().y);
        json.add("pos", pos);
        /*try {
            json.add("tags", new JSONParser().parse(entity.getTags().toString()));
        } catch (ParseException e) {
            MaewilLauncher.LOGGER.error(e);
            json.add("tags", new JsonObject());
        }*/
        json.add("tags", new JsonObject());
        json.addProperty("health", entity.getCurrentHealth());
        json.addProperty("maxHealth", entity.getMaxHealth());
        json.addProperty("hitType", entity.getHitType().getIndex());
        json.addProperty("currentTextureId", entity.getCurrentTextureId());

        return json;
    }
}
