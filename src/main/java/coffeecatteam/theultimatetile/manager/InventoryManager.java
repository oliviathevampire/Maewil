package coffeecatteam.theultimatetile.manager;

import coffeecatteam.coffeecatutils.Logger;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.inventory.Inventory;
import coffeecatteam.theultimatetile.game.inventory.InventoryAbstractPlayer;

import java.util.ArrayList;
import java.util.List;

public class InventoryManager {

    public static final List<Inventory> INVENTORIES = new ArrayList<>();

    private GameEngine gameEngine;

    public InventoryManager(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    public boolean openInventory(InventoryAbstractPlayer inventory) {
        if (!gameEngine.getEntityManager().getPlayer().isGuiOpen()) {
            Logger.print(gameEngine.getEntityManager().getPlayer().getUsername() + ": Opening inventory " + inventory.getInvName());
            inventory.setActive(true);
            gameEngine.getEntityManager().getPlayer().setGuiOpen(true);
            return true;
        }
        return false;
    }

    public void closeAllInventories() {
        INVENTORIES.forEach(inventory -> {
            Logger.print(gameEngine.getEntityManager().getPlayer().getUsername() + ": Closing inventory " + inventory.getInvName());
            inventory.setActive(false);
            gameEngine.getEntityManager().getPlayer().setGuiOpen(false);
        });
    }

    public void closeInventory(InventoryAbstractPlayer inventory) {
        Logger.print(gameEngine.getEntityManager().getPlayer().getUsername() + ": Closing inventory " + inventory.getInvName());
        inventory.setActive(false);
        gameEngine.getEntityManager().getPlayer().setGuiOpen(false);
    }

    public void openCloseInventory(InventoryAbstractPlayer inventory) {
        if (!openInventory(inventory))
            closeInventory(inventory);
    }
}
