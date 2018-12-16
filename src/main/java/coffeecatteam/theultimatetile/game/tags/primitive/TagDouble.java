package coffeecatteam.theultimatetile.game.tags.primitive;

import coffeecatteam.theultimatetile.game.tags.supers.TagBase;
import coffeecatteam.theultimatetile.game.tags.supers.TagPrimitive;
import coffeecatteam.theultimatetile.utils.Utils;

/**
 * @author CoffeeCatRailway
 * Created: 16/12/2018
 */
public class TagDouble extends TagPrimitive {

    /**
     * The double value for the tag.
     */
    private double data;

    public TagDouble(double data) {
        this.data = data;
    }

    /**
     * Gets the type byte for the tag.
     */
    @Override
    public int getId() {
        return TagBase.TAG_IDS.get("DOUBLE");
    }

    @Override
    public String toString() {
        return this.data + "d";
    }

    /**
     * Creates a clone of the tag.
     */
    @Override
    public TagDouble copy() {
        return new TagDouble(this.data);
    }

    @Override
    public boolean equals(Object p_equals_1_) {
        return super.equals(p_equals_1_) && this.data == ((TagDouble) p_equals_1_).data;
    }

    @Override
    public byte getByte() {
        return (byte) (Utils.floor(this.data) & 255);
    }

    @Override
    public int getInt() {
        return Utils.floor(this.data);
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
