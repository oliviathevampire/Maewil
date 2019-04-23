package coffeecatteam.theultimatetile.tags;

import coffeecatteam.theultimatetile.start.TutLauncher;
import coffeecatteam.theultimatetile.tags.primitive.TagDouble;
import coffeecatteam.theultimatetile.tags.primitive.TagFloat;
import coffeecatteam.theultimatetile.tags.primitive.TagInt;
import coffeecatteam.theultimatetile.tags.supers.TagBase;
import coffeecatteam.theultimatetile.tags.supers.TagPrimitive;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author CoffeeCatRailway
 * Created: 15/12/2018
 */
public class TagCompound extends TagBase {

    private static final Pattern SIMPLE_VALUE = Pattern.compile("[A-Za-z0-9._+-]+");
    private final Map<String, TagBase> tagMap = new HashMap<>();

    /**
     * Gets a set with the names of the keys in the tag compound.
     */
    public Set<String> getKeySet() {
        return this.tagMap.keySet();
    }

    /**
     * Gets the type byte for the tag.
     */
    @Override
    public int getId() {
        return TagBase.TAG_IDS.get("COMPOUND");
    }

    public int getSize() {
        return this.tagMap.size();
    }

    /**
     * Stores the given tag into the map with the given string key. This is mostly used to store tag lists.
     */
    public void setTag(String key, TagBase value) {
        this.tagMap.put(key, value);
    }

    /**
     * Stores a new TagInt with the given integer value into the map with the given string key.
     */
    public void setInteger(String key, int value) {
        this.tagMap.put(key, new TagInt(value));
    }

    /**
     * Stores a new TagFloat with the given float value into the map with the given string key.
     */
    public void setFloat(String key, float value) {
        this.tagMap.put(key, new TagFloat(value));
    }

    /**
     * Stores a new TagDouble with the given double value into the map with the given string key.
     */
    public void setDouble(String key, double value) {
        this.tagMap.put(key, new TagDouble(value));
    }

    /**
     * Stores a new TagString with the given string value into the map with the given string key.
     */
    public void setString(String key, String value) {
        this.tagMap.put(key, new TagString(value));
    }

    /**
     * Stores a new TagIntArray with the given array as data into the map with the given string key.
     */
    public void setIntArray(String key, int[] value) {
        this.tagMap.put(key, new TagIntArray(value));
    }

    /**
     * Stores a new TagFloatArray with the given array as data into the map with the given string key.
     */
    public void setFloatArray(String key, float[] value) {
        this.tagMap.put(key, new TagFloatArray(value));
    }

    /**
     * Stores a new TagDoubleArray with the given array as data into the map with the given string key.
     */
    public void setDoubleArray(String key, double[] value) {
        this.tagMap.put(key, new TagDoubleArray(value));
    }

    /**
     * Stores the given boolean value as a TagByte, storing 1 for true and 0 for false, using the given string key.
     */
    public void setBoolean(String key, boolean value) {
        this.tagMap.put(key, new TagBoolean(value));
    }

    /**
     * gets a generic tag with the specified name
     */
    public TagBase getTag(String key) {
        return this.tagMap.get(key);
    }

    /**
     * Gets the ID byte for the given tag key
     */
    public int getTagId(String key) {
        TagBase nbtbase = this.tagMap.get(key);
        return nbtbase == null ? 0 : nbtbase.getId();
    }

    /**
     * Returns whether the given string has been previously stored as a key in the map.
     */
    public boolean hasKey(String key) {
        return this.tagMap.containsKey(key);
    }

    /**
     * Returns whether the given string has been previously stored as a key in this tag compound as a particular type,
     * denoted by a parameter in the form of an ordinal. If the provided ordinal is 99, this method will match tag types
     * representing numbers.
     */
    public boolean hasKey(String key, int type) {
        int i = this.getTagId(key);

        if (i == type) {
            return true;
        } else if (type != 99) {
            return false;
        } else {
            return i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 6;
        }
    }

    /**
     * Retrieves an integer value using the specified key, or 0 if no such key was stored.
     */
    public int getInteger(String key) {
        try {
            if (this.hasKey(key, TagBase.TAG_IDS.get("INT"))) {
                return ((TagPrimitive) this.tagMap.get(key)).getInt();
            }
        } catch (ClassCastException classcastexception) {
            TutLauncher.LOGGER.error(classcastexception);
            throw classcastexception;
        }

        return 0;
    }

    /**
     * Retrieves a float value using the specified key, or 0 if no such key was stored.
     */
    public float getFloat(String key) {
        try {
            if (this.hasKey(key, TagBase.TAG_IDS.get("FLOAT"))) {
                return ((TagPrimitive) this.tagMap.get(key)).getFloat();
            }
        } catch (ClassCastException classcastexception) {
            TutLauncher.LOGGER.error(classcastexception);
            throw classcastexception;
        }

        return 0.0F;
    }

    /**
     * Retrieves a double value using the specified key, or 0 if no such key was stored.
     */
    public double getDouble(String key) {
        try {
            if (this.hasKey(key, TagBase.TAG_IDS.get("DOUBLE"))) {
                return ((TagPrimitive) this.tagMap.get(key)).getDouble();
            }
        } catch (ClassCastException classcastexception) {
            TutLauncher.LOGGER.error(classcastexception);
            throw classcastexception;
        }

        return 0.0D;
    }

    /**
     * Retrieves a string value using the specified key, or an empty string if no such key was stored.
     */
    public String getString(String key) {
        try {
            if (this.hasKey(key, TagBase.TAG_IDS.get("STRING"))) {
                return this.tagMap.get(key).getString();
            }
        } catch (ClassCastException classcastexception) {
            TutLauncher.LOGGER.error(classcastexception);
            throw classcastexception;
        }

        return "";
    }

    /**
     * Retrieves an int array using the specified key, or a zero-length array if no such key was stored.
     */
    public int[] getIntArray(String key) {
        try {
            if (this.hasKey(key, TagBase.TAG_IDS.get("INT_ARRAY"))) {
                return ((TagIntArray) this.tagMap.get(key)).getIntArray();
            }
        } catch (ClassCastException classcastexception) {
            TutLauncher.LOGGER.error(classcastexception);
            throw classcastexception;
        }

        return new int[0];
    }

    /**
     * Retrieves an float array using the specified key, or a zero-length array if no such key was stored.
     */
    public float[] getFloatArray(String key) {
        try {
            if (this.hasKey(key, TagBase.TAG_IDS.get("FLOAT_ARRAY"))) {
                return ((TagFloatArray) this.tagMap.get(key)).getFloatArray();
            }
        } catch (ClassCastException classcastexception) {
            TutLauncher.LOGGER.error(classcastexception);
            throw classcastexception;
        }

        return new float[0];
    }

    /**
     * Retrieves an double array using the specified key, or a zero-length array if no such key was stored.
     */
    public double[] getDoubleArray(String key) {
        try {
            if (this.hasKey(key, TagBase.TAG_IDS.get("DOUBLE_ARRAY"))) {
                return ((TagDoubleArray) this.tagMap.get(key)).getDoubleArray();
            }
        } catch (ClassCastException classcastexception) {
            TutLauncher.LOGGER.error(classcastexception);
            throw classcastexception;
        }

        return new double[0];
    }

    /**
     * Retrieves a TagCompound subtag matching the specified key, or a new empty TagCompound if no such key was
     * stored.
     */
    public TagCompound getCompoundTag(String key) {
        try {
            if (this.hasKey(key, TagBase.TAG_IDS.get("COMPOUND"))) {
                return (TagCompound) this.tagMap.get(key);
            }
        } catch (ClassCastException classcastexception) {
            TutLauncher.LOGGER.error(classcastexception);
            throw classcastexception;
        }

        return new TagCompound();
    }

    /**
     * Gets the TagList object with the given name.
     */
    public TagList getTagList(String key) {
        try {
            if (this.getTagId(key) == TagBase.TAG_IDS.get("LIST")) {
                TagList tagList = (TagList) this.tagMap.get(key);

                if (tagList.hasNoTags()) {
                    return new TagList();
                }

                return tagList;
            }
        } catch (ClassCastException classcastexception) {
            TutLauncher.LOGGER.error(classcastexception);
            throw classcastexception;
        }

        return new TagList();
    }

    /**
     * Retrieves a boolean value using the specified key, or false if no such key was stored. This uses the getByte
     * method.
     */
    public boolean getBoolean(String key) {
        try {
            if (this.hasKey(key, TagBase.TAG_IDS.get("BOOLEAN"))) {
                return ((TagBoolean) this.tagMap.get(key)).isData();
            }
        } catch (ClassCastException classcastexception) {
            TutLauncher.LOGGER.error(classcastexception);
            throw classcastexception;
        }

        return false;
    }

    /**
     * Remove the specified tag.
     */
    public void removeTag(String key) {
        this.tagMap.remove(key);
    }

    @Override
    public String toString() {
        StringBuilder stringbuilder = new StringBuilder("{");
        Collection<String> collection = this.tagMap.keySet();

        for (String s : collection) {
            if (stringbuilder.length() != 1) {
                stringbuilder.append(',');
            }

            stringbuilder.append(TagString.quoteAndEscape(handleEscape(s))).append(':').append(this.tagMap.get(s));
        }

        return stringbuilder.append('}').toString();
    }

    /**
     * Return whether this compound has no tags.
     */
    @Override
    public boolean hasNoTags() {
        return this.tagMap.isEmpty();
    }

    /**
     * Creates a clone of the tag.
     */
    @Override
    public TagCompound copy() {
        TagCompound tagCompound = new TagCompound();

        for (String s : this.tagMap.keySet()) {
            tagCompound.setTag(s, ((TagBase) this.tagMap.get(s)).copy());
        }

        return tagCompound;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && Objects.equals(this.tagMap.entrySet(), ((TagCompound) obj).tagMap.entrySet());
    }

    /**
     * Merges copies of data contained in {@code other} into this compound tag.
     */
    public void merge(TagCompound other) {
        for (String s : other.tagMap.keySet()) {
            TagBase nbtbase = other.tagMap.get(s);

            if (nbtbase.getId() == 10) {
                if (this.hasKey(s, 10)) {
                    TagCompound tagCompound = this.getCompoundTag(s);
                    tagCompound.merge((TagCompound) nbtbase);
                } else {
                    this.setTag(s, nbtbase.copy());
                }
            } else {
                this.setTag(s, nbtbase.copy());
            }
        }
    }

    protected static String handleEscape(String p_193582_0_) {
        return SIMPLE_VALUE.matcher(p_193582_0_).matches() ? p_193582_0_ : TagString.quoteAndEscape(p_193582_0_);
    }
}
