package coffeecatteam.theultimatetile.game.tags;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.tags.primitive.TagDouble;
import coffeecatteam.theultimatetile.game.tags.primitive.TagFloat;
import coffeecatteam.theultimatetile.game.tags.primitive.TagInt;
import coffeecatteam.theultimatetile.game.tags.supers.TagBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author CoffeeCatRailway
 * Created: 16/12/2018
 */
public class TagList extends TagBase implements java.lang.Iterable<TagBase> {

    /**
     * The array list containing the tags encapsulated in this list.
     */
    private List<TagBase> tagList = new ArrayList<>();
    /**
     * The type byte for the tags in the list - they must all be of the same type.
     */
    private int tagType = 0;

    public TagList() {
    }

    public TagList(List<TagBase> tagList) {
        for (int i = 0; i < tagList.size(); i++)
            this.tagList.add(tagList.get(i).copy());
    }

    @Override
    public int getId() {
        return TagBase.TAG_IDS.get("LIST");
    }

    @Override
    public String toString() {
        StringBuilder stringbuilder = new StringBuilder("[");

        for (int i = 0; i < this.tagList.size(); ++i) {
            if (i != 0) {
                stringbuilder.append(',');
            }

            stringbuilder.append(this.tagList.get(i));
        }

        return stringbuilder.append(']').toString();
    }

    /**
     * Adds the provided tag to the end of the list. There is no check to verify this tag is of the same type as any
     * previous tag.
     */
    public void appendTag(TagBase tag) {
        if (tag.getId() == 0) {
            Engine.getEngine().getLogger().print("Invalid TagEnd added to ListTag");
        } else {
            if (this.tagType == 0) {
                this.tagType = tag.getId();
            } else if (this.tagType != tag.getId()) {
                Engine.getEngine().getLogger().print("Adding mismatching tag types to tag list");
                return;
            }

            this.tagList.add(tag);
        }
    }

    /**
     * Set the given index to the given tag
     */
    public void set(int idx, TagBase tag) {
        if (tag.getId() == 0) {
            Engine.getEngine().getLogger().print("Invalid TagEnd added to ListTag");
        } else if (idx >= 0 && idx < this.tagList.size()) {
            if (this.tagType == 0) {
                this.tagType = tag.getId();
            } else if (this.tagType != tag.getId()) {
                Engine.getEngine().getLogger().print("Adding mismatching tag types to tag list");
                return;
            }

            this.tagList.set(idx, tag);
        } else {
            Engine.getEngine().getLogger().print("index out of bounds to set tag in tag list");
        }
    }

    /**
     * Removes a tag at the given index.
     */
    public TagBase removeTag(int i) {
        return this.tagList.remove(i);
    }

    /**
     * Return whether this compound has no tags.
     */
    public boolean hasNoTags() {
        return this.tagList.isEmpty();
    }

    /**
     * Retrieves the TagCompound at the specified index in the list
     */
    public TagCompound getCompoundTagAt(int i) {
        if (i >= 0 && i < this.tagList.size()) {
            TagBase base = this.tagList.get(i);

            if (base.getId() == 10) {
                return (TagCompound) base;
            }
        }

        return new TagCompound();
    }

    public int getIntAt(int p_186858_1_) {
        if (p_186858_1_ >= 0 && p_186858_1_ < this.tagList.size()) {
            TagBase base = this.tagList.get(p_186858_1_);

            if (base.getId() == 3) {
                return ((TagInt) base).getInt();
            }
        }

        return 0;
    }

    public int[] getIntArrayAt(int i) {
        if (i >= 0 && i < this.tagList.size()) {
            TagBase base = this.tagList.get(i);

            if (base.getId() == 11) {
                return ((TagIntArray) base).getIntArray();
            }
        }

        return new int[0];
    }

    public double getDoubleAt(int i) {
        if (i >= 0 && i < this.tagList.size()) {
            TagBase base = this.tagList.get(i);

            if (base.getId() == 6) {
                return ((TagDouble) base).getDouble();
            }
        }

        return 0.0D;
    }

    public float getFloatAt(int i) {
        if (i >= 0 && i < this.tagList.size()) {
            TagBase base = this.tagList.get(i);

            if (base.getId() == 5) {
                return ((TagFloat) base).getFloat();
            }
        }

        return 0.0F;
    }

    /**
     * Retrieves the tag String value at the specified index in the list
     */
    public String getStringTagAt(int i) {
        if (i >= 0 && i < this.tagList.size()) {
            TagBase base = this.tagList.get(i);
            return base.getId() == 8 ? base.getString() : base.toString();
        } else {
            return "";
        }
    }

    /**
     * Get the tag at the given position
     */
    public TagBase get(int idx) {
        return (idx >= 0 && idx < this.tagList.size() ? this.tagList.get(idx) : new TagEnd());
    }

    /**
     * Returns the number of tags in the list.
     */
    public int size() {
        return this.tagList.size();
    }

    /**
     * Creates a clone of the tag.
     */
    @Override
    public TagList copy() {
        TagList tagList = new TagList();
        tagList.tagType = this.tagType;

        for (TagBase base : this.tagList) {
            TagBase base1 = base.copy();
            tagList.tagList.add(base1);
        }

        return tagList;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        } else {
            TagList taglist = (TagList) obj;
            return this.tagType == taglist.tagType && Objects.equals(this.tagList, taglist.tagList);
        }
    }

    public int getTagType() {
        return this.tagType;
    }

    @Override
    public java.util.Iterator<TagBase> iterator() {
        return tagList.iterator();
    }
}
