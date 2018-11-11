package coffeecatteam.theultimatetile.inventory;

import coffeecatteam.theultimatetile.GameEngine;
import coffeecatteam.theultimatetile.entities.creatures.EntityPlayer;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.inventory.items.Item;
import coffeecatteam.theultimatetile.inventory.items.ItemStack;
import coffeecatteam.theultimatetile.manager.ItemManager;
import coffeecatteam.theultimatetile.state.StateOptions;
import coffeecatteam.theultimatetile.state.options.controls.Keybind;
import coffeecatteam.utils.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

public class InventoryCampfire extends InventoryAbstractPlayer {

    private BufferedImage flame = Assets.getSpriteExact("/assets/textures/gui/inventory/campfire.png", 57, 0, 16, 15);
    private Map<String, String> TAGS;

    public InventoryCampfire(GameEngine gameEngine, EntityPlayer player, Map<String, String> TAGS) {
        super(gameEngine, player, "Campfire", 190, 370);
        this.TAGS = TAGS;

        int y = 266;
        addSlot(0, 270, y).setSelector(Assets.SLOT_SELECTER);
    }

    @Override
    public void onOpen() {
        String tagItem = TAGS.get("item");
        String[] itemData = tagItem.split(":");
        if (itemData[0].equals("null"))
            slots.get(slots.size() - 1).setStack(null);
        else {
            Item item = ItemManager.getItemById(itemData[0]);
            int count = Utils.parseInt(itemData[1]);
            slots.get(slots.size() - 1).setStack(new ItemStack(item, count));
        }
        super.onOpen();
    }

    @Override
    public void onClose() {
        if (slots.get(slots.size() - 1).getStack() == null)
            TAGS.replace("item", "null:0");
        else {
            ItemStack stack = slots.get(slots.size() - 1).getStack();
            String id = stack.getId();
            String amt = String.valueOf(stack.getCount());
            TAGS.replace("item", id + ":" + amt);
        }
        super.onClose();
    }

    @Override
    public void tick() {
        super.tick();

        if (active) {
            if (gameEngine.getKeyManager().keyJustPressed(StateOptions.OPTIONS.controls().get(Keybind.X).getKeyCode())) {
                //if (slots.get(getInventorySelectedIndex()).getStack() == null || slots.get(getInventorySelectedIndex()).getStack().getItem() instanceof ItemFood) {
                swapSlots(slots.get(slots.size() - 1), slots.get(getInventorySelectedIndex()));
                //}
            }
        }
    }

    @Override
    public void render(Graphics g) {
        if (active) {
            int multiplier = 6;
            int width = 57 * multiplier;
            int height = 44 * multiplier;
            int x = gameEngine.getWidth() / 2 - width / 2;
            int y = gameEngine.getHeight() / 2 - height / 2;

            g.drawImage(Assets.CAMPFIRE_INVENTORY, x, y, width, height, null);

            int dfWidth = 16;
            int dfHeight = 15;
            int fWidth = dfWidth * multiplier;
            int fHeight = dfHeight * multiplier;
            int amt = (int) Utils.map(0, 0, 100, 0, dfHeight);
            g.drawImage(flame.getSubimage(0, amt, dfWidth, dfHeight - amt), x + 32 * multiplier, y + 5 * multiplier + amt * multiplier, fWidth, fHeight - amt * multiplier, null);

            super.renderInventorySlots(g);

            slots.get(slots.size() - 1).render(g);
        }
    }

    public void setTAGS(Map<String, String> TAGS) {
        this.TAGS = TAGS;
    }
}
