package coffeecatteam.theultimatetile.inventory.items;

import java.awt.image.BufferedImage;

public class ItemTool extends Item {

    private int damage;
    private ToolType toolType;

    public ItemTool(BufferedImage texture, String id, int damage, ToolType toolType) {
        this(texture, id, damage, toolType, false);
    }

    public ItemTool(BufferedImage texture, String id, int damage, ToolType toolType, boolean isStackable) {
        super(texture, id);
        this.damage = damage;
        this.isStackable = isStackable;
        this.toolType = toolType;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public ToolType getToolType() {
        return toolType;
    }

    public enum ToolType {
        SOWRD, PICKAXE, HOE;
    }
}
