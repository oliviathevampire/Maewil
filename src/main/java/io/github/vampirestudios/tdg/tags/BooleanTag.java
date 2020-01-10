package io.github.vampirestudios.tdg.tags;

import io.github.vampirestudios.tdg.tags.supers.BaseTag;

/**
 * @author CoffeeCatRailway
 * Created: 16/12/2018
 */
public class BooleanTag extends BaseTag {
    /**
     * The byte value for the tag.
     */
    private boolean data;

    public BooleanTag(boolean data) {
        this.data = data;
    }

    /**
     * Gets the type byte for the tag.
     */
    @Override
    public int getId() {
        return BaseTag.TAG_IDS.get("BOOLEAN");
    }

    @Override
    public String toString() {
        return String.valueOf(this.data);
    }

    /**
     * Creates a clone of the tag.
     */
    @Override
    public BooleanTag copy() {
        return new BooleanTag(this.data);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && this.data == ((BooleanTag) obj).data;
    }

    public boolean isData() {
        return data;
    }
}
