package io.github.vampirestudios.tdg.utils.set;

import io.github.vampirestudios.tdg.utils.Identifier;
import io.github.vampirestudios.tdg.utils.set.part.*;
import io.github.vampirestudios.tdg.utils.set.part.num.*;

import java.util.List;
import java.util.UUID;

public final class ModBasedDataSet extends AbstractDataSet {

    @Override
    public void addPart(String name, DataPart part) {
//        Preconditions.checkArgument(Util.isResourceName(name), "The name " + name + " of data part " + part + " which is being added to mod based data set " + this + " is not a valid resource name!");
        super.addPart(name, part);
    }

    public int getInt(Identifier key) {
        return this.getPartContent(key.toString(), PartInt.class, 0);
    }

    public void addInt(Identifier key, int i) {
        this.addPart(key.toString(), new PartInt(i));
    }

    public long getLong(Identifier key) {
        return this.getPartContent(key.toString(), PartLong.class, 0L);
    }

    public void addLong(Identifier key, long l) {
        this.addPart(key.toString(), new PartLong(l));
    }

    public float getFloat(Identifier key) {
        return this.getPartContent(key.toString(), PartFloat.class, 0F);
    }

    public void addFloat(Identifier key, float f) {
        this.addPart(key.toString(), new PartFloat(f));
    }

    public double getDouble(Identifier key) {
        return this.getPartContent(key.toString(), PartDouble.class, 0D);
    }

    public void addDouble(Identifier key, double d) {
        this.addPart(key.toString(), new PartDouble(d));
    }

    public DataSet getDataSet(Identifier key) {
        return this.getPartContent(key.toString(), PartDataSet.class, new DataSet());
    }

    public void addDataSet(Identifier key, DataSet set) {
        this.addPart(key.toString(), new PartDataSet(set));
    }

    public ModBasedDataSet getModBasedDataSet(Identifier key) {
        return this.getPartContent(key.toString(), PartModBasedDataSet.class, new ModBasedDataSet());
    }

    public void addModBasedDataSet(Identifier key, ModBasedDataSet set) {
        this.addPart(key.toString(), new PartModBasedDataSet(set));
    }

    public UUID getUniqueId(Identifier key) {
        return this.getPartContent(key.toString(), PartUniqueId.class, null);
    }

    public void addUniqueId(Identifier key, UUID id) {
        this.addPart(key.toString(), new PartUniqueId(id));
    }

    public byte getByte(Identifier key) {
        return this.getPartContent(key.toString(), PartByte.class, (byte) 0);
    }

    public void addByte(Identifier key, byte b) {
        this.addPart(key.toString(), new PartByte(b));
    }

    public short getShort(Identifier key) {
        return this.getPartContent(key.toString(), PartShort.class, (short) 0);
    }

    public void addShort(Identifier key, short s) {
        this.addPart(key.toString(), new PartShort(s));
    }

    public boolean getBoolean(Identifier key) {
        return this.getPartContent(key.toString(), PartBoolean.class, false);
    }

    public void addBoolean(Identifier key, boolean s) {
        this.addPart(key.toString(), new PartBoolean(s));
    }

    public String getString(Identifier key) {
        return this.getPartContent(key.toString(), PartString.class, null);
    }

    public void addString(Identifier key, String s) {
        this.addPart(key.toString(), new PartString(s));
    }

    public <T extends DataPart> List<T> getList(Identifier key) {
        return (List<T>) this.getPartContent(key.toString(), PartList.class, null);
    }

    public void addList(Identifier key, List<DataPart> list) {
        this.addPart(key.toString(), new PartList(list));
    }

    public ModBasedDataSet copy() {
        ModBasedDataSet set = new ModBasedDataSet();
        set.data.putAll(this.data);
        return set;
    }
}