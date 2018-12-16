package coffeecatteam.theultimatetile.game.tags;

import coffeecatteam.coffeecatutils.Logger;
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
    public void appendTag(TagBase nbt) {
        if (nbt.getId() == 0) {
            Logger.print("Invalid TagEnd added to ListTag");
        } else {
            if (this.tagType == 0) {
                this.tagType = nbt.getId();
            } else if (this.tagType != nbt.getId()) {
                Logger.print("Adding mismatching tag types to tag list");
                return;
            }

            this.tagList.add(nbt);
        }
    }

    /**
     * Set the given index to the given tag
     */
    public void set(int idx, TagBase nbt) {
        if (nbt.getId() == 0) {
            Logger.print("Invalid TagEnd added to ListTag");
        } else if (idx >= 0 && idx < this.tagList.size()) {
            if (this.tagType == 0) {
                this.tagType = nbt.getId();
            } else if (this.tagType != nbt.getId()) {
                Logger.print("Adding mismatching tag types to tag list");
                return;
            }

            this.tagList.set(idx, nbt);
        } else {
            Logger.print("index out of bounds to set tag in tag list");
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
            TagBase nbtbase = this.tagList.get(i);

            if (nbtbase.getId() == 10) {
                return (TagCompound) nbtbase;
            }
        }

        return new TagCompound();
    }

    public int getIntAt(int p_186858_1_) {
        if (p_186858_1_ >= 0 && p_186858_1_ < this.tagList.size()) {
            TagBase nbtbase = this.tagList.get(p_186858_1_);

            if (nbtbase.getId() == 3) {
                return ((TagInt) nbtbase).getInt();
            }
        }

        return 0;
    }

    public int[] getIntArrayAt(int i) {
        if (i >= 0 && i < this.tagList.size()) {
            TagBase nbtbase = this.tagList.get(i);

            if (nbtbase.getId() == 11) {
                return ((TagIntArray) nbtbase).getIntArray();
            }
        }

        return new int[0];
    }

    public double getDoubleAt(int i) {
        if (i >= 0 && i < this.tagList.size()) {
            TagBase nbtbase = this.tagList.get(i);

            if (nbtbase.getId() == 6) {
                return ((TagDouble) nbtbase).getDouble();
            }
        }

        return 0.0D;
    }

    public float getFloatAt(int i) {
        if (i >= 0 && i < this.tagList.size()) {
            TagBase nbtbase = this.tagList.get(i);

            if (nbtbase.getId() == 5) {
                return ((TagFloat) nbtbase).getFloat();
            }
        }

        return 0.0F;
    }

    /**
     * Retrieves the tag String value at the specified index in the list
     */
    public String getStringTagAt(int i) {
        if (i >= 0 && i < this.tagList.size()) {
            TagBase nbtbase = this.tagList.get(i);
            return nbtbase.getId() == 8 ? nbtbase.getString() : nbtbase.toString();
        } else {
            return "";
        }
    }

    /**
     * Get the tag at the given position
     */
    public TagBase get(int idx) {
        return (TagBase) (idx >= 0 && idx < this.tagList.size() ? (TagBase) this.tagList.get(idx) : new TagEnd());
    }

    /**
     * Returns the number of tags in the list.
     */
    public int tagCount() {
        return this.tagList.size();
    }

    /**
     * Creates a clone of the tag.
     */
    @Override
    public TagList copy() {
        TagList tagList = new TagList();
        tagList.tagType = this.tagType;

        for (TagBase nbtbase : this.tagList) {
            TagBase nbtbase1 = nbtbase.copy();
            tagList.tagList.add(nbtbase1);
        }

        return tagList;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        } else {
            TagList nbttaglist = (TagList) obj;
            return this.tagType == nbttaglist.tagType && Objects.equals(this.tagList, nbttaglist.tagList);
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
