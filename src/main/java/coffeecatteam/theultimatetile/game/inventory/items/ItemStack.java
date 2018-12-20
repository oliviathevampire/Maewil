package coffeecatteam.theultimatetile.game.inventory.items;

import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.tags.TagCompound;
import coffeecatteam.theultimatetile.game.tags.TagUtils;
import coffeecatteam.theultimatetile.manager.ItemManager;

import java.awt.image.BufferedImage;

public class ItemStack {

    public static final int MAX_STACK_COUNT = 128;

    private Item item;
    private int count;

    public ItemStack(TagCompound compound) {
        this(ItemManager.getItemById(compound.getString("id")), compound.getInteger("count"));
        if (compound.hasKey("pos"))
            setPosition(TagUtils.getPosFromTag(compound.getCompoundTag("pos")));
    }

    public ItemStack(Item item) {
        this(item, 1);
    }

    public ItemStack(Item item, int count) {
        try {
            this.item = (Item) item.clone();
        } catch (CloneNotSupportedException e) {
            Engine.getEngine().getLogger().print(e);
            this.item = ItemManager.STICK;
        }
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

    public BufferedImage getTexture() {
        return this.item.getTexture();
    }

    public void setTexture(BufferedImage texture) {
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

    public void setGameEngine(GameEngine gameEngine) {
        this.item.setGameEngine(gameEngine);
    }

    public boolean isPickedUp() {
        return this.item.isPickedUp();
    }

    public void setPickedUp(boolean pickedUp) {
        this.item.setPickedUp(pickedUp);
    }
}
