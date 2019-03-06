package coffeecatteam.theultimatetile.objs.entities;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.inventory.Slot;
import coffeecatteam.theultimatetile.objs.items.Item;
import coffeecatteam.theultimatetile.objs.items.ItemStack;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * @author CoffeeCatRailway
 * Created: 22/02/2019
 */
public class EntityItem extends Entity {

    private ItemStack stack;
    private boolean pickedUp;

    public EntityItem(TutEngine tutEngine, Item item) {
        this(tutEngine, new ItemStack(item, 1));
    }

    public EntityItem(TutEngine tutEngine, ItemStack stack) {
        this(tutEngine, stack, false);
    }

    public EntityItem(TutEngine tutEngine, ItemStack stack, boolean pickedUp) {
        super(tutEngine, "item", Item.WIDTH, Item.HEIGHT, EntityHitType.NONE);
        this.stack = stack;
        this.pickedUp = pickedUp;
    }

    @Override
    public void update(GameContainer container, int delta) {
        if (this.tutEngine.getPlayer().isActive()) {
            if (this.isTouching(this.tutEngine.getPlayer())) {
                if (!this.tutEngine.getPlayer().getInventoryPlayer().isFull()) {
                    this.pickedUp = true;
                } else {
                    int hotbar = 0;
                    for (Slot slot : this.tutEngine.getPlayer().getInventoryPlayer().getSlots()) {
                        if (slot.getStack() != null) {
                            if (slot.getStack().getId().equals(this.stack.getId())) {
                                if (slot.getStack().getItem().isStackable()) {
                                    this.pickedUp = true;
                                }
                            } else {
                                hotbar++;
                            }
                        }
                    }

                    if (hotbar >= this.tutEngine.getPlayer().getInventoryPlayer().size()) {
                        if (!this.tutEngine.getPlayer().getInventoryPlayer().isHotbarFull()) {
                            this.pickedUp = true;
                        } else {
                            for (Slot slot : this.tutEngine.getPlayer().getInventoryPlayer().getSlots()) {
                                if (slot.getStack() != null) {
                                    if (slot.getStack().getId().equals(this.stack.getId())) {
                                        if (slot.getStack().getItem().isStackable()) {
                                            this.pickedUp = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (this.pickedUp) {
            if (!this.tutEngine.getPlayer().getInventoryPlayer().isFull())
                this.tutEngine.getPlayer().getInventoryPlayer().addItem(new ItemStack(stack.getItem(), stack.getCount()));
            else
                this.tutEngine.getPlayer().getInventoryPlayer().addStackToHotbar(new ItemStack(stack.getItem(), stack.getCount()));
        }
    }

    @Override
    public void render(Graphics g) {
        stack.getTexture().draw(renderX, renderY, width, height);
        if (stack.getCount() > 1) {
            int xPos = (int) (this.position.x - this.tutEngine.getCamera().getxOffset());
            int yPos = (int) (this.position.y + 15 - this.tutEngine.getCamera().getyOffset());
            Text.drawString(g, String.valueOf(stack.getCount()), xPos, yPos, false, false, Color.white, Assets.FONTS.get("20"));
        }
        this.renderEffect(g);
    }

    public ItemStack getStack() {
        return stack;
    }

    public void setStack(ItemStack stack) {
        this.stack = stack;
    }

    public boolean isPickedUp() {
        return pickedUp;
    }

    public void setPickedUp(boolean pickedUp) {
        this.pickedUp = pickedUp;
    }

    @Override
    public Entity newCopy() {
        return super.newCopy(new EntityItem(tutEngine, stack, pickedUp));
    }
}
