package io.github.vampirestudios.tdg.manager;

import io.github.vampirestudios.tdg.inventory.Inventory;
import io.github.vampirestudios.tdg.inventory.InventoryAbstractPlayer;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.start.MaewilLauncher;

import java.util.ArrayList;
import java.util.List;

public class InventoryManager {

    public static final List<Inventory> INVENTORIES = new ArrayList<>();

    private MaewilEngine maewilEngine;

    public InventoryManager(MaewilEngine maewilEngine) {
        this.maewilEngine = maewilEngine;
    }

    public boolean openInventory(InventoryAbstractPlayer inventory) {
        if (!maewilEngine.getPlayer().isGuiOpen()) {
            MaewilLauncher.LOGGER.info(maewilEngine.getPlayer().getUsername() + ": Opening inventory " + inventory.getInvName());
            inventory.setActive(true);
            maewilEngine.getPlayer().setGuiOpen(true);
            return true;
        }
        return false;
    }

    public void closeAllInventories() {
        INVENTORIES.forEach(inventory -> {
            MaewilLauncher.LOGGER.info(maewilEngine.getPlayer().getUsername() + ": Closing inventory " + inventory.getInvName());
            inventory.setActive(false);
            maewilEngine.getPlayer().setGuiOpen(false);
        });
    }

    public void closeInventory(InventoryAbstractPlayer inventory) {
        MaewilLauncher.LOGGER.info(maewilEngine.getPlayer().getUsername() + ": Closing inventory " + inventory.getInvName());
        inventory.setActive(false);
        maewilEngine.getPlayer().setGuiOpen(false);
    }

    public void openCloseInventory(InventoryAbstractPlayer inventory) {
        if (!openInventory(inventory))
            closeInventory(inventory);
    }
}
