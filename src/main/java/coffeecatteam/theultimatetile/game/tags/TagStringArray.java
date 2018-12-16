package coffeecatteam.theultimatetile.game.tags;

import coffeecatteam.theultimatetile.game.tags.supers.TagBase;

import java.util.Arrays;
import java.util.List;

/**
 * @author CoffeeCatRailway
 * Created: 16/12/2018
 */
public class TagStringArray extends TagBase {

    /**
     * The array of saved integers
     */
    private String[] stringArray;

    public TagStringArray(String[] stringArray) {
        this.stringArray = stringArray;
    }

    public TagStringArray(List<String> stringList) {
        this(toArray(stringList));
    }

    private static String[] toArray(List<String> stringList) {
        String[] strings = new String[stringList.size()];

        for (int i = 0; i < stringList.size(); ++i) {
            String s = stringList.get(i);
            strings[i] = s == null ? "" : s;
        }

        return strings;
    }

    @Override
    public int getId() {
        return TagBase.TAG_IDS.get("STRING_ARRAY");
    }

    @Override
    public String toString() {
        StringBuilder stringbuilder = new StringBuilder("[");

        for (int i = 0; i < this.stringArray.length; ++i) {
            if (i != 0) {
                stringbuilder.append(',');
            }

            stringbuilder.append(TagString.quoteAndEscape(this.stringArray[i]));
        }

        return stringbuilder.append(']').toString();
    }

    /**
     * Creates a clone of the tag.
     */
    @Override
    public TagStringArray copy() {
        String[] strings = new String[this.stringArray.length];
        System.arraycopy(this.stringArray, 0, strings, 0, this.stringArray.length);
        return new TagStringArray(strings);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && Arrays.equals(this.stringArray, ((TagStringArray) obj).stringArray);
    }

    public String[] getStringArray() {
        return this.stringArray;
    }
}
