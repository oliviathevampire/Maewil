package coffeecatteam.theultimatetile.manager;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.inventory.Inventory;
import coffeecatteam.theultimatetile.inventory.InventoryAbstractPlayer;

import java.util.ArrayList;
import java.util.List;

public class InventoryManager {

    public static final List<Inventory> INVENTORIES = new ArrayList<>();

    private TutEngine tutEngine;

    public InventoryManager(TutEngine tutEngine) {
        this.tutEngine = tutEngine;
    }

    public boolean openInventory(InventoryAbstractPlayer inventory) {
        if (!tutEngine.getPlayer().isGuiOpen()) {
            tutEngine.getLogger().info(tutEngine.getPlayer().getUsername() + ": Opening inventory " + inventory.getInvName());
            inventory.setActive(true);
            tutEngine.getPlayer().setGuiOpen(true);
            return true;
        }
        return false;
    }

    public void closeAllInventories() {
        INVENTORIES.forEach(inventory -> {
            tutEngine.getLogger().info(tutEngine.getPlayer().getUsername() + ": Closing inventory " + inventory.getInvName());
            inventory.setActive(false);
            tutEngine.getPlayer().setGuiOpen(false);
        });
    }

    public void closeInventory(InventoryAbstractPlayer inventory) {
        tutEngine.getLogger().info(tutEngine.getPlayer().getUsername() + ": Closing inventory " + inventory.getInvName());
        inventory.setActive(false);
        tutEngine.getPlayer().setGuiOpen(false);
    }

    public void openCloseInventory(InventoryAbstractPlayer inventory) {
        if (!openInventory(inventory))
            closeInventory(inventory);
    }
}
