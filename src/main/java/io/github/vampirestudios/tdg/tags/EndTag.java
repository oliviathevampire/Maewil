package io.github.vampirestudios.tdg.tags;

import io.github.vampirestudios.tdg.tags.supers.BaseTag;

/**
 * @author CoffeeCatRailway
 * Created: 16/12/2018
 */
public class EndTag extends BaseTag {

    /**
     * Gets the type byte for the tag.
     */
    public int getId() {
        return BaseTag.TAG_IDS.get("END");
    }

    public String toString() {
        return "END";
    }

    /**
     * Creates a clone of the tag.
     */
    public EndTag copy() {
        return new EndTag();
    }
}
