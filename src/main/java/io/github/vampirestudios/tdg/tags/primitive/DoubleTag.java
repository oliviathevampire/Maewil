package io.github.vampirestudios.tdg.tags.primitive;

import io.github.vampirestudios.tdg.tags.supers.BaseTag;
import io.github.vampirestudios.tdg.tags.supers.PrimitiveTag;
import io.github.vampirestudios.tdg.utils.UtilsIdk;

public class DoubleTag extends PrimitiveTag {

    /**
     * The double value for the tag.
     */
    private double data;

    public DoubleTag(double data) {
        this.data = data;
    }

    /**
     * Gets the type byte for the tag.
     */
    @Override
    public int getId() {
        return BaseTag.TAG_IDS.get("DOUBLE");
    }

    @Override
    public String toString() {
        return String.valueOf(this.data);
    }

    /**
     * Creates a clone of the tag.
     */
    @Override
    public DoubleTag copy() {
        return new DoubleTag(this.data);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && this.data == ((DoubleTag) obj).data;
    }

    @Override
    public int getInt() {
        return UtilsIdk.floorD(this.data);
    }

    @Override
    public double getDouble() {
        return this.data;
    }

    @Override
    public float getFloat() {
        return (float) this.data;
    }
}
