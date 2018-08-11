package coffeecatteam.tilegame.items;

public class ItemStack {

    private Item item;
    private int count;

    public ItemStack(Item item) {
        this(item, 1);
    }

    public ItemStack(Item item, int count) {
        this.item = item;
        this.count = count;
        this.item.setCount(this.count);
    }

    public ItemStack setPosition(int x, int y) {
        this.item.setPosition(x, y);
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

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
        this.item.setCount(this.count);
    }
}
