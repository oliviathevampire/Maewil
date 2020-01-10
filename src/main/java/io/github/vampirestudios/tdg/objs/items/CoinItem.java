package io.github.vampirestudios.tdg.objs.items;

import io.github.vampirestudios.tdg.start.TutEngine;

public class CoinItem extends Item {

    private float worth;

    public CoinItem(TutEngine tutEngine, String id) {
        this(tutEngine, id, 0.00f);
    }

    public CoinItem(TutEngine tutEngine, String id, float worth) {
        super(tutEngine, id);
        this.worth = worth;
    }

    public float addWorth(float worth) {
        this.worth += worth;
        return this.worth;
    }

    public float mulWorth(float worth) {
        this.worth *= worth;
        return this.worth;
    }

    public float getWorth() {
        return worth;
    }

    public void setWorth(float worth) {
        this.worth = worth;
    }

    @Override
    public CoinItem newCopy() {
        return super.newCopy(new CoinItem(tutEngine, id, worth));
    }
}
