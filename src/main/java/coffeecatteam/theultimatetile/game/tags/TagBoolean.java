package coffeecatteam.theultimatetile.game.tags;

import coffeecatteam.theultimatetile.game.tags.supers.TagBase;

/**
 * @author CoffeeCatRailway
 * Created: 16/12/2018
 */
public class TagBoolean extends TagBase {
    /**
     * The byte value for the tag.
     */
    private boolean data;

    public TagBoolean(boolean data) {
        this.data = data;
    }

    /**
     * Gets the type byte for the tag.
     */
    @Override
    public int getId() {
        return TagBase.TAG_IDS.get("BOOLEAN");
    }

    @Override
    public String toString() {
        return String.valueOf(this.data);
    }

    /**
     * Creates a clone of the tag.
     */
    @Override
    public TagBoolean copy() {
        return new TagBoolean(this.data);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && this.data == ((TagBoolean) obj).data;
    }

    public boolean isData() {
        return data;
    }
}
