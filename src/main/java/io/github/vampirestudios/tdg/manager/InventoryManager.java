package io.github.vampirestudios.tdg.manager;

import io.github.vampirestudios.tdg.inventory.Inventory;
import io.github.vampirestudios.tdg.inventory.InventoryAbstractPlayer;
import io.github.vampirestudios.tdg.start.TutEngine;
import io.github.vampirestudios.tdg.start.TutLauncher;

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
            TutLauncher.LOGGER.info(tutEngine.getPlayer().getUsername() + ": Opening inventory " + inventory.getInvName());
            inventory.setActive(true);
            tutEngine.getPlayer().setGuiOpen(true);
            return true;
        }
        return false;
    }

    public void closeAllInventories() {
        INVENTORIES.forEach(inventory -> {
            TutLauncher.LOGGER.info(tutEngine.getPlayer().getUsername() + ": Closing inventory " + inventory.getInvName());
            inventory.setActive(false);
            tutEngine.getPlayer().setGuiOpen(false);
        });
    }

    public void closeInventory(InventoryAbstractPlayer inventory) {
        TutLauncher.LOGGER.info(tutEngine.getPlayer().getUsername() + ": Closing inventory " + inventory.getInvName());
        inventory.setActive(false);
        tutEngine.getPlayer().setGuiOpen(false);
    }

    public void openCloseInventory(InventoryAbstractPlayer inventory) {
        if (!openInventory(inventory))
            closeInventory(inventory);
    }
}
