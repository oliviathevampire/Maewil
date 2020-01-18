package io.github.vampirestudios.tdg.objs.items.tool;

import io.github.vampirestudios.tdg.objs.items.IInteractable;
import io.github.vampirestudios.tdg.objs.items.Item;
import io.github.vampirestudios.tdg.start.MaewilEngine;

public abstract class ToolItem extends Item implements IInteractable {

    protected int damage;
    protected ToolType toolType;

    public ToolItem(MaewilEngine maewilEngine, String id, int damage, ToolType toolType) {
        super(maewilEngine, id);
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
