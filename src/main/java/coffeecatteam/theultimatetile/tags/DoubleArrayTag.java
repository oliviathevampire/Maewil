package coffeecatteam.theultimatetile.tags;

import coffeecatteam.theultimatetile.tags.supers.BaseTag;

import java.util.Arrays;
import java.util.List;

/**
 * @author CoffeeCatRailway
 * Created: 16/12/2018
 */
public class DoubleArrayTag extends BaseTag {

    /**
     * The array of saved doubles
     */
    private double[] doubleArray;

    public DoubleArrayTag(double[] doubleArray) {
        this.doubleArray = doubleArray;
    }

    public DoubleArrayTag(List<Double> floatList) {
        this(toArray(floatList));
    }

    private static double[] toArray(List<Double> doubleList) {
        double[] doubles = new double[doubleList.size()];

        for (int i = 0; i < doubleList.size(); ++i) {
            Double aDouble = doubleList.get(i);
            doubles[i] = aDouble == null ? 0 : aDouble;
        }

        return doubles;
    }

    @Override
    public int getId() {
        return BaseTag.TAG_IDS.get("DOUBLE_ARRAY");
    }

    @Override
    public String toString() {
        StringBuilder stringbuilder = new StringBuilder("[");

        for (int i = 0; i < this.doubleArray.length; ++i) {
            if (i != 0) {
                stringbuilder.append(',');
            }

            stringbuilder.append(this.doubleArray[i]);
        }

        return stringbuilder.append(']').toString();
    }

    /**
     * Creates a clone of the tag.
     */
    @Override
    public DoubleArrayTag copy() {
        double[] afloat = new double[this.doubleArray.length];
        System.arraycopy(this.doubleArray, 0, afloat, 0, this.doubleArray.length);
        return new DoubleArrayTag(afloat);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && Arrays.equals(this.doubleArray, ((DoubleArrayTag) obj).doubleArray);
    }

    public double[] getDoubleArray() {
        return this.doubleArray;
    }
}
