package io.github.vampirestudios.tdg.tags;

import io.github.vampirestudios.tdg.tags.supers.BaseTag;

import java.util.Objects;

public class StringTag extends BaseTag {

    /**
     * The string value for the tag (cannot be empty).
     */
    private String data;

    public StringTag() {
        this("");
    }

    public StringTag(String data) {
        Objects.requireNonNull(data, "Null string not allowed");
        this.data = data;
    }

    @Override
    public int getId() {
        return BaseTag.TAG_IDS.get("STRING");
    }

    public String toString() {
        return quoteAndEscape(this.data);
    }

    /**
     * Creates a clone of the tag.
     */
    @Override
    public StringTag copy() {
        return new StringTag(this.data);
    }

    /**
     * Return whether this compound has no tags.
     */
    @Override
    public boolean hasNoTags() {
        return this.data.isEmpty();
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        } else {
            StringTag nbttagstring = (StringTag) obj;
            return this.data == null && nbttagstring.data == null || Objects.equals(this.data, nbttagstring.data);
        }
    }

    @Override
    public String getString() {
        return this.data;
    }

    public static String quoteAndEscape(String string) {
        StringBuilder stringbuilder = new StringBuilder("\"");

        for (int i = 0; i < string.length(); ++i) {
            char c0 = string.charAt(i);

            if (c0 == '\\' || c0 == '"') {
                stringbuilder.append('\\');
            }

            stringbuilder.append(c0);
        }

        return stringbuilder.append('"').toString();
    }
}
