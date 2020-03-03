package io.github.vampirestudios.tdg.tags.primitive;

import io.github.vampirestudios.tdg.tags.supers.BaseTag;
import io.github.vampirestudios.tdg.tags.supers.PrimitiveTag;

public class IntTag extends PrimitiveTag {

    /**
     * The integer value for the tag.
     */
    private int data;

    public IntTag(int data) {
        this.data = data;
    }

    @Override
    public int getId() {
        return BaseTag.TAG_IDS.get("INT");
    }

    @Override
    public String toString() {
        return String.valueOf(this.data);
    }

    /**
     * Creates a clone of the tag.
     */
    @Override
    public IntTag copy() {
        return new IntTag(this.data);
    }

    @Override
    public boolean equals(Object p_equals_1_) {
        return super.equals(p_equals_1_) && this.data == ((IntTag) p_equals_1_).data;
    }

    @Override
    public int getInt() {
        return this.data;
    }

    @Override
    public double getDouble() {
        return (double) this.data;
    }

    @Override
    public float getFloat() {
        return (float) this.data;
    }
}
