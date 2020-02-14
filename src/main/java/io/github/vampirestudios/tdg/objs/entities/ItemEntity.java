package io.github.vampirestudios.tdg.objs.entities;

import io.github.vampirestudios.tdg.gfx.Text;
import io.github.vampirestudios.tdg.gfx.assets.Assets;
import io.github.vampirestudios.tdg.inventory.Slot;
import io.github.vampirestudios.tdg.objs.items.Item;
import io.github.vampirestudios.tdg.objs.items.ItemStack;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;
public class ItemEntity extends Entity {

    private ItemStack stack;
    private boolean pickedUp;

    public ItemEntity(MaewilEngine maewilEngine, Item item) {
        this(maewilEngine, new ItemStack(item, 1));
    }

    public ItemEntity(MaewilEngine maewilEngine, ItemStack stack) {
        this(maewilEngine, stack, false);
    }

    public ItemEntity(MaewilEngine maewilEngine, ItemStack stack, boolean pickedUp) {
        super(maewilEngine, "item", Item.WIDTH, Item.HEIGHT, HitType.NONE);
        this.stack = stack;
        this.pickedUp = pickedUp;
        this.isCollidable = false;
    }

    @Override
    public String getName() {
        return stack != null ? stack.getItem().getName() : "Item";
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {
        if (this.maewilEngine.getPlayer().isActive()) {
            if (this.isTouching(this.maewilEngine.getPlayer())) {
                if (!this.maewilEngine.getPlayer().getInventoryPlayer().isFull()) {
                    this.pickedUp = true;
                } else {
                    int hotbar = 0;
                    for (Slot slot : this.maewilEngine.getPlayer().getInventoryPlayer().getSlots()) {
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

                    if (hotbar >= this.maewilEngine.getPlayer().getInventoryPlayer().size()) {
                        if (!this.maewilEngine.getPlayer().getInventoryPlayer().isHotbarFull()) {
                            this.pickedUp = true;
                        } else {
                            for (Slot slot : this.maewilEngine.getPlayer().getInventoryPlayer().getSlots()) {
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
            if (!this.maewilEngine.getPlayer().getInventoryPlayer().isFull())
                this.maewilEngine.getPlayer().getInventoryPlayer().addItem(new ItemStack(stack.getItem(), stack.getCount()));
            else
                this.maewilEngine.getPlayer().getInventoryPlayer().addStackToHotbar(new ItemStack(stack.getItem(), stack.getCount()));
            this.hurt(currentHealth);
        }
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        stack.getTexture().draw(renderX, renderY, width, height);
        if (stack.getCount() > 1) {
            int xPos = (int) (this.position.x - this.maewilEngine.getCamera().getXOffset());
            int yPos = (int) (this.position.y + 15 - this.maewilEngine.getCamera().getYOffset());
            Text.drawString(g, String.valueOf(stack.getCount()), xPos, yPos, false, false, Color.white, Assets.FONTS.get("20"));
        }
        this.renderEffect(container, game, g);
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
        return super.newCopy(new ItemEntity(maewilEngine, stack, pickedUp));
    }
}
