package coffeecatteam.theultimatetile.objs.items.tool;

import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.objs.items.IInteractable;
import coffeecatteam.theultimatetile.objs.items.Item;

public abstract class ItemTool extends Item implements IInteractable {

    protected int damage;
    protected ToolType toolType;

    public ItemTool(TutEngine tutEngine, String id, int damage, ToolType toolType) {
        super(tutEngine, id);
        this.damage = damage;
        this.stackable = false;
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
        SOWRD, AXE, PICKAXE, SHOVEL, HOE, MULTI, SHOOTER
    }
}
