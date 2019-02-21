package coffeecatteam.theultimatetile.objs.items;

import coffeecatteam.theultimatetile.TutEngine;

public class ItemTool extends Item {

    private int damage;
    private ToolType toolType;

    public ItemTool(TutEngine tutEngine, String id, int damage, ToolType toolType) {
        this(tutEngine, id, damage, toolType, false);
    }

    public ItemTool(TutEngine tutEngine, String id, int damage, ToolType toolType, boolean stackable) {
        super(tutEngine, id);
        this.damage = damage;
        this.stackable = stackable;
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
        SOWRD, PICKAXE, HOE
    }

    @Override
    public ItemTool newCopy() {
        return super.newCopy(new ItemTool(tutEngine, id, damage, toolType, stackable));
    }
}
