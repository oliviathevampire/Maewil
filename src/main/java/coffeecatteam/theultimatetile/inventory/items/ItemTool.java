package coffeecatteam.theultimatetile.inventory.items;

import java.awt.image.BufferedImage;

public class ItemTool extends Item {

    private int damage;

    public ItemTool(BufferedImage texture, String id, int damage) {
        this(texture, id, damage, false);
    }

    public ItemTool(BufferedImage texture, String id, int damage, boolean isStackable) {
        super(texture, id);
        this.damage = damage;
        this.isStackable = isStackable;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
