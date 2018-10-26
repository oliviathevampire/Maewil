package coffeecatteam.theultimatetile.inventory;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.entities.creatures.EntityPlayer;
import coffeecatteam.theultimatetile.entities.statics.interactable.EntityCampfire;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.inventory.items.Item;
import coffeecatteam.theultimatetile.inventory.items.ItemFood;
import coffeecatteam.theultimatetile.inventory.items.ItemStack;
import coffeecatteam.theultimatetile.manager.ItemManager;
import coffeecatteam.theultimatetile.state.StateOptions;
import coffeecatteam.theultimatetile.state.options.controls.Keybind;
import coffeecatteam.theultimatetile.utils.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class InventoryCampfire extends InventoryAbstractPlayer {

    private BufferedImage flame = Assets.getSpriteExact("/assets/textures/gui/inventory/campfire.png", 57, 0, 16, 15);

    public InventoryCampfire(TheUltimateTile theUltimateTile, EntityPlayer player, EntityCampfire campfire) {
        super(theUltimateTile, player, 190, 370);

        int y = 266;
        addSlot(0, 270, y).setSelector(Assets.SLOT_SELECTER).setStack(
                campfire.getDataTags() != null
                ? new ItemStack(ItemManager.getItemById(campfire.getDataTags()[0].split(":")[0]),
                        Utils.parseInt(campfire.getDataTags()[0].split(":")[1]))
                : null
        );

        if (campfire.getDataTags() != null) {
            Item item = ItemManager.getItemById(campfire.getDataTags()[0].split(":")[0]);
            int count = Utils.parseInt(campfire.getDataTags()[0].split(":")[1]);
            slots.get(slots.size() - 1).setStack(new ItemStack(item, count));
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (active) {
            if (theUltimateTile.getKeyManager().keyJustPressed(StateOptions.OPTIONS.controls().get(Keybind.X).getKeyCode())) {
                if (slots.get(getInventorySelectedIndex()).getStack() == null || slots.get(getInventorySelectedIndex()).getStack().getItem() instanceof ItemFood) {
                    swapSlots(slots.get(slots.size() - 1), slots.get(getInventorySelectedIndex()));
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        if (active) {
            int multiplier = 6;
            int width = 57 * multiplier;
            int height = 44 * multiplier;
            int x = theUltimateTile.getWidth() / 2 - width / 2;
            int y = theUltimateTile.getHeight() / 2 - height / 2;

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
}
