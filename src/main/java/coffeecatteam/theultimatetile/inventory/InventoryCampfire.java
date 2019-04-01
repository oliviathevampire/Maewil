package coffeecatteam.theultimatetile.inventory;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.TutLauncher;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.objs.entities.creatures.EntityPlayer;
import coffeecatteam.theultimatetile.objs.items.Item;
import coffeecatteam.theultimatetile.objs.items.ItemStack;
import coffeecatteam.theultimatetile.objs.items.Items;
import coffeecatteam.theultimatetile.state.StateOptions;
import coffeecatteam.theultimatetile.state.options.controls.Keybind;
import coffeecatteam.theultimatetile.tags.TagCompound;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

public class InventoryCampfire extends InventoryAbstractPlayer {

    private Image flame = Assets.getSpriteExact("/assets/textures/gui/inventory/campfire.png", 57, 0, 16, 15);
    private TagCompound TAGS;

    public InventoryCampfire(TutEngine tutEngine, EntityPlayer player, TagCompound TAGS) {
        super(tutEngine, player, "Campfire", 190, 370);
        this.TAGS = TAGS;

        int y = 266;
        addSlot(0, 270, y).setSelector(Assets.SLOT_SELECTER);
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
    public void update(GameContainer container, StateBasedGame game, int delta) {
        super.update(container, game, delta);

        if (active) {
            if (tutEngine.getKeyManager().keyJustPressed(StateOptions.OPTIONS.controls().get(Keybind.X).getKeyCode())) {
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
            int x = TutLauncher.WIDTH / 2 - width / 2;
            int y = TutLauncher.HEIGHT / 2 - height / 2;

            Assets.CAMPFIRE_INVENTORY.draw(x, y, width, height);

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

    public void setTAGS(TagCompound TAGS) {
        this.TAGS = TAGS;
    }
}
