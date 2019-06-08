package coffeecatteam.theultimatetile.objs.items;

import coffeecatteam.theultimatetile.tags.CompoundTag;
import org.newdawn.slick.Image;

public class ItemStack {

    public static final int MAX_STACK_COUNT = 128;

    private Item item;
    private int count;

    public ItemStack(CompoundTag compound) {
        this(Items.getItemById(compound.getString("id")), compound.getInteger("count"));
    }

    public ItemStack(Item item) {
        this(item, 1);
    }

    public ItemStack(Item item, int count) {
        this.item = item.newCopy();
        setCount(count);
    }

    public void saveToTagCompound(CompoundTag compound) {
        compound.setString("id", this.getId());
        compound.setInteger("count", this.getCount());
    }

    public ItemStack copy() {
        ItemStack itemStack = new ItemStack(this.item.newCopy(), this.count);
        return itemStack;
    }

    public Item getItem() {
        return this.item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Image getTexture() {
        return this.item.getTexture();
    }

    public void setTexture(Image texture) {
        this.item.setTexture(texture);
    }

    public String getId() {
        return this.item.getId();
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
        if (this.count > MAX_STACK_COUNT)
            this.count = MAX_STACK_COUNT;
    }
}
