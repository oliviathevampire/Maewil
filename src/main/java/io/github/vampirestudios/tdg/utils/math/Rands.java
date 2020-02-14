package io.github.vampirestudios.tdg.utils.math;

import java.util.*;

public class Rands {

    private static final Random rand = new Random();

    public static Random getRandom() {
        return rand;
    }

    public static int randInt(int bound) {
        return rand.nextInt(bound);
    }

    public static int randIntRange(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }

    public static float randFloatRange(float min, float max) {
        return min + rand.nextFloat() * (max - min);
    }

    public static float randFloat(float bound) {
        return ((float) rand.nextInt((int) (bound * 10) + 1)) / 10;
    }

    public static boolean chance(int bound) {
        return randInt(bound) == 0;
    }

    public static <O extends Object> O values(O[] values) {
        return values[randInt(values.length)];
    }

    public static <O extends Object> O list(List<O> list) {
        return list.get(randInt(list.size()));
    }

    public static <O extends Object> List<O> lists(List<O> list, List<O> list2) {
        int int1 = randInt(list.size());
        int int2 = randInt(list2.size());
        return Arrays.asList(list.get(int1), list2.get(int2));
    }

    public static <K, V extends Object> Map.Entry<K, V> map(Map<K, V> map) {
        Set<Map.Entry<K, V>> entry = map.entrySet();
        return new ArrayList<>(entry).get(randInt(entry.size()));
    }
}
