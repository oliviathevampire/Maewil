package io.github.vampirestudios.tdg.inventory;

import io.github.vampirestudios.tdg.gfx.assets.Assets;
import io.github.vampirestudios.tdg.objs.entities.creatures.PlayerEntity;
import io.github.vampirestudios.tdg.objs.items.IInteractable;
import io.github.vampirestudios.tdg.objs.items.ItemStack;
import io.github.vampirestudios.tdg.objs.tiles.Tile;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.start.MaewilLauncher;
import io.github.vampirestudios.tdg.screen.options.OptionsScreen;
import io.github.vampirestudios.tdg.screen.options.controls.Keybind;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public abstract class InventoryAbstractPlayer extends Inventory {

    private static int maxHotbarSize = 9;
    private int inventorySelectedIndex = 0;
    private int hotbarSelectedIndex = 0;

    protected boolean isDefault = false;

    public InventoryAbstractPlayer(MaewilEngine maewilEngine, PlayerEntity player, String invName) {
        this(maewilEngine, player, invName, 190, 360);
    }

    public InventoryAbstractPlayer(MaewilEngine maewilEngine, PlayerEntity player, String invName, int xOff, int yOff) {
        super(maewilEngine, player, invName);

        // Add inventory slots
        int x, y;
        int width = 48, height = 48;
        for (int i = 0; i < maxSize; i++) {
            x = xOff + 54 * i;
            y = yOff;
            if (i > maxSize / 2 - 1) {
                x -= width * 7 - 12;
                y += height + 5;
            }
            Slot s = new Slot(i, x, y, width, height);
            addSlot(s).setSelector(Assets.GUI_SLOT_SELECTER);
        }

        // Add hotbar slots
        int hxd = (MaewilLauncher.WIDTH / 2 - width / 2) - width - 150, hx;
        for (int i = 0; i < maxHotbarSize; i++) {
            hx = hxd + 54 * i;
            Slot s = new Slot(i, hx, MaewilLauncher.HEIGHT - height - height / 2 + 13, width, height);
            addSlot(s).setSelector(Assets.GUI_HOTBAR_SELECTER);
        }
    }

    public static void copyItems(InventoryAbstractPlayer from, InventoryAbstractPlayer to, boolean includeNull) {
        for (int i = 0; i < maxSize + maxHotbarSize; i++) {
            Slot s1 = from.getSlot(i);
            Slot s2 = to.getSlot(i);

            if (includeNull)
                s2.setStack(s1.getStack());
            else {
                if (s1.getStack() != null) {
                    s2.setStack(s1.getStack().copy());
                }
            }
        }
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {
        if (active) {
            // Change select item
            boolean up = maewilEngine.getKeyManager().keyJustPressed(OptionsScreen.OPTIONS.controls().get(Keybind.W).getKeyCode());
            boolean down = maewilEngine.getKeyManager().keyJustPressed(OptionsScreen.OPTIONS.controls().get(Keybind.S).getKeyCode());
            boolean left = maewilEngine.getKeyManager().keyJustPressed(OptionsScreen.OPTIONS.controls().get(Keybind.A).getKeyCode());
            boolean right = maewilEngine.getKeyManager().keyJustPressed(OptionsScreen.OPTIONS.controls().get(Keybind.D).getKeyCode());

            if (up || down) {
                inventorySelectedIndex += 6;
                if (inventorySelectedIndex > maxSize - 1)
                    inventorySelectedIndex -= maxSize;
            }
            if (left)
                inventorySelectedIndex -= 1;
            if (right)
                inventorySelectedIndex += 1;

            if (inventorySelectedIndex < 0)
                inventorySelectedIndex = maxSize - 1;
            if (inventorySelectedIndex > maxSize - 1)
                inventorySelectedIndex = 0;

            if (inventorySelectedIndex < slots.size()) {
                ItemStack stack = getSlot(inventorySelectedIndex).getStack();

                if (stack != null) {
                    // Check if item was interacted with
                    if (maewilEngine.getKeyManager().keyJustPressed(OptionsScreen.OPTIONS.controls().get(Keybind.R).getKeyCode())) {
                        if (stack.getItem() instanceof IInteractable)
                            if (((IInteractable) stack.getItem()).onInteracted(player))
                                stack.setCount(stack.getCount() - 1);
                    }

                    // Check if item count is 0
                    if (stack.getCount() <= 0)
                        getSlot(inventorySelectedIndex).remove();
                }
            }

            // Swap selected stacks
            if (maewilEngine.getKeyManager().keyJustPressed(OptionsScreen.OPTIONS.controls().get(Keybind.Z).getKeyCode()))
                swapSlots(getSlot(inventorySelectedIndex), getSlot(maxSize + hotbarSelectedIndex));
        }
        if (this.active)
            if (maewilEngine.getKeyManager().keyJustPressed(OptionsScreen.OPTIONS.controls().get(Keybind.Q).getKeyCode()))
                dropItem(active, inventorySelectedIndex, hotbarSelectedIndex);

        if (maewilEngine.getKeyManager().keyJustPressed(OptionsScreen.OPTIONS.controls().get(Keybind.ONE).getKeyCode()))
            hotbarSelectedIndex = 0;
        if (maewilEngine.getKeyManager().keyJustPressed(OptionsScreen.OPTIONS.controls().get(Keybind.TWO).getKeyCode()))
            hotbarSelectedIndex = 1;
        if (maewilEngine.getKeyManager().keyJustPressed(OptionsScreen.OPTIONS.controls().get(Keybind.THREE).getKeyCode()))
            hotbarSelectedIndex = 2;
        if (maewilEngine.getKeyManager().keyJustPressed(OptionsScreen.OPTIONS.controls().get(Keybind.FOUR).getKeyCode()))
            hotbarSelectedIndex = 3;
        if (maewilEngine.getKeyManager().keyJustPressed(OptionsScreen.OPTIONS.controls().get(Keybind.FIVE).getKeyCode()))
            hotbarSelectedIndex = 4;
        if (maewilEngine.getKeyManager().keyJustPressed(OptionsScreen.OPTIONS.controls().get(Keybind.SIX).getKeyCode()))
            hotbarSelectedIndex = 5;
        if (maewilEngine.getKeyManager().keyJustPressed(OptionsScreen.OPTIONS.controls().get(Keybind.SEVEN).getKeyCode()))
            hotbarSelectedIndex = 6;
        if (maewilEngine.getKeyManager().keyJustPressed(OptionsScreen.OPTIONS.controls().get(Keybind.EIGHT).getKeyCode()))
            hotbarSelectedIndex = 7;
        if (maewilEngine.getKeyManager().keyJustPressed(OptionsScreen.OPTIONS.controls().get(Keybind.NINE).getKeyCode()))
            hotbarSelectedIndex = 8;
        if (hotbarSelectedIndex < maxSize + maxHotbarSize) {
            player.setEquippedItem(getSlot(maxSize + hotbarSelectedIndex).getStack());

            if (player.getEquippedItem() != null && player.getEquippedItem().getCount() <= 0)
                getSlot(maxSize + hotbarSelectedIndex).remove();
        }
    }

    // Render inventory slots
    public void renderInventorySlots(GameContainer container, StateBasedGame game, Graphics g) {
        for (int i = 0; i < maxSize; i++) {
            getSlot(i).setSelected(i == inventorySelectedIndex);

            getSlot(i).render(container, game, g);
        }
        for (int i = 0; i < maxSize; i++)
            getSlot(i).postRender(container, game, g);
    }

    public void renderHotbar(GameContainer container, StateBasedGame game, Graphics g) {
        // Render hotbar backgorund
        int barWidth = 82;
        int barHeight = 10;

        int multiplier = 6;
        int width = barWidth * multiplier;
        int height = barHeight * multiplier;
        int y = MaewilLauncher.HEIGHT - height - 5;

        Assets.GUI_HOTBAR.draw(MaewilLauncher.WIDTH / 2f - width / 2f, y, width, height);

        // Render hotbar slots
        for (int i = maxSize; i < maxSize + maxHotbarSize; i++) {
            // Render hotbar slots
            int selected = i - maxSize;
            getSlot(i).setSelected(selected == hotbarSelectedIndex);

            getSlot(i).render(container, game, g);
        }
        for (int i = maxSize; i < maxSize + maxHotbarSize; i++)
            getSlot(i).postRender(container, game, g);
    }

    public void dropItem(boolean active, int inventorySelectedIndex, int hotbarSelectedIndex) {
        float xOff = Tile.TILE_SIZE / 4f;
        float yOff = Tile.TILE_SIZE + Tile.TILE_SIZE / 4f;
        if (getSlot(inventorySelectedIndex).getStack() != null) {
            if (active) {
                maewilEngine.getEntityManager().addItem(getSlot(inventorySelectedIndex).remove(), (player.getX() + xOff) / Tile.TILE_SIZE, (player.getY() + yOff) / Tile.TILE_SIZE);
            } else {
                maewilEngine.getEntityManager().addItem(getSlot(hotbarSelectedIndex + maxHotbarSize).remove(), (player.getX() + xOff) / Tile.TILE_SIZE, (player.getY() + yOff) / Tile.TILE_SIZE);
            }
        }
    }

    public boolean addItem(ItemStack stackIn) {
        if (!isFull()) {
            for (Slot slot : getSlots()) {
                if (slot.getStack() != null && stackIn != null) {
                    if (slot.getStack().getId().equals(stackIn.getId())) {
                        if (slot.getStack().getItem().isStackable()) {
                            if (slot.getStack().getCount() != ItemStack.MAX_STACK_COUNT) {
                                int size = slot.getStack().getCount() + stackIn.getCount();
                                if (size > ItemStack.MAX_STACK_COUNT) {
                                    int extra = size - ItemStack.MAX_STACK_COUNT;
                                    ItemStack extraStack = new ItemStack(slot.getStack().getItem(), extra);
                                    if (!isFull()) {
                                        add(extraStack);
                                        slot.getStack().setCount(size);
                                    }
                                } else {
                                    slot.getStack().setCount(size);
                                }
                                return true;
                            }
                        }
                    }
                }
            }
            add(stackIn);
        }
        return false;
    }

    public void addStackToHotbar(ItemStack stack) {
        for (int i = maxSize; i < maxSize + maxHotbarSize; i++) {
            ItemStack stack1 = getSlot(i).getStack();

            if (stack1 != null) {
                if (stack1.getId().equals(stack.getId())) {
                    if (stack1.getItem().isStackable()) {
                        int size = stack1.getCount() + stack.getCount();
                        stack1.setCount(size);
                        stack.setCount(stack.getCount() - size);
                        return;
                    }
                }
            } else {
                getSlot(i).setStack(stack);
                removeFromInventory(stack);
                return;
            }
        }
    }

    private void removeFromInventory(ItemStack stack) {
        for (int i = 0; i < maxSize; i++)
            if (getSlot(i).getStack() == stack)
                getSlot(i).remove();
    }

    public boolean isPInventoryFull() {
        int size = 0;
        for (int i = 0; i < maxSize; i++)
            if (getSlot(i).getStack() != null)
                size++;
        return size >= maxSize;
    }

    public boolean isHotbarFull() {
        int size = 0;
        for (int i = maxSize; i < maxSize + maxHotbarSize; i++)
            if (getSlot(i).getStack() != null)
                size++;
        return size >= maxSize + maxHotbarSize;
    }

    public ItemStack getSelectedHotbarItemStack() {
        if (hotbarIsntEmpty())
            return getSlot(maxSize + hotbarSelectedIndex).getStack();
        return null;
    }

    public boolean hotbarIsntEmpty() {
        int size = 0;
        for (int i = 0; i < maxSize + maxHotbarSize; i++)
            if (getSlot(i).getStack() != null)
                size++;
        return size <= maxSize + maxHotbarSize;
    }

    public int size() {
        int size = 0;
        for (Slot slot : slots)
            if (slot.getStack() != null)
                size++;
        return size;
    }

    public int getInventorySelectedIndex() {
        return inventorySelectedIndex;
    }

    public void setInventorySelectedIndex(int inventorySelectedIndex) {
        this.inventorySelectedIndex = inventorySelectedIndex;
    }

    public int getHotbarSelectedIndex() {
        return hotbarSelectedIndex;
    }

    public void setHotbarSelectedIndex(int hotbarSelectedIndex) {
        this.hotbarSelectedIndex = hotbarSelectedIndex;
    }

    @Override
    public void onOpen() {
        copyItems(maewilEngine.getPlayer().getInventoryPlayer(), this, false);
    }

    @Override
    public void onClose() {
        copyItems(this, maewilEngine.getPlayer().getInventoryPlayer(), false);
    }
}
