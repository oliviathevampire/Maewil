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
        return String.valueOf(this.data);
    }

    /**
     * Creates a clone of the tag.
     */
    @Override
    public TagDouble copy() {
        return new TagDouble(this.data);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && this.data == ((TagDouble) obj).data;
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