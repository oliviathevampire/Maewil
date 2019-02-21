package coffeecatteam.theultimatetile.objs.items;

import coffeecatteam.theultimatetile.TutEngine;

public class ItemCoin extends Item {

    private float worth;

    public ItemCoin(TutEngine tutEngine, String id) {
        this(tutEngine, id, 0.00f);
    }

    public ItemCoin(TutEngine tutEngine, String id, float worth) {
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
    public ItemCoin newCopy() {
        return super.newCopy(new ItemCoin(tutEngine, id, worth));
    }
}
