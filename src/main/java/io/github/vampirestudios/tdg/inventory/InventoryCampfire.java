package io.github.vampirestudios.tdg.inventory;

import coffeecatteam.coffeecatutils.NumberUtils;
import io.github.vampirestudios.tdg.gfx.assets.Assets;
import io.github.vampirestudios.tdg.objs.entities.creatures.PlayerEntity;
import io.github.vampirestudios.tdg.objs.items.Item;
import io.github.vampirestudios.tdg.objs.items.ItemStack;
import io.github.vampirestudios.tdg.objs.items.Items;
import io.github.vampirestudios.tdg.screen.options.OptionsScreen;
import io.github.vampirestudios.tdg.screen.options.controls.Keybind;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.start.MaewilLauncher;
import io.github.vampirestudios.tdg.tags.CompoundTag;
import io.github.vampirestudios.tdg.utils.Identifier;
import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;
import org.newdawn.slick.Image;

public class InventoryCampfire extends InventoryAbstractPlayer {

    private Image flame = Assets.getSpriteExact(new Identifier("maewil", "textures/gui/inventory/campfire.png"), 57, 0, 16, 15);
    private CompoundTag TAGS;

    public InventoryCampfire(MaewilEngine maewilEngine, PlayerEntity player, CompoundTag TAGS) {
        super(maewilEngine, player, "Campfire", 190, 370);
        this.TAGS = TAGS;

        int y = 266;
        addSlot(0, 270, y).setSelector(Assets.GUI_SLOT_SELECTER);
    }

    @Override
    public void onOpen() {
        String tagItem = TAGS.getString("item");
        String[] itemData = tagItem.split(":");
        if (itemData[0].equals("null"))
            slots.get(slots.size() - 1).setStack(null);
        else {
            Item item = Items.getItemById(itemData[0]);
            int count = NumberUtils.parseInt(itemData[1]);
            slots.get(slots.size() - 1).setStack(new ItemStack(item, count));
        }
        super.onOpen();
    }

    @Override
    public void onClose() {
        if (slots.get(slots.size() - 1).getStack() == null)
            TAGS.setString("item", "null:0");
        else {
            ItemStack stack = slots.get(slots.size() - 1).getStack();
            String id = stack.getId();
            String amt = String.valueOf(stack.getCount());
            TAGS.setString("item", id + ":" + amt);
        }
        super.onClose();
    }

    @Override
    public void update(GameContainer container, float delta) {
        super.update(container, delta);

        if (active) {
            if (maewilEngine.getKeyManager().keyJustPressed(OptionsScreen.OPTIONS.controls().get(Keybind.X).getKeyCode())) {
                //if (slots.get(getInventorySelectedIndex()).getStack() == null || slots.get(getInventorySelectedIndex()).getStack().getItem() instanceof FoodItem) {
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
            int x = MaewilLauncher.WIDTH / 2 - width / 2;
            int y = MaewilLauncher.HEIGHT / 2 - height / 2;

            Assets.GUI_CAMPFIRE_INVENTORY.draw(x, y, width, height);

            int dfWidth = 16;
            int dfHeight = 15;
            int fWidth = dfWidth * multiplier;
            int fHeight = dfHeight * multiplier;
            int amt = (int) NumberUtils.map(0, 0, 100, 0, dfHeight);
            flame.getSubImage(0, amt, dfWidth, dfHeight - amt).draw(x + 32 * multiplier, y + 5 * multiplier + amt * multiplier, fWidth, fHeight - amt * multiplier);

            super.renderInventorySlots(g);

            slots.get(slots.size() - 1).render(g);
        }
    }

    public void setTAGS(CompoundTag TAGS) {
        this.TAGS = TAGS;
    }
}
