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
        if (!tutEngine.getEntityManager().getPlayer().isGuiOpen()) {
            tutEngine.getLogger().print(tutEngine.getEntityManager().getPlayer().getUsername() + ": Opening inventory " + inventory.getInvName());
            inventory.setActive(true);
            tutEngine.getEntityManager().getPlayer().setGuiOpen(true);
            return true;
        }
        return false;
    }

    public void closeAllInventories() {
        INVENTORIES.forEach(inventory -> {
            tutEngine.getLogger().print(tutEngine.getEntityManager().getPlayer().getUsername() + ": Closing inventory " + inventory.getInvName());
            inventory.setActive(false);
            tutEngine.getEntityManager().getPlayer().setGuiOpen(false);
        });
    }

    public void closeInventory(InventoryAbstractPlayer inventory) {
        tutEngine.getLogger().print(tutEngine.getEntityManager().getPlayer().getUsername() + ": Closing inventory " + inventory.getInvName());
        inventory.setActive(false);
        tutEngine.getEntityManager().getPlayer().setGuiOpen(false);
    }

    public void openCloseInventory(InventoryAbstractPlayer inventory) {
        if (!openInventory(inventory))
            closeInventory(inventory);
    }
}
