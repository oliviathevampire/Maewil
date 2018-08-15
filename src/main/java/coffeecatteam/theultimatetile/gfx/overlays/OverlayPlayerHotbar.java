package coffeecatteam.theultimatetile.gfx.overlays;

import coffeecatteam.theultimatetile.Handler;
import coffeecatteam.theultimatetile.entities.creatures.EntityPlayer;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.items.ItemStack;

import java.awt.*;
import java.awt.event.KeyEvent;

public class OverlayPlayerHotbar extends Overlay {

    private int selectedIndex = 0;

    public OverlayPlayerHotbar(Handler handler, EntityPlayer player) {
        super(handler, player);
    }

    @Override
    public void tick() {
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_1))
            selectedIndex = 0;
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_2))
            selectedIndex = 1;
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_3))
            selectedIndex = 2;
        if (selectedIndex < handler.getWorld().getEntityManager().getPlayer().getInventory().getHotbar().size())
            player.setEquippedItem(handler.getWorld().getEntityManager().getPlayer().getInventory().getHotbar().get(selectedIndex));
    }

    @Override
    public void render(Graphics g) {
        int barWidth = 28;
        int barHeight = 10;

        int multiplier = 6;
        int width = barWidth * multiplier;
        int height = barHeight * multiplier;
        int y = handler.getHeight() - height;

        g.drawImage(Assets.INVENTORY.crop(10, 54, barWidth, barHeight), handler.getWidth() / 2 - width / 2, y, width, height, null);

        int selectedWidth = 10;
        int selectedHeight = 10;
        int sWidth = selectedWidth * multiplier;
        int sHeight = selectedHeight * multiplier;

        int x = handler.getWidth() / 2 - width / 2;
        g.drawImage(Assets.INVENTORY.crop(0, 54, selectedWidth, selectedHeight), x + selectedIndex * (sWidth - multiplier), y, sWidth, sHeight, null);

        int itemWidth = 32;
        int itemHeight = 32;
        if (handler.getWorld().getEntityManager().getPlayer().getInventory().getHotbar().size() > 0) {
            for (int i = 0; i < handler.getWorld().getEntityManager().getPlayer().getInventory().getHotbar().size(); i++) {
                ItemStack stack = handler.getWorld().getEntityManager().getPlayer().getInventory().getHotbar().get(i);
                int xPos = x + 14 + 54 * i;
                int yPos = handler.getHeight() - itemHeight - itemHeight / 2;
                g.drawImage(stack.getTexture(), xPos, yPos, itemWidth, itemHeight, null);
                Text.drawString(g, String.valueOf(stack.getCount()), xPos, yPos + 15, Color.white, Assets.FONT_20);
            }
        }
    }
}
