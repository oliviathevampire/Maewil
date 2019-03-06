package coffeecatteam.theultimatetile.tags;

import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.objs.items.ItemStack;

/**
 * @author CoffeeCatRailway
 * Created: 15/12/2018
 */
public class TagUtils {

    /**
     * Creates a Vector2D object from the data stored in the passed TagCompound.
     */
    public static Vector2D getPosFromTag(TagCompound compound) {
        return new Vector2D(compound.getDouble("x"), compound.getDouble("y"));
    }

    /**
     * Creates a new TagCompound from a Vector2D.
     */
    public static TagCompound createPosTag(Vector2D pos) {
        TagCompound compound = new TagCompound();
        compound.setDouble("x", pos.x);
        compound.setDouble("y", pos.y);
        return compound;
    }

    /**
     * Creates a Item object from the data stored in the passed TagCompound.
     */
    public static ItemStack getItemFromTag(TagCompound compound) {
        ItemStack stack = new ItemStack(compound);
        return stack;
    }

    /**
     * Creates a new TagCompound from a Item.
     */
    public static TagCompound createItemTag(ItemStack stack) {
        TagCompound compound = new TagCompound();
        stack.saveToTagCompound(compound);
        return compound;
    }
}
