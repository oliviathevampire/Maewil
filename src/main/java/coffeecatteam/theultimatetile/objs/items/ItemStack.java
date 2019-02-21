package coffeecatteam.theultimatetile.objs.items;

import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.tags.TagCompound;
import coffeecatteam.theultimatetile.tags.TagUtils;
import org.newdawn.slick.Image;

public class ItemStack {

    public static final int MAX_STACK_COUNT = 128;

    private Item item;
    private int count;

    public ItemStack(TagCompound compound) {
        this(Items.getItemById(compound.getString("id")), compound.getInteger("count"));
        if (compound.hasKey("pos"))
            setPosition(TagUtils.getPosFromTag(compound.getCompoundTag("pos")));
    }

    public ItemStack(Item item) {
        this(item, 1);
    }

    public ItemStack(Item item, int count) {
        this.item = item.newCopy();
        setCount(count);
    }

    public void saveToTagCompound(TagCompound compound) {
        compound.setString("id", this.getId());
        compound.setInteger("count", this.getCount());
        compound.setTag("pos", TagUtils.createPosTag(this.getPosition()));
    }

    public Vector2D getPosition() {
        return this.item.getPosition();
    }

    public ItemStack setPosition(int x, int y) {
        return setPosition(new Vector2D(x, y));
    }

    public ItemStack setPosition(Vector2D pos) {
        this.item.setPosition(pos);
        return new ItemStack(this.item, this.count);
    }

    public ItemStack copy() {
        ItemStack itemStack = new ItemStack(this.item, this.count);
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

    public boolean isPickedUp() {
        return this.item.isPickedUp();
    }

    public void setPickedUp(boolean pickedUp) {
        this.item.setPickedUp(pickedUp);
    }
}
