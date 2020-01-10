package io.github.vampirestudios.tdg.tags;

import io.github.vampirestudios.tdg.start.TutLauncher;
import io.github.vampirestudios.tdg.tags.primitive.DoubleTag;
import io.github.vampirestudios.tdg.tags.primitive.FloatTag;
import io.github.vampirestudios.tdg.tags.primitive.IntTag;
import io.github.vampirestudios.tdg.tags.supers.BaseTag;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author CoffeeCatRailway
 * Created: 16/12/2018
 */
public class ListTag extends BaseTag implements java.lang.Iterable<BaseTag> {

    /**
     * The array list containing the tags encapsulated in this list.
     */
    private List<BaseTag> tagList = new ArrayList<>();
    /**
     * The type byte for the tags in the list - they must all be of the same type.
     */
    private int tagType = 0;

    public ListTag() {
    }

    public ListTag(List<BaseTag> tagList) {
        for (int i = 0; i < tagList.size(); i++)
            this.tagList.add(tagList.get(i).copy());
    }

    @Override
    public int getId() {
        return BaseTag.TAG_IDS.get("LIST");
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
    public void appendTag(BaseTag tag) {
        if (tag.getId() == 0) {
            TutLauncher.LOGGER.warn("Invalid EndTag added to ListTag");
        } else {
            if (this.tagType == 0) {
                this.tagType = tag.getId();
            } else if (this.tagType != tag.getId()) {
                TutLauncher.LOGGER.warn("Adding mismatching tag types to tag list");
                return;
            }

            this.tagList.add(tag);
        }
    }

    /**
     * Set the given index to the given tag
     */
    public void set(int idx, BaseTag tag) {
        if (tag.getId() == 0) {
            TutLauncher.LOGGER.warn("Invalid EndTag added to ListTag");
        } else if (idx >= 0 && idx < this.tagList.size()) {
            if (this.tagType == 0) {
                this.tagType = tag.getId();
            } else if (this.tagType != tag.getId()) {
                TutLauncher.LOGGER.warn("Adding mismatching tag types to tag list");
                return;
            }

            this.tagList.set(idx, tag);
        } else {
            TutLauncher.LOGGER.warn("index out of bounds to set tag in tag list");
        }
    }

    /**
     * Removes a tag at the given index.
     */
    public BaseTag removeTag(int i) {
        return this.tagList.remove(i);
    }

    /**
     * Return whether this compound has no tags.
     */
    public boolean hasNoTags() {
        return this.tagList.isEmpty();
    }

    /**
     * Retrieves the CompoundTag at the specified index in the list
     */
    public CompoundTag getCompoundTagAt(int i) {
        if (i >= 0 && i < this.tagList.size()) {
            BaseTag base = this.tagList.get(i);

            if (base.getId() == 10) {
                return (CompoundTag) base;
            }
        }

        return new CompoundTag();
    }

    public int getIntAt(int p_186858_1_) {
        if (p_186858_1_ >= 0 && p_186858_1_ < this.tagList.size()) {
            BaseTag base = this.tagList.get(p_186858_1_);

            if (base.getId() == 3) {
                return ((IntTag) base).getInt();
            }
        }

        return 0;
    }

    public int[] getIntArrayAt(int i) {
        if (i >= 0 && i < this.tagList.size()) {
            BaseTag base = this.tagList.get(i);

            if (base.getId() == 11) {
                return ((IntArrayTag) base).getIntArray();
            }
        }

        return new int[0];
    }

    public double getDoubleAt(int i) {
        if (i >= 0 && i < this.tagList.size()) {
            BaseTag base = this.tagList.get(i);

            if (base.getId() == 6) {
                return ((DoubleTag) base).getDouble();
            }
        }

        return 0.0D;
    }

    public float getFloatAt(int i) {
        if (i >= 0 && i < this.tagList.size()) {
            BaseTag base = this.tagList.get(i);

            if (base.getId() == 5) {
                return ((FloatTag) base).getFloat();
            }
        }

        return 0.0F;
    }

    /**
     * Retrieves the tag String value at the specified index in the list
     */
    public String getStringTagAt(int i) {
        if (i >= 0 && i < this.tagList.size()) {
            BaseTag base = this.tagList.get(i);
            return base.getId() == 8 ? base.getString() : base.toString();
        } else {
            return "";
        }
    }

    /**
     * Get the tag at the given position
     */
    public BaseTag get(int idx) {
        return (idx >= 0 && idx < this.tagList.size() ? this.tagList.get(idx) : new EndTag());
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
    public ListTag copy() {
        ListTag tagList = new ListTag();
        tagList.tagType = this.tagType;

        for (BaseTag base : this.tagList) {
            BaseTag base1 = base.copy();
            tagList.tagList.add(base1);
        }

        return tagList;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        } else {
            ListTag taglist = (ListTag) obj;
            return this.tagType == taglist.tagType && Objects.equals(this.tagList, taglist.tagList);
        }
    }

    public int getTagType() {
        return this.tagType;
    }

    @Override
    public java.util.Iterator<BaseTag> iterator() {
        return tagList.iterator();
    }
}
