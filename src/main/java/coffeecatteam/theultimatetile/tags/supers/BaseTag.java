package coffeecatteam.theultimatetile.tags.supers;

import coffeecatteam.theultimatetile.start.TutLauncher;
import coffeecatteam.theultimatetile.tags.*;
import coffeecatteam.theultimatetile.tags.ListTag;
import coffeecatteam.theultimatetile.tags.StringTag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author CoffeeCatRailway
 * Created: 15/12/2018
 */
public abstract class BaseTag {

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
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
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
        CompoundTag baseCompound = new CompoundTag();

        CompoundTag entity = new CompoundTag();
        entity.setString("id", "pig");

        IntArrayTag pos = new IntArrayTag(new int[]{7, 10});
        entity.setTag("pos", pos);

        entity.setInteger("count", 3);

        CompoundTag tags = new CompoundTag();

        List<BaseTag> crops = new ArrayList<>();
        crops.add(new StringTag("crop_corn"));
        crops.add(new StringTag("crop_wheat"));
        crops.add(new StringTag("crop_potato"));
        crops.add(new StringTag("crop_carrot"));
        crops.add(new StringTag("crop_tomato"));
        ListTag eatCrops = new ListTag(crops);
        tags.setTag("eatCrops", eatCrops);

        tags.setBoolean("fleePlayer", true);

        entity.setTag("tags", tags);
        baseCompound.setTag("entity", entity);

        TutLauncher.LOGGER.error(baseCompound.toString());
        System.exit(0);
    }

    public abstract String toString();

    /**
     * Gets the type byte for the tag.
     */
    public abstract int getId();

    /**
     * Creates a clone of the tag.
     */
    public abstract BaseTag copy();

    /**
     * Return whether this compound has no tags.
     */
    public boolean hasNoTags() {
        return false;
    }

    public boolean equals(Object obj) {
        return obj instanceof BaseTag && this.getId() == ((BaseTag) obj).getId();
    }

    public String getString() {
        return this.toString();
    }
}
