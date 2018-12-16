package coffeecatteam.theultimatetile.game.tags.primitive;

import coffeecatteam.theultimatetile.game.tags.supers.TagBase;
import coffeecatteam.theultimatetile.game.tags.supers.TagPrimitive;
import coffeecatteam.theultimatetile.utils.Utils;

/**
 * @author CoffeeCatRailway
 * Created: 16/12/2018
 */
public class TagFloat extends TagPrimitive {

    /**
     * The float value for the tag.
     */
    private float data;

    public TagFloat(float data) {
        this.data = data;
    }

    /**
     * Gets the type byte for the tag.
     */
    @Override
    public int getId() {
        return TagBase.TAG_IDS.get("FLOAT");
    }

    @Override
    public String toString() {
        return this.data + "f";
    }

    /**
     * Creates a clone of the tag.
     */
    @Override
    public TagFloat copy() {
        return new TagFloat(this.data);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && this.data == ((TagFloat) obj).data;
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
        return (double) this.data;
    }

    @Override
    public float getFloat() {
        return this.data;
    }
}
