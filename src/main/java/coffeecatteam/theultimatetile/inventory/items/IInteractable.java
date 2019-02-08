package coffeecatteam.theultimatetile.inventory.items;

import coffeecatteam.theultimatetile.game.entities.creatures.EntityPlayer;

public interface IInteractable {

    boolean onInteracted(EntityPlayer player);
}
