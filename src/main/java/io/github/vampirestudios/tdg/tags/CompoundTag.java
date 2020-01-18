package io.github.vampirestudios.tdg.tags;

import io.github.vampirestudios.tdg.start.MaewilLauncher;
import io.github.vampirestudios.tdg.tags.primitive.IntTag;
import io.github.vampirestudios.tdg.tags.primitive.DoubleTag;
import io.github.vampirestudios.tdg.tags.primitive.FloatTag;
import io.github.vampirestudios.tdg.tags.supers.BaseTag;
import io.github.vampirestudios.tdg.tags.supers.PrimitiveTag;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author CoffeeCatRailway
 * Created: 15/12/2018
 */
public class CompoundTag extends BaseTag {

    private static final Pattern SIMPLE_VALUE = Pattern.compile("[A-Za-z0-9._+-]+");
    private final Map<String, BaseTag> tagMap = new HashMap<>();

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
        return BaseTag.TAG_IDS.get("COMPOUND");
    }

    public int getSize() {
        return this.tagMap.size();
    }

    /**
     * Stores the given tag into the map with the given string key. This is mostly used to store tag lists.
     */
    public void setTag(String key, BaseTag value) {
        this.tagMap.put(key, value);
    }

    /**
     * Stores a new IntTag with the given integer value into the map with the given string key.
     */
    public void setInteger(String key, int value) {
        this.tagMap.put(key, new IntTag(value));
    }

    /**
     * Stores a new FloatTag with the given float value into the map with the given string key.
     */
    public void setFloat(String key, float value) {
        this.tagMap.put(key, new FloatTag(value));
    }

    /**
     * Stores a new DoubleTag with the given double value into the map with the given string key.
     */
    public void setDouble(String key, double value) {
        this.tagMap.put(key, new DoubleTag(value));
    }

    /**
     * Stores a new StringTag with the given string value into the map with the given string key.
     */
    public void setString(String key, String value) {
        this.tagMap.put(key, new StringTag(value));
    }

    /**
     * Stores a new IntArrayTag with the given array as data into the map with the given string key.
     */
    public void setIntArray(String key, int[] value) {
        this.tagMap.put(key, new IntArrayTag(value));
    }

    /**
     * Stores a new FloatArrayTag with the given array as data into the map with the given string key.
     */
    public void setFloatArray(String key, float[] value) {
        this.tagMap.put(key, new FloatArrayTag(value));
    }

    /**
     * Stores a new DoubleArrayTag with the given array as data into the map with the given string key.
     */
    public void setDoubleArray(String key, double[] value) {
        this.tagMap.put(key, new DoubleArrayTag(value));
    }

    /**
     * Stores the given boolean value as a TagByte, storing 1 for true and 0 for false, using the given string key.
     */
    public void setBoolean(String key, boolean value) {
        this.tagMap.put(key, new BooleanTag(value));
    }

    /**
     * gets a generic tag with the specified name
     */
    public BaseTag getTag(String key) {
        return this.tagMap.get(key);
    }

    /**
     * Gets the ID byte for the given tag key
     */
    public int getTagId(String key) {
        BaseTag nbtbase = this.tagMap.get(key);
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
            if (this.hasKey(key, BaseTag.TAG_IDS.get("INT"))) {
                return ((PrimitiveTag) this.tagMap.get(key)).getInt();
            }
        } catch (ClassCastException classcastexception) {
            MaewilLauncher.LOGGER.error(classcastexception);
            throw classcastexception;
        }

        return 0;
    }

    /**
     * Retrieves a float value using the specified key, or 0 if no such key was stored.
     */
    public float getFloat(String key) {
        try {
            if (this.hasKey(key, BaseTag.TAG_IDS.get("FLOAT"))) {
                return ((PrimitiveTag) this.tagMap.get(key)).getFloat();
            }
        } catch (ClassCastException classcastexception) {
            MaewilLauncher.LOGGER.error(classcastexception);
            throw classcastexception;
        }

        return 0.0F;
    }

    /**
     * Retrieves a double value using the specified key, or 0 if no such key was stored.
     */
    public double getDouble(String key) {
        try {
            if (this.hasKey(key, BaseTag.TAG_IDS.get("DOUBLE"))) {
                return ((PrimitiveTag) this.tagMap.get(key)).getDouble();
            }
        } catch (ClassCastException classcastexception) {
            MaewilLauncher.LOGGER.error(classcastexception);
            throw classcastexception;
        }

        return 0.0D;
    }

    /**
     * Retrieves a string value using the specified key, or an empty string if no such key was stored.
     */
    public String getString(String key) {
        try {
            if (this.hasKey(key, BaseTag.TAG_IDS.get("STRING"))) {
                return this.tagMap.get(key).getString();
            }
        } catch (ClassCastException classcastexception) {
            MaewilLauncher.LOGGER.error(classcastexception);
            throw classcastexception;
        }

        return "";
    }

    /**
     * Retrieves an int array using the specified key, or a zero-length array if no such key was stored.
     */
    public int[] getIntArray(String key) {
        try {
            if (this.hasKey(key, BaseTag.TAG_IDS.get("INT_ARRAY"))) {
                return ((IntArrayTag) this.tagMap.get(key)).getIntArray();
            }
        } catch (ClassCastException classcastexception) {
            MaewilLauncher.LOGGER.error(classcastexception);
            throw classcastexception;
        }

        return new int[0];
    }

    /**
     * Retrieves an float array using the specified key, or a zero-length array if no such key was stored.
     */
    public float[] getFloatArray(String key) {
        try {
            if (this.hasKey(key, BaseTag.TAG_IDS.get("FLOAT_ARRAY"))) {
                return ((FloatArrayTag) this.tagMap.get(key)).getFloatArray();
            }
        } catch (ClassCastException classcastexception) {
            MaewilLauncher.LOGGER.error(classcastexception);
            throw classcastexception;
        }

        return new float[0];
    }

    /**
     * Retrieves an double array using the specified key, or a zero-length array if no such key was stored.
     */
    public double[] getDoubleArray(String key) {
        try {
            if (this.hasKey(key, BaseTag.TAG_IDS.get("DOUBLE_ARRAY"))) {
                return ((DoubleArrayTag) this.tagMap.get(key)).getDoubleArray();
            }
        } catch (ClassCastException classcastexception) {
            MaewilLauncher.LOGGER.error(classcastexception);
            throw classcastexception;
        }

        return new double[0];
    }

    /**
     * Retrieves a CompoundTag subtag matching the specified key, or a new empty CompoundTag if no such key was
     * stored.
     */
    public CompoundTag getCompoundTag(String key) {
        try {
            if (this.hasKey(key, BaseTag.TAG_IDS.get("COMPOUND"))) {
                return (CompoundTag) this.tagMap.get(key);
            }
        } catch (ClassCastException classcastexception) {
            MaewilLauncher.LOGGER.error(classcastexception);
            throw classcastexception;
        }

        return new CompoundTag();
    }

    /**
     * Gets the ListTag object with the given name.
     */
    public ListTag getTagList(String key) {
        try {
            if (this.getTagId(key) == BaseTag.TAG_IDS.get("LIST")) {
                ListTag tagList = (ListTag) this.tagMap.get(key);

                if (tagList.hasNoTags()) {
                    return new ListTag();
                }

                return tagList;
            }
        } catch (ClassCastException classcastexception) {
            MaewilLauncher.LOGGER.error(classcastexception);
            throw classcastexception;
        }

        return new ListTag();
    }

    /**
     * Retrieves a boolean value using the specified key, or false if no such key was stored. This uses the getByte
     * method.
     */
    public boolean getBoolean(String key) {
        try {
            if (this.hasKey(key, BaseTag.TAG_IDS.get("BOOLEAN"))) {
                return ((BooleanTag) this.tagMap.get(key)).isData();
            }
        } catch (ClassCastException classcastexception) {
            MaewilLauncher.LOGGER.error(classcastexception);
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

            stringbuilder.append(StringTag.quoteAndEscape(handleEscape(s))).append(':').append(this.tagMap.get(s));
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
    public CompoundTag copy() {
        CompoundTag compoundTag = new CompoundTag();

        for (String s : this.tagMap.keySet()) {
            compoundTag.setTag(s, ((BaseTag) this.tagMap.get(s)).copy());
        }

        return compoundTag;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && Objects.equals(this.tagMap.entrySet(), ((CompoundTag) obj).tagMap.entrySet());
    }

    /**
     * Merges copies of data contained in {@code other} into this compound tag.
     */
    public void merge(CompoundTag other) {
        for (String s : other.tagMap.keySet()) {
            BaseTag nbtbase = other.tagMap.get(s);

            if (nbtbase.getId() == 10) {
                if (this.hasKey(s, 10)) {
                    CompoundTag compoundTag = this.getCompoundTag(s);
                    compoundTag.merge((CompoundTag) nbtbase);
                } else {
                    this.setTag(s, nbtbase.copy());
                }
            } else {
                this.setTag(s, nbtbase.copy());
            }
        }
    }

    protected static String handleEscape(String p_193582_0_) {
        return SIMPLE_VALUE.matcher(p_193582_0_).matches() ? p_193582_0_ : StringTag.quoteAndEscape(p_193582_0_);
    }
}
