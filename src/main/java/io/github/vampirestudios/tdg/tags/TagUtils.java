package io.github.vampirestudios.tdg.tags;

import coffeecatteam.coffeecatutils.position.Vector2D;
import io.github.vampirestudios.tdg.objs.items.ItemStack;

public class TagUtils {

    /**
     * Creates a Vector2D object from the data stored in the passed CompoundTag.
     */
    public static Vector2D getPosFromTag(CompoundTag compound) {
        return new Vector2D(compound.getDouble("x"), compound.getDouble("y"));
    }

    /**
     * Creates a new CompoundTag from a Vector2D.
     */
    public static CompoundTag createPosTag(Vector2D pos) {
        CompoundTag compound = new CompoundTag();
        compound.setDouble("x", pos.x);
        compound.setDouble("y", pos.y);
        return compound;
    }

    /**
     * Creates a Item object from the data stored in the passed CompoundTag.
     */
    public static ItemStack getItemFromTag(CompoundTag compound) {
        ItemStack stack = new ItemStack(compound);
        return stack;
    }

    /**
     * Creates a new CompoundTag from a Item.
     */
    public static CompoundTag createItemTag(ItemStack stack) {
        CompoundTag compound = new CompoundTag();
        stack.saveToTagCompound(compound);
        return compound;
    }
}
