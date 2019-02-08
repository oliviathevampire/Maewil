package coffeecatteam.theultimatetile.tags;

import coffeecatteam.theultimatetile.tags.supers.TagBase;

import java.util.Arrays;
import java.util.List;

/**
 * @author CoffeeCatRailway
 * Created: 16/12/2018
 */
public class TagFloatArray extends TagBase {

    /**
     * The array of saved floats
     */
    private float[] floatArray;

    public TagFloatArray(float[] floatArray) {
        this.floatArray = floatArray;
    }

    public TagFloatArray(List<Float> floatList) {
        this(toArray(floatList));
    }

    private static float[] toArray(List<Float> floatList) {
        float[] afloat = new float[floatList.size()];

        for (int i = 0; i < floatList.size(); ++i) {
            Float aFloat = floatList.get(i);
            afloat[i] = aFloat == null ? 0 : aFloat;
        }

        return afloat;
    }

    @Override
    public int getId() {
        return TagBase.TAG_IDS.get("FLOAT_ARRAY");
    }

    @Override
    public String toString() {
        StringBuilder stringbuilder = new StringBuilder("[");

        for (int i = 0; i < this.floatArray.length; ++i) {
            if (i != 0) {
                stringbuilder.append(',');
            }

            stringbuilder.append(this.floatArray[i]);
        }

        return stringbuilder.append(']').toString();
    }

    /**
     * Creates a clone of the tag.
     */
    @Override
    public TagFloatArray copy() {
        float[] afloat = new float[this.floatArray.length];
        System.arraycopy(this.floatArray, 0, afloat, 0, this.floatArray.length);
        return new TagFloatArray(afloat);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && Arrays.equals(this.floatArray, ((TagFloatArray) obj).floatArray);
    }

    public float[] getFloatArray() {
        return this.floatArray;
    }
}
