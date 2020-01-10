package io.github.vampirestudios.tdg.utils.set;

import io.github.vampirestudios.tdg.utils.set.part.*;
import io.github.vampirestudios.tdg.utils.set.part.num.*;

import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

public final class DataSet extends AbstractDataSet {

    public int getInt(String key) {
        return this.getPartContent(key, PartInt.class, 0);
    }

    public void addInt(String key, int i) {
        this.addPart(key, new PartInt(i));
    }

    public long getLong(String key) {
        return this.getPartContent(key, PartLong.class, 0L);
    }

    public void addLong(String key, long l) {
        this.addPart(key, new PartLong(l));
    }

    public float getFloat(String key) {
        return this.getPartContent(key, PartFloat.class, 0F);
    }

    public void addFloat(String key, float f) {
        this.addPart(key, new PartFloat(f));
    }

    public double getDouble(String key) {
        return this.getPartContent(key, PartDouble.class, 0D);
    }

    public void addDouble(String key, double d) {
        this.addPart(key, new PartDouble(d));
    }

    public DataSet getDataSet(String key) {
        return this.getPartContent(key, PartDataSet.class, new DataSet());
    }

    public void addDataSet(String key, DataSet set) {
        this.addPart(key, new PartDataSet(set));
    }

    public ModBasedDataSet getModBasedDataSet(String key) {
        return this.getPartContent(key, PartModBasedDataSet.class, new ModBasedDataSet());
    }

    public void addModBasedDataSet(String key, ModBasedDataSet set) {
        this.addPart(key, new PartModBasedDataSet(set));
    }

    public UUID getUniqueId(String key) {
        return this.getPartContent(key, PartUniqueId.class, null);
    }

    public void addUniqueId(String key, UUID id) {
        this.addPart(key, new PartUniqueId(id));
    }

    public byte getByte(String key) {
        return this.getPartContent(key, PartByte.class, (byte) 0);
    }

    public void addByte(String key, byte b) {
        this.addPart(key, new PartByte(b));
    }

    public short getShort(String key) {
        return this.getPartContent(key, PartShort.class, (short) 0);
    }

    public void addShort(String key, short s) {
        this.addPart(key, new PartShort(s));
    }

    public boolean getBoolean(String key) {
        return this.getPartContent(key, PartBoolean.class, false);
    }

    public void addBoolean(String key, boolean s) {
        this.addPart(key, new PartBoolean(s));
    }

    public String getString(String key) {
        return this.getPartContent(key, PartString.class, null);
    }

    public void addString(String key, String s) {
        this.addPart(key, new PartString(s));
    }

    public <T extends DataPart> List<T> getList(String key) {
        return (List<T>) this.getPartContent(key, PartList.class, null);
    }

    public void addList(String key, List<? extends DataPart> list) {
        this.addPart(key, new PartList(list));
    }

    public void addEnum(String key, Enum<?> value) {
        this.addInt(key, value.ordinal());
    }

    public <T extends Enum<T>> T getEnum(String key, Class<T> type) {
        if (this.hasKey(key)) {
            int ordinal = this.getInt(key);
            EnumSet<T> set = EnumSet.allOf(type);
            if (set.size() > ordinal) {
                return (T) set.toArray()[ordinal];
            }
        }
        return null;
    }

    public DataSet copy() {
        DataSet set = new DataSet();
        set.data.putAll(this.data);
        return set;
    }
}