package coffeecatteam.theultimatetile.tags;

import coffeecatteam.theultimatetile.tags.supers.TagBase;

/**
 * @author CoffeeCatRailway
 * Created: 16/12/2018
 */
public class TagEnd extends TagBase {

    /**
     * Gets the type byte for the tag.
     */
    public int getId() {
        return TagBase.TAG_IDS.get("END");
    }

    public String toString() {
        return "END";
    }

    /**
     * Creates a clone of the tag.
     */
    public TagEnd copy() {
        return new TagEnd();
    }
}
