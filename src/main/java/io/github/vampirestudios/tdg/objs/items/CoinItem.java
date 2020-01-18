package io.github.vampirestudios.tdg.objs.items;

import io.github.vampirestudios.tdg.start.MaewilEngine;

public class CoinItem extends Item {

    private float worth;

    public CoinItem(MaewilEngine maewilEngine, String id) {
        this(maewilEngine, id, 0.00f);
    }

    public CoinItem(MaewilEngine maewilEngine, String id, float worth) {
        super(maewilEngine, id);
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
        return super.newCopy(new CoinItem(maewilEngine, id, worth));
    }
}
