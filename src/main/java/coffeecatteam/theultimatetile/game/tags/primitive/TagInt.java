package coffeecatteam.theultimatetile.game.tags.primitive;

import coffeecatteam.theultimatetile.game.tags.supers.TagBase;
import coffeecatteam.theultimatetile.game.tags.supers.TagPrimitive;

/**
 * @author CoffeeCatRailway
 * Created: 15/12/2018
 */
public class TagInt extends TagPrimitive {

    /**
     * The integer value for the tag.
     */
    private int data;

    public TagInt(int data) {
        this.data = data;
    }

    @Override
    public int getId() {
        return TagBase.TAG_IDS.get("INT");
    }

    @Override
    public String toString() {
        return String.valueOf(this.data);
    }

    /**
     * Creates a clone of the tag.
     */
    @Override
    public TagInt copy() {
        return new TagInt(this.data);
    }

    @Override
    public boolean equals(Object p_equals_1_) {
        return super.equals(p_equals_1_) && this.data == ((TagInt) p_equals_1_).data;
    }

    @Override
    public byte getByte() {
        return (byte) (this.data & 255);
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
