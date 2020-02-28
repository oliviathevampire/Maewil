package io.github.vampirestudios.tdg.tags.primitive;

import io.github.vampirestudios.tdg.tags.supers.BaseTag;
import io.github.vampirestudios.tdg.tags.supers.PrimitiveTag;
import io.github.vampirestudios.tdg.utils.UtilsIdk;

public class FloatTag extends PrimitiveTag {

    /**
     * The float value for the tag.
     */
    private float data;

    public FloatTag(float data) {
        this.data = data;
    }

    /**
     * Gets the type byte for the tag.
     */
    @Override
    public int getId() {
        return BaseTag.TAG_IDS.get("FLOAT");
    }

    @Override
    public String toString() {
        return String.valueOf(this.data);
    }

    /**
     * Creates a clone of the tag.
     */
    @Override
    public FloatTag copy() {
        return new FloatTag(this.data);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && this.data == ((FloatTag) obj).data;
    }

    @Override
    public int getInt() {
        return UtilsIdk.floorF(this.data);
    }

    @Override
    public double getDouble() {
        return (double) this.data;
    }

    @Override
    public float getFloat() {
        return this.data;
    }
}
