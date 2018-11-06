package coffeecatteam.theultimatetile.manager;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.inventory.Inventory;
import coffeecatteam.theultimatetile.inventory.InventoryAbstractPlayer;
import coffeecatteam.theultimatetile.utils.Logger;

import java.util.ArrayList;
import java.util.List;

public class InventoryManager {

    public static final List<Inventory> INVENTORIES = new ArrayList<>();

    private TheUltimateTile theUltimateTile;

    public InventoryManager(TheUltimateTile theUltimateTile) {
        this.theUltimateTile = theUltimateTile;
    }

    public boolean openInventory(InventoryAbstractPlayer inventory) {
        if (!theUltimateTile.getEntityManager().getPlayer().isGuiOpen()) {
            Logger.print(theUltimateTile.getEntityManager().getPlayer().getUsername() + ": Opening inventory " + inventory.getInvName());
            inventory.setActive(true);
            theUltimateTile.getEntityManager().getPlayer().setGuiOpen(true);
            return true;
        }
        return false;
    }

    public void closeAllInventories() {
        INVENTORIES.forEach(inventory -> {
            Logger.print(theUltimateTile.getEntityManager().getPlayer().getUsername() + ": Closing inventory " + inventory.getInvName());
            inventory.setActive(false);
            theUltimateTile.getEntityManager().getPlayer().setGuiOpen(false);
        });
    }

    public void closeInventory(InventoryAbstractPlayer inventory) {
        Logger.print(theUltimateTile.getEntityManager().getPlayer().getUsername() + ": Closing inventory " + inventory.getInvName());
        inventory.setActive(false);
        theUltimateTile.getEntityManager().getPlayer().setGuiOpen(false);
    }

    public void openCloseInventory(InventoryAbstractPlayer inventory) {
        if (!openInventory(inventory))
            closeInventory(inventory);
    }
}
