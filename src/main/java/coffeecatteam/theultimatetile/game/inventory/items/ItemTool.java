package coffeecatteam.theultimatetile.game.inventory.items;

import org.newdawn.slick.Image;

public class ItemTool extends Item {

    private int damage;
    private ToolType toolType;

    public ItemTool(Image texture, String id, int damage, ToolType toolType) {
        this(texture, id, damage, toolType, false);
    }

    public ItemTool(Image texture, String id, int damage, ToolType toolType, boolean isStackable) {
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
