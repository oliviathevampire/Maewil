package coffeecatteam.theultimatetile.game.tags.supers;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.tags.TagCompound;
import coffeecatteam.theultimatetile.game.tags.TagIntArray;
import coffeecatteam.theultimatetile.game.tags.TagList;
import coffeecatteam.theultimatetile.game.tags.TagString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author CoffeeCatRailway
 * Created: 15/12/2018
 */
public abstract class TagBase {

    public static final Map<String, Integer> TAG_IDS = new HashMap<>();

    static {
        TAG_IDS.put("END", 0);
        TAG_IDS.put("COMPOUND", 1);
        TAG_IDS.put("STRING", 2);
        TAG_IDS.put("INT", 3);
        TAG_IDS.put("FLOAT", 4);
        TAG_IDS.put("DOUBLE", 5);
        TAG_IDS.put("INT_ARRAY", 6);
        TAG_IDS.put("FLOAT_ARRAY", 7);
        TAG_IDS.put("DOUBLE_ARRAY", 8);
        TAG_IDS.put("LIST", 9);
        TAG_IDS.put("BOOLEAN", 10);
    }

    public static String getTagTypeName(int id) {
        switch (id) {
            case 0:
                return "TAG_" + TAG_IDS.keySet().toArray()[id];
            case 1:
                return "TAG_" + TAG_IDS.keySet().toArray()[id];
            case 2:
                return "TAG_" + TAG_IDS.keySet().toArray()[id];
            case 3:
                return "TAG_" + TAG_IDS.keySet().toArray()[id];
            case 4:
                return "TAG_" + TAG_IDS.keySet().toArray()[id];
            case 5:
                return "TAG_" + TAG_IDS.keySet().toArray()[id];
            case 6:
                return "TAG_" + TAG_IDS.keySet().toArray()[id];
            case 7:
                return "TAG_" + TAG_IDS.keySet().toArray()[id];
            case 8:
                return "TAG_" + TAG_IDS.keySet().toArray()[id];
            case 9:
                return "TAG_" + TAG_IDS.keySet().toArray()[id];
            case 10:
                return "TAG_" + TAG_IDS.keySet().toArray()[id];
            case 99:
                return "Any Numeric Tag";
            default:
                return "UNKNOWN";
        }
    }

    /**
     * This method is used to test the tag system and make sure it works fine.
     */
    public static void TEST() {
        TagCompound baseCompound = new TagCompound();

        TagCompound entity = new TagCompound();
        entity.setString("id", "pig");

        TagIntArray pos = new TagIntArray(new int[]{7, 10});
        entity.setTag("pos", pos);

        entity.setInteger("count", 3);

        TagCompound tags = new TagCompound();

        List<TagBase> crops = new ArrayList<>();
        crops.add(new TagString("crop_corn"));
        crops.add(new TagString("crop_wheat"));
        crops.add(new TagString("crop_potato"));
        crops.add(new TagString("crop_carrot"));
        crops.add(new TagString("crop_tomato"));
        TagList eatCrops = new TagList(crops);
        tags.setTag("eatCrops", eatCrops);

        tags.setBoolean("fleePlayer", true);

        entity.setTag("tags", tags);
        baseCompound.setTag("entity", entity);

        Engine.getEngine().getLogger().print(baseCompound.toString());
        Engine.getEngine().setRunning(false);
    }

    public abstract String toString();

    /**
     * Gets the type byte for the tag.
     */
    public abstract int getId();

    /**
     * Creates a clone of the tag.
     */
    public abstract TagBase copy();

    /**
     * Return whether this compound has no tags.
     */
    public boolean hasNoTags() {
        return false;
    }

    public boolean equals(Object obj) {
        return obj instanceof TagBase && this.getId() == ((TagBase) obj).getId();
    }

    public String getString() {
        return this.toString();
    }
}
