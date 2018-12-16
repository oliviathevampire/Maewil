package coffeecatteam.theultimatetile.game.tags;

import coffeecatteam.theultimatetile.game.tags.supers.TagBase;

import java.util.Arrays;
import java.util.List;

/**
 * @author CoffeeCatRailway
 * Created: 16/12/2018
 */
public class TagIntArray extends TagBase {

    /**
     * The array of saved integers
     */
    private int[] intArray;

    public TagIntArray(int[] intArray) {
        this.intArray = intArray;
    }

    public TagIntArray(List<Integer> intList) {
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
        return TagBase.TAG_IDS.get("INT_ARRAY");
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
    public TagIntArray copy() {
        int[] aint = new int[this.intArray.length];
        System.arraycopy(this.intArray, 0, aint, 0, this.intArray.length);
        return new TagIntArray(aint);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && Arrays.equals(this.intArray, ((TagIntArray) obj).intArray);
    }

    public int[] getIntArray() {
        return this.intArray;
    }
}
