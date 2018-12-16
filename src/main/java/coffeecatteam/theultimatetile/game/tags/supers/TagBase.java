package coffeecatteam.theultimatetile.game.tags.supers;

import coffeecatteam.coffeecatutils.Logger;
import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.tags.TagCompound;
import coffeecatteam.theultimatetile.game.tags.TagIntArray;
import coffeecatteam.theultimatetile.game.tags.TagStringArray;

import java.util.HashMap;
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
        TAG_IDS.put("STRING_ARRAY", 6);
        TAG_IDS.put("INT_ARRAY", 7);
        TAG_IDS.put("FLOAT_ARRAY", 8);
        TAG_IDS.put("DOUBLE_ARRAY", 9);
        TAG_IDS.put("LIST", 10);
        TAG_IDS.put("BOOLEAN", 11);
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

        TagStringArray eatCrops = new TagStringArray(new String[]{"crop_corn", "crop_wheat", "crop_potato", "crop_carrot", "crop_tomato"});
        tags.setTag("eatCrops", eatCrops);

        tags.setBoolean("fleePlayer", true);

        entity.setTag("tags", tags);
        baseCompound.setTag("entity", entity);

        Logger.print(baseCompound.toString());
        GameEngine.getGameEngine().setRunning(false);
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
