package coffeecatteam.theultimatetile.game.tags;

import coffeecatteam.theultimatetile.game.tags.supers.TagBase;

import java.util.Arrays;
import java.util.List;

/**
 * @author CoffeeCatRailway
 * Created: 16/12/2018
 */
public class TagDoubleArray extends TagBase {

    /**
     * The array of saved doubles
     */
    private double[] doubleArray;

    public TagDoubleArray(double[] doubleArray) {
        this.doubleArray = doubleArray;
    }

    public TagDoubleArray(List<Double> floatList) {
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
        return TagBase.TAG_IDS.get("DOUBLE_ARRAY");
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
    public TagDoubleArray copy() {
        double[] afloat = new double[this.doubleArray.length];
        System.arraycopy(this.doubleArray, 0, afloat, 0, this.doubleArray.length);
        return new TagDoubleArray(afloat);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && Arrays.equals(this.doubleArray, ((TagDoubleArray) obj).doubleArray);
    }

    public double[] getDoubleArray() {
        return this.doubleArray;
    }
}
