package coffeecatteam.theultimatetile.game.inventory.items;

import java.awt.image.BufferedImage;

public class ItemCoin extends Item {

    private float worth;

    public ItemCoin(BufferedImage texture, String id) {
        this(texture, id, 0.00f);
    }

    public ItemCoin(BufferedImage texture, String id, float worth) {
        super(texture, id);
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
}
