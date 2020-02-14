package io.github.vampirestudios.tdg.objs;

import java.util.List;

public class AnimationSimple {

    private int length;
    private int speed;
    private List<Integer> startPos;

    public AnimationSimple(int length, int speed, List<Integer> startPos) {
        this.length = length;
        this.speed = speed;
        this.startPos = startPos;
    }

    public int getLength() {
        return length;
    }

    public int getSpeed() {
        return speed;
    }

    public List<Integer> getStartPos() {
        return startPos;
    }

}