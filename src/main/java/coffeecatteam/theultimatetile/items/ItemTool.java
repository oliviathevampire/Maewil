package coffeecatteam.theultimatetile.items;

import java.awt.image.BufferedImage;

public class ItemTool extends Item {

    private int damage;

    public ItemTool(BufferedImage texture, String name, int id, int damage) {
        this(texture, name, id, damage, false);
    }

    public ItemTool(BufferedImage texture, String name, int id, int damage, boolean isStackable) {
        super(texture, name, id);
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
