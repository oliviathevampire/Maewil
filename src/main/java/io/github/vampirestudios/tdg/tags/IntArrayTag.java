package io.github.vampirestudios.tdg.tags;

import io.github.vampirestudios.tdg.tags.supers.BaseTag;

import java.util.Arrays;
import java.util.List;

public class IntArrayTag extends BaseTag {

    /**
     * The array of saved integers
     */
    private int[] intArray;

    public IntArrayTag(int[] intArray) {
        this.intArray = intArray;
    }

    public IntArrayTag(List<Integer> intList) {
        this(toArray(intList));
    }

    private static int[] toArray(List<Integer> intList) {
        int[] aint = new int[intList.size()];

        for (int i = 0; i < intList.size(); ++i) {
            Integer integer = intList.get(i);
            aint[i] = integer == null ? 0 : integer;
        }

        return aint;
    }

    @Override
    public int getId() {
        return BaseTag.TAG_IDS.get("INT_ARRAY");
    }

    @Override
    public String toString() {
        StringBuilder stringbuilder = new StringBuilder("[");

        for (int i = 0; i < this.intArray.length; ++i) {
            if (i != 0) {
                stringbuilder.append(',');
            }

            stringbuilder.append(this.intArray[i]);
        }

        return stringbuilder.append(']').toString();
    }

    /**
     * Creates a clone of the tag.
     */
    @Override
    public IntArrayTag copy() {
        int[] aint = new int[this.intArray.length];
        System.arraycopy(this.intArray, 0, aint, 0, this.intArray.length);
        return new IntArrayTag(aint);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && Arrays.equals(this.intArray, ((IntArrayTag) obj).intArray);
    }

    public int[] getIntArray() {
        return this.intArray;
    }
}
